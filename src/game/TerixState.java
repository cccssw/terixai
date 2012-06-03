/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author wssccc
 */
public class TerixState {

    int[] data;
    int width;
    int height;
    Brick brick = null;
    int brickX;
    int brickY;

    public boolean isGameOver() {
        return !checkRow(0, 0);
    }

    public void copyState(TerixState ts) {
        if (ts.width != width || ts.height != height) {
            System.out.println("bad copy");
            return;
        }
        //copy data
        for (int i = 0; i < width * height; ++i) {
            this.data[i] = ts.data[i];
        }
        //
        if (ts.brick != null) {
            if (this.brick == null) {
                this.brick = Brick.randomBrick();
            }
            this.brick.copyData(ts.brick);
        }
        this.brickX = ts.brickX;
        this.brickY = ts.brickY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean rotate(int n) {
        for (int i = 0; i < n; ++i) {
            if (!rotateRight()) {
                return false;
            }
        }
        return true;
    }

    public boolean rotateRight() {
        if (this.brick != null) {
            brick.rotateRight();
            int result = testBrick();
            if (result == 1) {
                //can't rotate here
                brick.rotateLeft();
                return false;
            }
            if (result == 3) {
                //up
                while (testBrick() == 3) {
                    --brickY;
                }
            }
            if (result == 2) {
                //right
                while (testBrick() == 2) {
                    --brickX;
                }
            }
            return true;
        }
        return false;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        this.brickX = (this.width - Brick.WIDTH) / 2;
        this.brickY = 0;
    }

    public int get(int x, int y) {
        if (x == width) {
            return 2;
        }
        if (y == height) {
            return 3;
        }
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return data[x + y * width];
        }
        return 1;
    }

    public void set(int x, int y, int k) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            data[x + y * width] = k;
        }
    }

    public TerixState(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new int[this.width * this.height];
    }

    int testBrick() {
        for (int i = 0; i < Brick.HEIGHT; ++i) {
            for (int j = 0; j < Brick.WIDTH; ++j) {
                int result = brick.data[i * Brick.WIDTH + j] * get(j + brickX, i + brickY);
                if (result != 0) {
                    return result;
                }
            }
        }
        return 0;
    }

    public boolean moveBrick(int x, int y) {
        int lx = brickX;
        int ly = brickY;
        brickX = x;
        brickY = y;
        if (testBrick() != 0) {
            brickX = lx;
            brickY = ly;
            return false;
        }
        return true;
    }

    public boolean moveLeft() {
        return moveBrick(brickX - 1, brickY);
    }

    public boolean moveRight() {
        return moveBrick(brickX + 1, brickY);
    }

    public boolean moveDown() {
        return moveBrick(brickX, brickY + 1);
    }

    public void moveDownDirect() {
        while (moveDown());
    }

    boolean checkRow(int i, int n) {
        for (int j = 0; j < width; ++j) {
            if (data[i * width + j] != n) {
                return false;
            }
        }
        return true;
    }

    void moveLine(int a, int b) {
        for (int i = 0; i < width; ++i) {
            data[a * width + i] = data[b * width + i];
            data[b * width + i] = 0;
        }
    }

    public int boom() {
        int count = 0;
        for (int i = height - 1; i >= 0; --i) {
            if (checkRow(i, 1)) {
                //boom
                ++count;
                for (int j = i; j > 0; --j) {
                    moveLine(j, j - 1);
                }
                ++i;
            }
        }
        return count;
    }

    public void fusion() {
        for (int i = 0; i < Brick.HEIGHT; ++i) {
            for (int j = 0; j < Brick.WIDTH; ++j) {
                if (brick.data[i * Brick.WIDTH + j] != 0) {
                    set(j + brickX, i + brickY, 1);
                }
            }
        }
        brick = null;
    }
}
