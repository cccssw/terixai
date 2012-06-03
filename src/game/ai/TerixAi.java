/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.Brick;
import game.Evaluate;
import game.TerixState;

/**
 *
 * @author wssccc
 */
public class TerixAi {

    TerixAiConfig config;
    final Brick nextBrick;

    public TerixAi(TerixAiConfig config, Brick nextBrick) {
        this.config = config;
        this.nextBrick = nextBrick;
    }

    //op
    public TerixAiCommand findSteps(TerixState ts, boolean level2) {
        float maxval = Float.NEGATIVE_INFINITY;
        int maxx = 0;
        int maxr = 0;
        float fitness;
        TerixState nts = new TerixState(ts.getWidth(), ts.getHeight());
        for (int x = 0; x < ts.getWidth(); ++x) {
            for (int r = 0; r < 4; ++r) {
                fitness = 0;
                nts.copyState(ts);
                Evaluate e = new Evaluate(nts);
                if (nts.rotate(r)) {
                    //rotate ok
                    if (nts.moveBrick(x, 0)) {
                        //can be moved
                        nts.moveDownDirect();
                        //eval brick fit 
                        fitness += e.evalBrickFit() * config.WEIGHT_FIT;
                        nts.fusion();
                        fitness += e.evalContinuous() * config.WEIGHT_CONTINUOUS;
                        int boom = nts.boom();
                        fitness += boom * config.WEIGHT_BOOM;
                        fitness += e.evalHeight() * config.WEIGHT_HEIGHT;
                        if (!level2) {
                            nts.setBrick(nextBrick);
                            fitness += findSteps(nts, true).val;
                        }

                        if (fitness > maxval) {
                            //save steps
                            maxx = x;
                            maxr = r;
                            maxval = fitness;
                        }
                    }
                }
            }
        }

        return new TerixAiCommand(maxx, maxr, maxval);
    }
}
