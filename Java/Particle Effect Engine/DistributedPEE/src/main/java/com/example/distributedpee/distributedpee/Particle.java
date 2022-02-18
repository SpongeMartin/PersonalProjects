package distributedpee;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

import java.util.Random;

public class Particle {
    private static Random random = new Random();
    public double steps;
    public double x;
    public double y;
    private Point2D vector;
    public int radius;
    private double weight;
    private double gravity=0.11;
    private boolean alreadyCollided;

    public void initializeParticle(double x, double y,int timeToLive){
        steps = timeToLive;
        this.x = x;
        this.y = y;
        radius = 5;
        weight = 5;
        spray();
        alreadyCollided=false;
    }


    public void spray(){
        float angle = (float) random.nextInt(0,90);
        angle+=45;
        int quadrant = (int) (angle/90);
        angle = angle % 90;
        angle = angle/90;
        if (quadrant==0) vector = new Point2D((Math.random()+1.6)*(1-angle),(Math.random()-1.6)*angle);
        if (quadrant==1) vector = new Point2D(-(Math.random()+1.6)*angle,(Math.random()-1.6)*(1-angle));
    }


    public void doStep(int position) {
        steps--;
        if (!alreadyCollided) {
            checkCollisions(position);
        }
        alreadyCollided=false;
        wallBounce();
    }

    public void doStepTred(Particle[] particleArray,int i){
        steps--;
        if (!alreadyCollided) {
            checkCollisionsTred(particleArray, i);
        }
        alreadyCollided=false;
        wallBounce();
    }


    public void wallBounce(){
        if (vector.getY() < 2.5) {
            this.vector = new Point2D(vector.getX(), vector.getY() + weight/100 * gravity);
        }
        xMovement();
        yMovement();
    }



    public void yMovement(){
        double nextY = y + vector.getY();
        double friction = weight/100+0.3;
        if (nextY > Main.windowHeight - radius) { //FLOOR
            vector = new Point2D(vector.getX(), -vector.getY()*friction);
            while (y>Main.windowHeight){
                if (vector.getY()<0) {
                    y += vector.getY();
                }else{
                    y -= vector.getY();
                }
            }
        } else if (nextY < 15 + (radius / 2)) { //CEILING
            vector = new Point2D(vector.getX(), -vector.getY());
            y = 15 + radius/2;
        } else {
            y = nextY;
        }
    }

    public void xMovement(){
        double nextX = x + vector.getX();
        double friction = weight/100+0.5;
        if (nextX > Main.windowWidth - (radius)) { //RIGHT WALL
            vector = new Point2D(-vector.getX() * friction,vector.getY()*0.95);
            x=Main.windowWidth - (radius);
        } else if (nextX < 0 + (radius / 2)) { //LEFT WALL
            vector = new Point2D(-vector.getX()*friction, vector.getY()*0.9);
            x=(radius/2);
        } else x = nextX;
    }

    public void render(GraphicsContext gc){
        if (steps>500)gc.setGlobalAlpha(1);
        else gc.setGlobalAlpha(steps/500);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setFill(Color.CORAL);
        gc.fillOval(x,y,radius,radius);
    }


    public void checkCollisionsTred(Particle[] particleArray,int position){
        for (int i = position+1; i<particleArray.length; i++) {
            Particle particle = particleArray[i];
            if (particle == null) break;
            if (this == particle || particle.alreadyCollided) continue;
            for (int j = -radius/2; j < radius/2; j++) {
                if (particle.x - radius / 2 < x + j && particle.x + radius / 2 > x + j && particle.y + radius / 2 > y + j && particle.y - radius / 2 < y + j) {
                    calculateCollision(this, particle);
                }
            }
        }
    }

    public synchronized void checkCollisions(int position){
        for (int i = position+1; i < Main.particles.size(); i++) {
            Particle particle = Main.particles.get(i);
            if (this == particle || particle.alreadyCollided) continue;
            for (int j = -radius/2; j < radius/2; j++) {
                if (particle.x-radius/2 < x+j && particle.x+radius/2 > x+j && particle.y+radius/2 > y+j && particle.y-radius/2 < y+j){
                    calculateCollision(this,particle);
                }
            }
        }
    }


    public synchronized void calculateCollision(Particle particle1, Particle particle2) {
        double xDifference = particle1.vector.getX() - particle2.vector.getX();
        double yDifference = particle1.vector.getY() - particle2.vector.getY();
        double xDistance = particle2.x - particle1.x;
        double yDistance = particle2.y - particle1.y;

        if (xDifference * xDistance + yDifference * yDistance >= 0) {
            double collisionAngle = -Math.atan2(particle2.y - particle1.y, particle2.x - particle1.x);
            particle1.vector= rotateRAD(particle1.vector,collisionAngle);
            particle2.vector= rotateRAD(particle2.vector,collisionAngle);
            Point2D postVector1 = new Point2D((particle1.vector.getX() * (particle1.weight - particle2.weight) / (particle1.weight + particle2.weight) + particle2.vector.getX() * 2 * (particle2.weight) / (particle1.weight + particle2.weight))*0.999, particle1.vector.getY()*0.999);
            Point2D postVector2 = new Point2D((particle2.vector.getX() * (particle1.weight - particle2.weight) / (particle1.weight + particle2.weight) + particle1.vector.getX() * 2 * (particle2.weight) / (particle1.weight + particle2.weight))*0.999, particle2.vector.getY()*0.999);
            particle1.vector = rotateRAD(postVector1,-collisionAngle);
            particle2.vector = rotateRAD(postVector2,-collisionAngle);
            if (particle1.vector.getY()<-1.2) particle1.vector = new Point2D(particle1.vector.getX(),-1.2);
            if (particle2.vector.getY()<-1.2) particle2.vector = new Point2D(particle2.vector.getX(),-1.2);
        }
    }
    public static Point2D rotateRAD(Point2D vector, double a) {
        double cosA = Math.cos(a), sinA = Math.sin(a);
        return new Point2D(cosA * vector.getX() - sinA * vector.getY(), sinA * vector.getX() + cosA * vector.getY());
    }

    public synchronized void calculateCollision1(Particle particle1, Particle particle2) {
        Point2D tmp = particle1.vector;
        particle1.vector = new Point2D(particle2.vector.getX()*0.999,particle2.vector.getY()*0.999);
        particle2.vector = new Point2D(tmp.getX()*0.999,tmp.getY()*0.999);
        particle1.alreadyCollided = true;
        particle2.alreadyCollided = true;
        particle1.x+=particle1.vector.getX()/2+particle1.vector.getX()/4;
        particle1.y+=particle1.vector.getY()/2+particle1.vector.getY()/4;
    }
}

