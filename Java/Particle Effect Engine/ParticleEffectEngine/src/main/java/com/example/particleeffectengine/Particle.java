package com.example.particleeffectengine;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import java.util.Random;

public class Particle {
    private static Random random = new Random();
    private double initialSteps;
    public double steps;
    private double x;
    private double y;
    private Point2D movement;
    private int radius;
    private double weight;
    private boolean floored;
    private boolean getFloored;
    private double gravity=0.11;
    private double minimumHeight;

    public Particle() {

    }

    public void initializeParticle(){
        initialSteps = Main.timeToLive;
        steps = Main.timeToLive;
        x = Main.emitterX;
        y = Main.emitterY;
        radius = 6;
        weight = random.nextDouble(0.05,0.20);
        spray();
        minimumHeight=(-movement.getY())*40;
        getFloored=false;
        floored=false;
    }

    public void spray(){
        float angle = (float) random.nextInt(0,90);
        angle+=45;
        int quadrant = (int) (angle/90);
        angle = angle % 90;
        angle = angle/90;
        if (quadrant==0) movement = new Point2D((Math.random()+1.5)*(1-angle),(Math.random()-1.5)*angle);
        if (quadrant==1) movement = new Point2D(-(Math.random()+1.5)*angle,(Math.random()-1.5)*(1-angle));
    }


    public void doStep() {
        steps--;
        wallBounce();
    }


    public void wallBounce(){
        double previousMovement=0;
        if (movement.getY()<0) previousMovement=movement.getY();
        if (movement.getY() < 2.5 && !floored) {
            this.movement = new Point2D(movement.getX(), movement.getY() + weight * gravity);
        }
        xMovement();
        yMovement();
        double maximum=0;
        boolean falling=false;
        if (previousMovement<0 && movement.getY()>0){
            falling=true;
            maximum=Main.windowHeight-y;
        }
        if (falling && maximum<minimumHeight){
            getFloored=true;
        }
    }

    public void yMovement(){
        double nextY = y + movement.getY();
        if (nextY > Main.windowHeight - (radius / 2) && !floored) { //FLOOR
            if (!getFloored) {
                double timeofcollision = (Main.windowHeight - nextY) / (y - nextY);
                y += movement.getY() * timeofcollision;
                movement = new Point2D(movement.getX(), -movement.getY());
                y += movement.getY() * (1 - timeofcollision);
                movement = new Point2D(movement.getX(), movement.getY() * (weight+0.3));
            }else{
                floored=true;
                movement = new Point2D(movement.getX(),0);
                y=Main.windowHeight-radius;
            }
        } else if (nextY < 15 + (radius / 2)) { //CEILING
            double timeofcollision = (15 - nextY + 2) / (y - nextY);
            y += movement.getY() * timeofcollision;
            movement = new Point2D(movement.getX(), -movement.getY());
            y += movement.getY() * (1 - timeofcollision);
        } else {
            y = nextY;
        }
    }

    public void xMovement(){
        double nextX = x + movement.getX();
        if (nextX > Main.windowWidth - (radius / 2)) { //RIGHT WALL
            double timeofcollision = (Main.windowWidth - nextX - 1) / (x - nextX);
            x += movement.getX() * timeofcollision;
            movement = new Point2D(-movement.getX(), movement.getY());
            x += movement.getX() * (1 - timeofcollision);
        } else if (nextX < 0 + (radius / 2)) { //LEFT WALL
            double timeofcollision = (nextX - 3) / (x - nextX);
            x += movement.getX() * timeofcollision;
            movement = new Point2D(-movement.getX(), movement.getY());
            x += movement.getX() * (1 - timeofcollision);
        } else x = nextX;
    }


    public void render(GraphicsContext gc){
        if (steps>500)gc.setGlobalAlpha(1);
        else gc.setGlobalAlpha(steps/500);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setFill(Color.GHOSTWHITE);
        gc.fillOval(x,y,radius,radius);
    }

    public void checkCollisionsButBetter(){

    }

    public void calculateCollision(Particle particle1, Particle particle2){
        Point2D tmp = particle1.movement;
        particle1.movement = particle2.movement;
        particle2.movement = tmp;
    }


}
