/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.brick.Brick;

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
    int score;
    boolean cantSet = false;

    public int getScore() {
        return score;
    }

    public int getBrickX() {
        return brickX;
    }

    public Brick getBrick() {
        return brick;
    }

    public int getBrickY() {
        return brickY;
    }

    public boolean isGameOver() {
        return !checkRow(0, 0) || cantSet;
    }

    public void growUp(int n) {
        for (int n_i = 0; n_i < n; ++n_i) {
            for (int i = 1; i < height; ++i) {
                moveLine(i - 1, i);
            }
        }
        int s;
        for (int i = height - n; i < height; ++i) {
            s = i % 2;
            for (int j = 0; j < width; ++j) {

                set(j, i, s);
                s = 1 - s;
            }
        }
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
            this.brick = ts.brick.clone();
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
            if (result != 0) {
                //can't rotate here
                brick.rotateLeft();
                return false;
            }

            return true;
        }
        return false;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        this.brickX = (this.width - Brick.WIDTH) / 2;
        this.brickY = 0;
        if (testBrick() != 0) {
            cantSet = true;
        }
    }

    public int get(int x, int y) {
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
        int[] bdata = brick.getData();
        assert bdata != null;
        for (int i = 0; i < Brick.HEIGHT; ++i) {
            for (int j = 0; j < Brick.WIDTH; ++j) {
                int result = bdata[i * Brick.WIDTH + j] * get(j + brickX, i + brickY);
                if (result != 0) {
                    return result;
                }
            }
        }
        return 0;
    }

    public boolean moveBrick(int x) {
        int ox = brickX;
        if (ox > x) {
            for (int i = ox - 1; i >= x; --i) {
                if (!moveBrick(i, brickY)) {
                    brickX = ox;
                    return false;
                }
            }
            return true;
        } else {
            for (int i = ox + 1; i <= x; ++i) {
                if (!moveBrick(i, brickY)) {
                    brickX = ox;
                    return false;
                }
            }
            return true;
        }
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

    void moveLine(int dst, int src) {
        for (int i = 0; i < width; ++i) {
            data[dst * width + i] = data[src * width + i];
            data[src * width + i] = 0;
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
        score += (count * 100);
        return count;
    }

    public void fusion() {
        int[] bdata = brick.getData();
        for (int i = 0; i < Brick.HEIGHT; ++i) {
            for (int j = 0; j < Brick.WIDTH; ++j) {
                if (bdata[i * Brick.WIDTH + j] != 0) {
                    set(j + brickX, i + brickY, 1);
                }
            }
        }
        brick = null;
    }
}
