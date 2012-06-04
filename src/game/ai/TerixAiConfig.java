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
public class TerixAiConfig {

    public float WEIGHT_FIT = 1;
    public float WEIGHT_HEIGHT = 1;
    public float WEIGHT_CONTINUOUS = 3;
    public float WEIGHT_BOOM = 10;
    public float WEIGHT_VHOLE = 0;
    public float WEIGHT_FALL = 1;

    public TerixAiConfig() {
    }

    public TerixAiConfig(Vector v) {
        this.WEIGHT_BOOM = v.get(0);
        this.WEIGHT_CONTINUOUS = v.get(1);
        this.WEIGHT_FIT = v.get(2);
        this.WEIGHT_HEIGHT = v.get(3);
        this.WEIGHT_VHOLE = v.get(4);
        this.WEIGHT_FALL = v.get(5);
    }
}
