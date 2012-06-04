/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai.pso;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author wssccc
 */
public abstract class Pso {

    int particleCount;
    Random rand;
    int dimision;
    static final int VMAX = 10;
    static final int C1 = 2;
    static final int C2 = 2;

    public Pso(int particleCount, int dimision) {
        this.particleCount = particleCount;
        rand = new Random(System.currentTimeMillis());
        this.dimision = dimision;
    }

    public abstract float fitness(Vector v);

    public abstract void beforeIteration();

    public Vector pso(int maxIteration, float criter) {
        Vector gBestPos = new Vector(dimision);

        float pBestVal[] = new float[particleCount];
        ArrayList<Vector> pBestPos = new ArrayList<Vector>();
        ArrayList<Vector> v = new ArrayList<Vector>();
        ArrayList<Vector> particle = new ArrayList<Vector>();

        float gBestVal = 0;


        //init
        for (int i = 0; i < particleCount; ++i) {
            Vector rdp = new Vector(dimision);
            Vector vi = new Vector(dimision);

            rdp.randomInit(rand);
            vi.randomInit(rand);
            particle.add(rdp);
            pBestPos.add(rdp);

            pBestVal[i] = Float.NEGATIVE_INFINITY;
            if (pBestVal[i] > gBestVal) {
                gBestVal = pBestVal[i];
                gBestPos.assgin(particle.get(i));
            }
            v.add(vi);
        }
        do {
            beforeIteration();
            for (int i = 0; i < particleCount; ++i) {
                float fit = fitness(particle.get(i));
                if (fit > pBestVal[i]) {
                    pBestVal[i] = fit;
                    pBestPos.get(i).assgin(particle.get(i));
                }
                if (pBestVal[i] > gBestVal) {
                    gBestVal = pBestVal[i];
                    gBestPos.assgin(pBestPos.get(i));

                    //check criter
                    if (Math.abs(gBestVal - 1) < criter) {
                        maxIteration = 0;
                        break;
                    }
                }
            }
            //vol
            for (int i = 0; i < particleCount; ++i) {
                Vector vt2 = new Vector(dimision);
                Vector vt1 = new Vector(dimision);
                vt2.assgin(gBestPos);
                vt2.sub(particle.get(i));
                vt2.mul(C2 * rand.nextFloat());

                vt1.assgin(pBestPos.get(i));
                vt1.sub(particle.get(i));
                vt1.mul(C1 * rand.nextFloat());
                vt1.add(vt2);
                v.get(i).add(vt1);

                float l = v.get(i).length();
                if (l > VMAX) {
                    v.get(i).mul((float) VMAX / l);
                }

                particle.get(i).add(v.get(i));
            }
        } while ((maxIteration-- > 0));
        System.out.println("gBestVal=" + gBestVal);
        gBestPos.addtional = gBestVal;
        return gBestPos;
    }
}
