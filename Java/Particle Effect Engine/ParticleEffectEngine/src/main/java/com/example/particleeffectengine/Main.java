package com.example.particleeffectengine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    enum AnimationOption {SEQUENTIAL,PARALLEL}
    private static GraphicsContext gc;
    private static List<Particle> particles = new ArrayList<>();
    private static Emitter emitter = new Emitter();
    private static final long[] frameTimes = new long[100];
    private static int frameTimeIndex = 0;
    private static boolean arrayFilled = false;
    private static boolean firstDeath = false;
    private static long runstart;
    private static int particlesOnScreen=0;
    private static Pane pane;
    private static Stage primaryStage;
    public static int particlesSoFar=0;
    public static int totalParticles;
    public static int windowWidth;
    public static int windowHeight;
    public static int timeToLive;
    public static int emitterX;
    public static int emitterY;
    public static int emitPerS;
    public static AnimationOption animationOption = AnimationOption.SEQUENTIAL;
    public static boolean fullscreen=false;
    public static int collisions=0;
    public static int emittedCount=0;

    public static void main(String[] args){
        launch(args);
        if (runstart!=0) System.out.println("Program se je zaključil v času " + (System.currentTimeMillis()-runstart) + " ms \nTrkov je bilo: " + collisions);
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

    public static void animation() throws Exception{
        //Animation scene
        pane.getChildren().clear();
        createAnimationPane(pane);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        runstart = System.currentTimeMillis();
        AnimationTimer fps = new AnimationTimer() {
            @Override
            public void handle(long currenttime) {
                long oldFrameTime = frameTimes[frameTimeIndex];
                double frameRate = 0;
                frameTimes[frameTimeIndex] = currenttime;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }
                if (arrayFilled) {
                    long elapsedNanos = currenttime - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                }
                gc.setFill(Color.WHITE);
                gc.setGlobalAlpha(1);
                gc.fillText("FPS: " + (int) frameRate, 5, 15);
                gc.fillText("Particles on screen: " + particles.size(), windowWidth-150, 15);
            }
        };
        AnimationTimer rendering = new AnimationTimer() {
            @Override
            public void handle(long l) {
                draw();
            }
        };
        rendering.start();
        fps.start();
    }

    private static Parent createAnimationPane(Pane p){
        if (fullscreen) {
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.setFullScreen(fullscreen);
        }
        p.setPrefSize(windowWidth,windowHeight);
        Canvas canvas = new Canvas(windowWidth,windowHeight);
        gc = canvas.getGraphicsContext2D();
        p.getChildren().add(canvas);
        return p;
    }

    private static void draw(){
        gc.setGlobalAlpha(1.0);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,windowWidth,windowHeight);
        if (!firstDeath) particles.addAll(emitter.emit(emitterX, emitterY));
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.doStep();
            if (particle.steps <= 0){
                firstDeath = true;
                if (totalParticles>particlesSoFar){
                    particlesSoFar++;
                    emittedCount++;
                    particle.initializeParticle();
                    if (emittedCount==emitPerS){
                        emittedCount=0;
                    }
                }
                else{
                    //particle.matrixRemove();
                    particles.remove(particle);
                }
            }
            particle.render(gc);
        }
        if (particles.size()==0 && particlesOnScreen==0){
            Platform.exit();
        }
    }
}