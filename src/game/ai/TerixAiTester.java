/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.TerixState;
import game.brick.Brick;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wssccc
 */
public class TerixAiTester {

    int[] testdata;

    public TerixAiTester(int length) {
        Random r = new Random(System.currentTimeMillis());
        testdata = new int[length];
        for (int i = 0; i < length; ++i) {
            testdata[i] = r.nextInt(7);
        }
    }

    public TerixAiTester(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            testdata = new int[n];
            for (int i = 0; i < n; ++i) {
                testdata[i] = sc.nextInt();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TerixAiTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float test(TerixAiParam taconf) {
        TerixState ts = new TerixState(10, 20);
        ts.setBrick(Brick.createBrick(testdata[0]));
        for (int i = 1; i < testdata.length; ++i) {

            if (ts.isGameOver()) {
                System.out.println("count=" + i);
                return (float) i / testdata.length;
            }

            Brick next = Brick.createBrick(testdata[i]);
            Brick nexth = next.clone();
            TerixAiCommand tac = new TerixAi(taconf, next).findSteps(ts);
            tac.applyTo(ts);
            ts.setBrick(nexth);
            if (i % 20 == 0) {
                // ts.growUp(2);
            }
        }
        System.out.println("eof");
        return 1f;
    }
}
