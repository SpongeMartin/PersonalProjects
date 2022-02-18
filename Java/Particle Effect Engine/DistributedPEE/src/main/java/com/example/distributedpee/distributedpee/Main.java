package distributedpee;
import com.google.gson.GsonBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mpi.MPI;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class Main extends Application {
    public enum AnimationOption {SEQUENTIAL,PARALLEL}
    public static GraphicsContext gc;
    public static List<Particle> particles = new ArrayList<>();

    public static boolean firstDeath = false;
    private static Pane pane;
    private static Stage primaryStage;
    public static int particlesSoFar=0;
    public static int totalParticles;
    public static int windowWidth;
    public static int windowHeight;
    public static AnimationOption animationOption = AnimationOption.SEQUENTIAL;
    public static Thread[] thread;
    public static int totalsteps=0;
    public static boolean animationEnd=false;

    public static void main(String[] args) {
        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        if (me==0) {
            System.out.println(MPI.COMM_WORLD.Size());
            int cores=MPI.COMM_WORLD.Size();
            launch(args);
            System.out.println("ok?");
            animationEnd=true;
            int[] info = new int[4];
            info[2]=1;
            for (int i = 1; i < cores; i++) {
                MPI.COMM_WORLD.Send(info,0,info.length,MPI.INT,i,4);
            }
        }else{
            Gson gson = new Gson();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeSpecialFloatingPointValues();
            while (true) {
                int[] info=new int[4]; //info0-total particles info1-windowwidth info2-chunksize info3-windowheight info4-end of program is 1, continue is 0
                MPI.COMM_WORLD.Recv(info,0,info.length,MPI.INT,0,4);
                if (info[2]==1) break;
                totalParticles=info[0];
                windowWidth = info[1];
                windowHeight = info[3];
                int messagesize = MPI.COMM_WORLD.Probe(0, 0).count;
                char[] particleinfo = new char[messagesize];
                MPI.COMM_WORLD.Recv(particleinfo, 0, messagesize, MPI.CHAR, 0, 0);
                String particlestring = String.valueOf(particleinfo);
                Particle[] particles = gson.fromJson(particlestring, Particle[].class);
                for (int i = 0; i<particles.length;i++) {
                    Particle particle = particles[i];
                    if (particle == null) continue;
                    particle.doStepTred(particles,i);
                }
                char[] charparticle = gsonBuilder.create().toJson(particles).toCharArray();
                MPI.COMM_WORLD.Send(charparticle, 0, charparticle.length, MPI.CHAR, 0, 1);
            }
        }
        MPI.Finalize();
    }


    @Override
    public void start(Stage stage) throws Exception {
        //Start scene
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("StartScene.fxml"));
        pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setTitle("Particle engine");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.sizeToScene();
    }

    public static void animation(int emitterX,int emitterY,int emitperS,int timeToLive,boolean testrun){
        //Animation scene
        pane.getChildren().clear();
        createAnimationPane(pane);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        Emitter emitter = new Emitter(emitperS,emitterX,emitterY,timeToLive);
        int cores = Runtime.getRuntime().availableProcessors();
        thread = new Thread[cores];
        for (int i = 0; i < cores; i++) {
            thread[i] = new Thread();
        }
        final long[] frameTimes = new long[100];
        final int[] frameTimeIndex = {0};
        final boolean[] arrayFilled = {false};
        int[] frameRates = new int[100];
        final int[] interval = {0};
        final int[] timeSoFar = {0};
        totalsteps=timeToLive+(totalParticles/emitperS);
        AnimationTimer fps = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                long previousTime = frameTimes[frameTimeIndex[0]];
                double frameRate = 0;
                frameTimes[frameTimeIndex[0]] = currentTime;
                frameTimeIndex[0] = (frameTimeIndex[0] + 1) % frameTimes.length;
                if (frameTimeIndex[0] == 0) {
                    arrayFilled[0] = true;
                }
                if (arrayFilled[0]) {
                    long elapsedNanos = currentTime - previousTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    frameRate = 1000_000_000.0 / elapsedNanosPerFrame;
                }
                gc.setFill(Color.WHITE);
                gc.setGlobalAlpha(1);
                gc.fillText("FPS: " + (int) frameRate, 5, 15);
                gc.fillText("Particles on screen: " + particles.size(), windowWidth-150, 15);
                if (timeSoFar[0]%(totalsteps/100)==0 && interval[0] <100){
                    if (frameRate>0) {
                        frameRates[interval[0]] = (int) frameRate;
                        interval[0]++;
                    }
                }
            }
        };
        AnimationTimer rendering = new AnimationTimer() {
            @Override
            public void handle(long l) {
                timeSoFar[0]++;
                draw(emitter,timeToLive,testrun,frameRates,interval);
            }
        };
        rendering.start();
        fps.start();
    }

    private static void createAnimationPane(Pane p){
        p.setPrefSize(windowWidth,windowHeight);
        Canvas canvas = new Canvas(windowWidth,windowHeight);
        gc = canvas.getGraphicsContext2D();
        p.getChildren().add(canvas);
    }

    private static void draw(Emitter emitter,int TTL,boolean testrun,int[] frameRates,int[] interval){
        if (!testrun) {
            refreshScreen();
            addingParticles(emitter, TTL);
            if (particles.size() == 0) Platform.exit();
        }else{
            refreshScreen();
            addingParticles(emitter,TTL);
            if (particles.size()==0) {
                int averageFPS=60;
                for (int i = 0; i < frameRates.length; i++) {
                    averageFPS+=frameRates[i];
                }
                averageFPS = averageFPS/frameRates.length;
                interval[0]=0;
                System.out.println("The average FPS for " + totalParticles + "particles is :" + averageFPS);
                if (averageFPS > 59) {
                    if (animationOption.toString().equals("BURST")) emitter.emitPerS+=100;
                    totalParticles+=100;
                    totalsteps=TTL+(totalParticles/emitter.emitPerS);
                    particlesSoFar=0;
                    firstDeath=false;
                    refreshScreen();
                    addingParticles(emitter, TTL);
                } else Platform.exit();
            }
        }
    }

    public static void refreshScreen(){
        gc.setGlobalAlpha(1.0);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,windowWidth,windowHeight+20);
    }

    public static void addingParticles(Emitter emitter,int TTL){
        if (!firstDeath) particles.addAll(emitter.emit());
        if (animationOption.toString().equals("PARALLEL")){
            distributedProgram();
        }else{
            sequentialProgram(emitter.x,emitter.y,TTL);
        }
    }

    public static void sequentialProgram(int emitterX, int emitterY,int TTL){
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.doStep(i);
            if (particle.steps <= 0) {
                firstDeath = true;
                if (totalParticles > particlesSoFar) {
                    particlesSoFar++;
                    particle.initializeParticle(emitterX,emitterY,TTL);
                } else {
                    particles.remove(particle);
                }
            }
            particle.render(gc);
        }
    }

    public static void distributedProgram(){
        int cores=MPI.COMM_WORLD.Size();
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeSpecialFloatingPointValues();
        Particle[][] particleMatrix = new Particle[cores][particles.size()];
        sendInfo(cores);
        distributeParticles(gsonBuilder,particleMatrix,cores);
        receiveParticles(gson,cores);
        renderParticles();
        Main.particlesSoFar=particles.size();
    }

    public static void sendInfo(int cores){
        int[] info = new int[4];
        info[0]=totalParticles;
        info[1]=windowWidth;
        info[2]=0;
        info[3]=windowHeight;
        for (int i = 1; i < cores; i++) {
            MPI.COMM_WORLD.Send(info,0,info.length,MPI.INT,i,4);
        }
    }

    public static void distributeParticles(GsonBuilder gsonBuilder,Particle[][] particleMatrix,int cores){
        double zoneWidth = (double)windowWidth/(double)(cores-1);
        char[] composedParticles;
        int[] counter = new int[cores-1];
        for (Particle particle : particles) {
            int zone = (int)(particle.x/zoneWidth);
            if (zone>=cores-1) zone=cores-2;
            if (zone<0) zone = 0;
            particleMatrix[zone][counter[zone]] = particle;
            counter[zone]++;
        }
        for (int i = 1; i < cores; i++) {
            composedParticles = gsonBuilder.create().toJson(particleMatrix[i-1]).toCharArray();
            MPI.COMM_WORLD.Send(composedParticles,0,composedParticles.length,MPI.CHAR,i,0);
        }
    }

    public static void receiveParticles(Gson gson, int cores){
        int next=0;
        for (int i = 1; i < cores; i++) {
            int mSize = MPI.COMM_WORLD.Probe(i,1).count;
            char[] temp = new char[mSize];
            MPI.COMM_WORLD.Recv(temp,0,mSize,MPI.CHAR,i,1);
            Particle[] array = gson.fromJson(String.valueOf(temp),Particle[].class);
            for (int j = 0; j < array.length; j++) {
                if (array[j]==null){
                    continue;
                }
                particles.set(next,array[j]);
                next++;
            }
        }
    }

    public static void renderParticles(){
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            if (particle==null) continue;
            if (particle.steps<=0 && totalParticles==particles.size() || firstDeath && particle.steps<=0) {
                particles.remove(i);
                firstDeath=true;
            }else{
                particle.render(Main.gc);
            }
        }
    }
}
