/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.TerixState;

/**
 *
 * @author wssccc
 */
public class TerixAiCommand {

    public int x;
    public int r;
    public int val;

    public void applyTo(TerixState ts) {
        ts.rotate(r);
        ts.moveBrick(x, 0);

        ts.moveDownDirect();
        ts.step();
    }

    public TerixAiCommand(int x, int r, int val) {
        this.x = x;
        this.r = r;
        this.val = val;
    }
}
