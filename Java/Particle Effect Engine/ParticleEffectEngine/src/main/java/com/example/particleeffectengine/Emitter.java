package com.example.particleeffectengine;

import java.util.ArrayList;
import java.util.List;

public class Emitter {
    public List<Particle> emit(double x, double y){
        List<Particle> particles = new ArrayList<>();
        int nParticles = Main.emitPerS;
        for (int i = 0; i < nParticles && Main.particlesSoFar < Main.totalParticles; i++,Main.particlesSoFar++) {
            Particle p = new Particle();
            p.initializeParticle();
            particles.add(p);
        }
        return particles;
    }
}
