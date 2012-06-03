/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Random;

/**
 *
 * @author wssccc
 */
public class Brick {

    int[] data;
    int[] tmpdata;
    public int type;
    static final int WIDTH = 4;
    static final int HEIGHT = 4;
    public static final int BRICK_I = 0;
    public static final int BRICK_J = 1;
    public static final int BRICK_L = 2;
    public static final int BRICK_O = 3;
    public static final int BRICK_S = 4;
    public static final int BRICK_T = 5;
    public static final int BRICK_Z = 6;
    static Random r = new Random(System.currentTimeMillis());

    private Brick(int[] data, int type) {
        this.type = type;
        this.data = data;
        this.tmpdata = new int[WIDTH * HEIGHT];
    }

    @Override
    public Brick clone() {
        return createBrick(type);
    }

    public void copyData(Brick b) {
        for (int i = 0; i < WIDTH * HEIGHT; ++i) {
            this.data[i] = b.data[i];
        }
        this.type = b.type;
    }

    public static Brick randomBrick() {
        return Brick.createBrick(r.nextInt(7));
    }

    boolean testLine(int l) {
        for (int x = 0; x < WIDTH; ++x) {
            if (data[l * WIDTH + x] != 0) {
                return true;
            }
        }
        return false;
    }

    public int getObjectHeight() {
        int count = 0;
        for (int y = 0; y < HEIGHT; ++y) {
            if (testLine(y)) {
                ++count;
            }
        }
        return count;
    }

    boolean shiftLeft() {
        for (int i = 0; i < HEIGHT; ++i) {
            if (data[i * WIDTH] != 0) {
                return false;
            }
        }
        //shift left
        for (int i = 1; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                data[j * WIDTH + i - 1] = data[j * WIDTH + i];
                data[j * WIDTH + i] = 0;
            }
        }
        return true;
    }

    boolean shiftUp() {
        for (int i = 0; i < WIDTH; ++i) {
            if (data[i] != 0) {
                return false;
            }
        }
        //shift up
        for (int i = 1; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                data[j + (i - 1) * WIDTH] = data[i * WIDTH + j];
                data[i * WIDTH + j] = 0;
            }
        }
        return true;
    }

    public void align() {
        while (shiftLeft());
        while (shiftUp());
    }

    public void rotateRight() {
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                tmpdata[i * WIDTH + j] = data[j * WIDTH + HEIGHT - 1 - i];
            }
        }
        int[] t = data;
        data = tmpdata;
        tmpdata = t;
        align();
    }

    public void rotateLeft() {
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                tmpdata[j * WIDTH + HEIGHT - 1 - i] = data[i * WIDTH + j];
            }
        }
        int[] t = data;
        data = tmpdata;
        tmpdata = t;
        align();
    }

    public static Brick createBrick(int brickType) {
        switch (brickType) {
            case BRICK_I:
                return new Brick(new int[]{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            case BRICK_J:
                return new Brick(new int[]{1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            case BRICK_L:
                return new Brick(new int[]{0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            case BRICK_O:
                return new Brick(new int[]{1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            case BRICK_S:
                return new Brick(new int[]{0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            case BRICK_T:
                return new Brick(new int[]{0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            case BRICK_Z:
                return new Brick(new int[]{1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, brickType);
            default:
                return null;
        }
    }
}
