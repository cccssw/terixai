/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package terix;

import game.ai.TerixAiConfig;
import game.ai.TerixAiTester;
import game.ai.pso.Pso;
import game.ai.pso.Vector;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wssccc
 */
public class Optimize {

    /**
     * @param args the command line arguments
     */
    public static void run() {

        long t1 = System.currentTimeMillis();
        //final TerixAiTester tai = new TerixAiTester("1.txt");
        int iterMax = 10;
        float criter = 0.01f;
        int particles = 5;
        final int testSize = 5000;

        Pso pso = new Pso(particles, 5) {

            TerixAiTester tai;

            @Override
            public float fitness(Vector v) {
                TerixAiConfig taconf = new TerixAiConfig(v);
                return tai.test(taconf);
            }

            @Override
            public void beforeIteration() {
                tai = new TerixAiTester(testSize);
            }
        };

        Vector v = pso.pso(iterMax, criter);
        try {
            PrintStream p = new PrintStream("out.txt");
            p.println("iterMax=" + iterMax);
            p.println("criter=" + criter);
            p.println("particle size=" + particles);
            p.println("testSize=" + testSize);
            p.println("optimized vector=" + v.toString());
            p.println("time=" + (System.currentTimeMillis() - t1));
            p.close();
        } catch (IOException ex) {
            Logger.getLogger(Optimize.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("optimized vector=" + v.toString());
        System.out.println("time=" + (System.currentTimeMillis() - t1));
    }
}
