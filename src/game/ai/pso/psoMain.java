/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai.pso;

/**
 *
 * @author wssccc
 */
public class psoMain {

    /**
     * @param args the command line arguments
     */
    static float _fitness(float l1, float l2) {
        return 1.0f / (Math.abs(l1 - l2) + 1);
    }

    public static void main(String[] args) {
        // TODO code application logic here

        float x1 = 0;
        float y1 = 1;
        float x2 = 1;
        float y2 = 1;
        float x3 = 2;
        float y3 = 1;
        final float t1 = 0;
        final float t2 = 0.6f;
        final float t3 = 1.6f;

        final Vector p1 = new Vector(new float[]{x1, y1});
        final Vector p2 = new Vector(new float[]{x2, y2});
        final Vector p3 = new Vector(new float[]{x3, y3});

        Pso pso = new Pso(100, 2) {

            @Override
            public float fitness(Vector v) {
                float a = Math.abs(v.distance(p1) - t1);
                float b = Math.abs(v.distance(p2) - t2);
                float c = Math.abs(v.distance(p3) - t3);

                float fit = _fitness(a, b) * _fitness(b, c) * _fitness(a, c);
                //System.out.println("fitness of " + v.toString() + "=" + fit);
                return fit;
            }

            @Override
            public void beforeIteration() {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        System.out.println("pso=" + pso.pso(100000, 0.00001f));
    }
}
