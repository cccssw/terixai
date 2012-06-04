 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.brick;

/**
 *
 * @author wssccc
 */
public class BrickJ extends Brick {

    static final int[] data1 = new int[]{0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0};
    static final int[] data2 = new int[]{1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static final int[] data3 = new int[]{0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
    static final int[] data4 = new int[]{0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0};

    public BrickJ() {
        super(BRICK_J);
    }

    @Override
    public int[] getData() {
        switch (angle) {
            case 0:
                return data1;
            case 1:
                return data2;
            case 2:
                return data3;
            case 3:
                return data4;
        }
        assert false;
        return null;
    }
}
