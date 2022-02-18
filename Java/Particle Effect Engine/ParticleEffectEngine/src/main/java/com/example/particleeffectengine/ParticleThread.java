package com.example.particleeffectengine;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ParticleThread implements Runnable{

    private int from;
    private int to;
    private double width;
    private int id;
    private CyclicBarrier barrier;
    private Particle[][] particleMatrix;
    private double emitterX;
    private double emitterY;
    private int ttl;

    public ParticleThread(int from, int to,double width,int id,CyclicBarrier barrier,Particle[][] particleMatrix,double emitterX,double emitterY,int ttl){
        this.from=from;
        this.to=to;
        this.width = width;
        this.id = id;
        this.barrier=barrier;
        this.particleMatrix=particleMatrix;
        this.emitterX=emitterX;
        this.emitterY=emitterY;
        this.ttl=ttl;
    }

    @Override
    public void run() {
        int cores = Runtime.getRuntime().availableProcessors();
        //Bag particles into zones (each thread has one zone of particles to take care of)
        for (int i = from; i < to; i++) {
            Particle particle = Main.particles.get(i);
            for (int j = 0; j < cores; j++) {
                if (particle.x<(j+1)*width+particle.radius && particle.x>(j-1)*width-particle.radius){
                    addToArray(j,particle);
                    break;
                }
                if (j==cores-1){
                    synchronized (this) {
                        for (int k = 0; k < particleMatrix[j].length; k++) {
                            if (particleMatrix[j][k]==null){
                                particleMatrix[j][k] = particle;
                                break;
                            }
                        }
                    }
                }
            }
        }
        try {
            barrier.await(); //Once all particles are distributed among the bags, we continue to the next step.
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        barrier.reset();
        //Here we handle particle physics, from movement to collisions via doStep method

        for (int i = 0; i<particleMatrix[id].length; i++) {
            Particle particle = particleMatrix[id][i];
            if (particle == null) break;
            particle.doStepTred(particleMatrix[id],i);
            if (particle.steps <= 0){
                Main.firstDeath = true;
                if (resetOrDie()){
                    Main.particlesSoFar++;
                    particle.initializeParticle(emitterX,emitterY,ttl);
                }
            }
        }
    }

    public synchronized void addToArray(int j, Particle particle){
        for (int k = 0; k < particleMatrix[j].length; k++) {
            if (particleMatrix[j][k]==null){
                particleMatrix[j][k] = particle;
                break;
            }
        }
    }

    public synchronized boolean resetOrDie(){
        return (Main.totalParticles>Main.particlesSoFar);
    }
}

