/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.TerixState;
import game.brick.Brick;

/**
 *
 * @author wssccc
 */
public class TerixReverseAi {
    
    TerixState ts;
    TerixState tmp;
    TerixAi tai;
    
    public TerixReverseAi(TerixState ts) {
        this.ts = ts;
        tmp = new TerixState(ts.getWidth(), ts.getHeight());
        tai = new TerixAi(TerixAiParam.DEFAULT_AI, null);
        tai.initTemp(ts);
    }
    
    float eval(Brick next) {
        tmp.copyState(ts);
        tmp.setBrick(next);
        return tai.findSteps2(tmp);
    }
    
    public Brick worstBrick() {
        Brick worst = Brick.createBrick(0);
        float worstVal = eval(worst);
        
        for (int i = 1; i < 7; ++i) {
            Brick b = Brick.createBrick(i);
            float val = eval(b);
            if (val < worstVal) {
                worst = b;
            }
        }
        return worst;
    }
}
