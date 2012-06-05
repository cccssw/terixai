/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.Evaluate;
import game.TerixState;
import game.brick.Brick;

/**
 *
 * @author wssccc
 */
public class TerixAi {

    TerixAiParam config;
    final Brick nextBrick;
    TerixState temp1;
    TerixState temp2;

    //TODO: TerixState as a member
    public TerixAi(TerixAiParam config, Brick nextBrick) {
        this.config = config;
        this.nextBrick = nextBrick;
    }

    float eval(TerixState ts) {
        Evaluate e = new Evaluate(ts);
        float score = 0;
        ts.moveDownDirect();
        //eval brick fit 
        score += e.evalBrickFit() * config.WEIGHT_FIT;
        ts.fusion();
        //before boom fit
        //score += e.evalFall() * config.WEIGHT_FALL;
        score += e.evalContinuous() * config.WEIGHT_CONTINUOUS;
        int boom = ts.boom();
        //after boom fit
        score += boom * config.WEIGHT_BOOM;
        score += e.evalVHole() * config.WEIGHT_VHOLE;
        score += e.evalHeight() * config.WEIGHT_HEIGHT;
        return score;
    }

    float findSteps2(TerixState ts) {
        float maxval = Float.NEGATIVE_INFINITY;
        float fitness;

        for (int x = -Brick.WIDTH; x < ts.getWidth(); ++x) {
            for (int r = 0; r < 4; ++r) {
                temp2.copyState(ts);
                if (temp2.rotate(r)) {
                    //rotate ok
                    if (temp2.moveBrick(x)) {
                        //can be moved
                        fitness = eval(temp2);

                        if (fitness > maxval) {
                            maxval = fitness;
                        }
                    }
                }
            }
        }

        return maxval;
    }

    void initTemp(TerixState ts) {
        temp1 = new TerixState(ts.getWidth(), ts.getHeight());
        temp2 = new TerixState(ts.getWidth(), ts.getHeight());
    }

    public TerixAiCommand findSteps(TerixState ts) {
        initTemp(ts);

        float maxval = Float.NEGATIVE_INFINITY;
        int maxx = 0;
        int maxr = 0;
        float fitness;

        for (int x = -Brick.WIDTH; x < ts.getWidth(); ++x) {
            for (int r = 0; r < 4; ++r) {
                temp1.copyState(ts);

                if (temp1.rotate(r)) {
                    //rotate ok
                    if (temp1.moveBrick(x)) {
                        //can be moved
                        fitness = eval(temp1);

                        temp1.setBrick(nextBrick.clone());
                        fitness += findSteps2(temp1);

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
