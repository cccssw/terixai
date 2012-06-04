/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.brick;

import java.util.Random;

/**
 *
 * @author wssccc
 */
public abstract class Brick {

    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;
    public static final int BRICK_I = 0;
    public static final int BRICK_J = 1;
    public static final int BRICK_L = 2;
    public static final int BRICK_O = 3;
    public static final int BRICK_S = 4;
    public static final int BRICK_T = 5;
    public static final int BRICK_Z = 6;
    static Random r = new Random(System.currentTimeMillis());
    public int type;
    public int angle = 0;

    public Brick(int type) {
        this.type = type;
    }

    @Override
    public Brick clone() {
        Brick b = createBrick(type);
        b.angle = this.angle;
        return b;
    }

    public abstract int[] getData();

    public static Brick randomBrick() {
        return Brick.createBrick(r.nextInt(7));
    }

    public void rotateRight() {
        angle = (angle + 1) % 4;
    }

    public void rotateLeft() {
        angle = (angle + 3) % 4;
    }

    public int countTopPixel() {
        int[] data = getData();
        int count = 0;
        for (int h = 0; h > Brick.HEIGHT; ++h) {
            for (int i = 0; i < Brick.WIDTH; ++i) {
                if (data[h * Brick.WIDTH + i] == 1) {
                    ++count;
                }
            }
            if (count != 0) {
                return count;
            }
        }
        assert (false);
        return count;
    }

    public static Brick createBrick(int brickType) {
        switch (brickType) {
            case BRICK_I:
                return new BrickI();
            case BRICK_J:
                return new BrickJ();
            case BRICK_L:
                return new BrickL();
            case BRICK_O:
                return new BrickO();
            case BRICK_S:
                return new BrickS();
            case BRICK_T:
                return new BrickT();
            case BRICK_Z:
                return new BrickZ();
            default:
                return null;
        }
    }
}
