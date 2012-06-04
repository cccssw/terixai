 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.brick;

/**
 *
 * @author wssccc
 */
public class BrickO extends Brick {

    static final int[] data1 = new int[]{1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public BrickO() {
        super(BRICK_O);
    }

    @Override
    public int[] getData() {
        return data1;
    }
}
