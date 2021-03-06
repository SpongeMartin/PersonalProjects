package com.example.particleeffectengine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main extends Application {

    public enum AnimationOption {SEQUENTIAL,PARALLEL}
    public static GraphicsContext gc;
    public static List<Particle> particles = new ArrayList<>();

    public static boolean firstDeath = false;
    private static long runstart;
    private static Pane pane;
    private static Stage primaryStage;
    public static int particlesSoFar=0;
    public static int totalParticles;
    public static int windowWidth;
    public static int windowHeight;
    public static AnimationOption animationOption = AnimationOption.SEQUENTIAL;
    public static Thread[] thread;
    public static Particle[][] particleMatrix;
    public static int totalsteps=0;



    public static void main(String[] args){
        launch(args);
        if (runstart!=0) System.out.println("Program se je zaključil v času " + (System.currentTimeMillis()-runstart));
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
        runstart = System.currentTimeMillis();

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
        gc.fillRect(0,0,windowWidth,windowHeight);
    }

    public static void addingParticles(Emitter emitter,int TTL){
        if (!firstDeath) particles.addAll(emitter.emit());
        if (animationOption.toString().equals("PARALLEL")){
            parallelProgram(emitter.x,emitter.y,TTL);
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

    public static void parallelProgram(int emitterX, int emitterY,int TTL){
        int cores = Runtime.getRuntime().availableProcessors();
        double subzoneWidth=windowWidth/cores;
        int chunk = particles.size()/cores;
        int extra = particles.size() - chunk*cores;
        CyclicBarrier barrier = new CyclicBarrier(cores);
        particleMatrix = new Particle[cores][particles.size()];
        for (int i = 0; i < cores; i++) {
            if (i==cores-1) thread[i] = new Thread(new ParticleThread(chunk*i,chunk*(i+1)+extra,subzoneWidth,i,barrier,particleMatrix,emitterX,emitterY,TTL));
            else thread[i] = new Thread(new ParticleThread(chunk*i,chunk*(i+1),subzoneWidth,i,barrier,particleMatrix,emitterX,emitterY,TTL));
        }
        for (int i = 0; i < cores; i++) {
            thread[i].start();
        }
        for (int i = 0; i < cores; i++) {
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            if (particle.steps<=0 && !(totalParticles>particlesSoFar)) particles.remove(i);
            else particle.render(Main.gc);
        }
    }

}