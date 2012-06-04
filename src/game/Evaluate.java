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
public class Evaluate {

    TerixState ts;

    public Evaluate(TerixState ts) {
        this.ts = ts;
    }

    int countColHeight(int x1) {
        for (int i = 0; i < ts.height; ++i) {
            if (ts.get(x1, i) != 0) {
                return ts.height - i;
            }
        }
        return 0;
    }

    int countFall() {

        int count = 0;
        int lastHeight = countColHeight(0);
        for (int i = 1; i < ts.width; ++i) {
            int curh = countColHeight(i);
            if (Math.abs(curh - lastHeight) > 2) {
                count += Math.abs(curh - lastHeight);
            }
            lastHeight = curh;
        }
        return count;
    }

    int countVerticalHole() {
        boolean begin;
        int count = 0;
        for (int x = 0; x < ts.getWidth(); ++x) {
            begin = false;
            for (int y = 0; y < ts.getHeight(); ++y) {
                int d = ts.get(x, y);
                if (d == 0) {
                    if (begin) {
                        ++count;
                        y = ts.getHeight();
                    }
                } else {
                    begin = true;
                }
            }
        }
        return count;
    }

    int countRowContinuous(int row) {
        int blackContinuousCount = 0;
        int sum = 0;
        for (int i = 0; i <= ts.getWidth(); ++i) {
            if (ts.get(i, row) == 1) {
                //black
                ++blackContinuousCount;
            } else {
                //white or wall
                if (blackContinuousCount > 0) {
                    sum += blackContinuousCount * blackContinuousCount;
                    blackContinuousCount = 0;
                }
            }
        }
        return sum;
    }
    //optimize as 1

    int countContinuous() {
        int sum = 0;
        for (int i = 0; i < ts.getHeight(); ++i) {
            sum += countRowContinuous(i);
        }
        return sum;
    }

    int countHeight() {
        for (int y = 0; y < ts.getHeight(); ++y) {
            if (!ts.checkRow(y, 0)) {
                return ts.getHeight() - y;
            }
        }
        return 0;
    }

    int countNearBlank() {
        int[] bdata = ts.brick.getData();
        int nb = 0;
        for (int i = 0; i < Brick.HEIGHT; ++i) {
            for (int j = 0; j < Brick.WIDTH; ++j) {
                if (bdata[i * Brick.WIDTH + j] != 0) {
                    if (ts.get(j + ts.brickX + 1, i + ts.brickY) == 0) {
                        ++nb;
                    }
                    if (ts.get(j + ts.brickX - 1, i + ts.brickY) == 0) {
                        ++nb;
                    }
                    if (ts.get(j + ts.brickX, i + ts.brickY + 1) == 0) {
                        ++nb;
                    }
                    if (ts.get(j + ts.brickX, i + ts.brickY - 1) == 0) {
                        ++nb;
                    }
                }
            }
        }
        return nb;
    }

    public float evalContinuous() {
        int objheight = countHeight();
        int totalc = objheight * ts.width * ts.width;
        return (float) countContinuous() / (float) totalc;
    }

    public float evalHeight() {
        return (float) (ts.height - countHeight()) / (float) ts.height;
    }

    public float evalBrickFit() {
        int ori = ts.brick.countTopPixel();
        int nb = countNearBlank();
        return 1.0f / (float) (nb - ori + 1);
    }

    public float evalVHole() {
        int vc = countVerticalHole();
        return 1.0f / (float) (vc + 1);
    }

    //TODO:
    public float evalFall() {
        int fall = countFall();
        return 1.0f / (float) (fall + 1);
    }
}
