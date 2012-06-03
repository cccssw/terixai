/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package terix;

import game.ai.TerixAiConfig;
import game.ai.TerixAiTester;

/**
 *
 * @author wssccc
 */
public class Optimize {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        long t1 = System.currentTimeMillis();
        TerixAiTester tai = new TerixAiTester("1.txt");
        System.out.println("score=" + tai.test(new TerixAiConfig()));
        System.out.println("time=" + (System.currentTimeMillis() - t1));
    }
}
