/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.ai.pso.Vector;

/**
 *
 * @author wssccc
 */
public class TerixAiParam {

    public float WEIGHT_FIT;
    public float WEIGHT_CONTINUOUS;
    public float WEIGHT_BOOM;
    public float WEIGHT_VHOLE;
    public float WEIGHT_HEIGHT;

    public TerixAiParam() {
    }

    public TerixAiParam(Vector v) {
        this.WEIGHT_BOOM = v.get(0);
        this.WEIGHT_CONTINUOUS = v.get(1);
        this.WEIGHT_FIT = v.get(2);
        this.WEIGHT_HEIGHT = v.get(3);
        this.WEIGHT_VHOLE = v.get(4);

    }
}
