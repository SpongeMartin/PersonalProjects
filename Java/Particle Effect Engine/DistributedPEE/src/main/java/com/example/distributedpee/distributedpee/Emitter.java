package distributedpee;

import java.util.ArrayList;
import java.util.List;


public class Emitter {
    public int emitPerS;
    public int x;
    public int y;
    private int timeToLive;
    public Emitter(int emitPerS,int x,int y,int timeToLive){
        this.emitPerS=emitPerS;
        this.x=x;
        this.y=y;
        this.timeToLive=timeToLive;
    }
    public List<Particle> emit(){
        List<Particle> particles = new ArrayList<>();
        int nParticles = emitPerS;
        for (int i = 0; i < nParticles && Main.particlesSoFar < Main.totalParticles; i++,Main.particlesSoFar++) {
            Particle p = new Particle();
            p.initializeParticle(x,y,timeToLive);
            particles.add(p);
        }
        return particles;
    }
}