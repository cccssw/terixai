/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.Brick;
import game.TerixState;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wssccc
 */
public class TerixAiTester {
    
    int[] testdata;
    
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
    
    public float test(TerixAiConfig taconf) {
        TerixState ts = new TerixState(10, 20);
        ts.setBrick(Brick.createBrick(testdata[0]));
        for (int i = 1; i < testdata.length; ++i) {
            if (ts.isGameOver()) {
                System.out.println("count=" + i);
                return (float) i / testdata.length;
            }
            
            Brick next = Brick.createBrick(testdata[i]);
            Brick nexth = next.clone();
            TerixAiCommand tac = new TerixAi(taconf, next).findSteps(ts, false);
            tac.applyTo(ts);
            ts.setBrick(nexth);
        }
        System.out.println("eof");
        return 1f;
    }
}
