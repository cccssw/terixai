/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai;

import game.Brick;
import game.TerixState;

/**
 *
 * @author wssccc
 */
public class TerixAi {

    public int weightVerticalHole = -3;
    public int weightHeight = -6;
    public int weightHorizonalSingleHole = 0;
    public int weightDeadHole = -5;
    public int weightBoom = 1000;
    public int weightDepth = 4;
    public int weightFit = 2;
    public int weightContinuous = 1;
    final Brick nextBrick;

    public TerixAi(Brick nextBrick) {
        this.nextBrick = nextBrick;
    }

    void flood(TerixState ts, int x, int y, int ori, int tag) {
        if (ts.get(x, y) == ori) {
            ts.set(x, y, tag);
            flood(ts, x + 1, y, ori, tag);
            flood(ts, x - 1, y, ori, tag);
            flood(ts, x, y + 1, ori, tag);
            flood(ts, x, y - 1, ori, tag);
        }
    }

    int countDeadHole(TerixState ts) {
        int count = 0;
        flood(ts, ts.getWidth() - 1, 0, 0, 5);
        for (int x = 0; x < ts.getWidth(); ++x) {
            for (int y = 0; y < ts.getHeight(); ++y) {
                if (ts.get(x, y) == 0) {
                    ++count;
                }
            }
        }

        flood(ts, ts.getWidth() - 1, 0, 5, 0);

        return count;
    }

    int countVerticalHole(TerixState ts) {
        boolean begin;
        int count = 0;
        for (int x = 0; x < ts.getWidth(); ++x) {
            begin = false;
            for (int y = 0; y < ts.getHeight(); ++y) {
                int d = ts.get(x, y);
                if (d == 0) {
                    if (begin) {
                        ++count;
                    }
                } else {
                    begin = true;
                }
            }
        }
        return count;
    }

    int countHeight(TerixState ts) {
        for (int y = 0; y < ts.getHeight(); ++y) {
            for (int x = 0; x < ts.getWidth(); ++x) {
                if (ts.get(x, y) != 0) {
                    return ts.getHeight() - y;
                }
            }
        }
        return 0;
    }

    int countHorizonalSingleHole(TerixState ts) {
        int count = 0;
        for (int y = 0; y < ts.getHeight(); ++y) {
            for (int x = 0; x < ts.getWidth(); ++x) {
                if (ts.get(x, y) == 0 && ts.get(x - 1, y) != 0 && ts.get(x + 1, y) != 0) {
                    ++count;
                }
            }
        }
        return count;
    }

    public int eval(TerixState ts) {

        int countheight = countHeight(ts);
        int counthsh = countHorizonalSingleHole(ts);
        int vholes = countVerticalHole(ts);
        int deadhole = countDeadHole(ts);
//
//        System.out.println("----------------------------");
//        System.out.println("height " + countheight);
//        System.out.println("h single hole " + counthsh);
//        System.out.println("vholes " + vholes);
//        System.out.println("dead hole" + deadhole);
//        System.out.println("boom " + ts.lastBoomCount);
//        System.out.println("depth " + ts.lastBrickDepth);
//
//        System.out.println("----------------------------");

        return ts.lastContinuous * weightContinuous + ts.lastFitVal * weightFit + countheight * weightHeight + counthsh * weightHorizonalSingleHole + vholes * weightVerticalHole + deadhole * weightDeadHole + weightBoom * ts.lastBoomCount + weightDepth * ts.lastBrickDepth;
    }

    public TerixAiCommand findSteps(TerixState ts, boolean level2) {
        int maxval = Integer.MIN_VALUE;
        int maxx = 0;
        int maxr = 0;
        TerixState nts = new TerixState(ts.getWidth(), ts.getHeight());
        for (int x = 0; x < ts.getWidth(); ++x) {
            for (int r = 0; r < 4; ++r) {
                nts.copyState(ts);
                if (nts.rotate(r)) {
                    //rotate ok
                    if (nts.moveBrick(x, 0)) {
                        //can be moved
                        nts.moveDownDirect();
                        nts.step();
                        int val;
                        if (!level2) {
                            nts.setBrick(nextBrick);
                            val = findSteps(nts, true).val + eval(nts);
                        } else {
                            val = eval(nts);
                        }

                        System.out.println("x=" + x + " r=" + r + " val=" + val + " fit= " + nts.lastFitVal + "depth =" + nts.lastBrickDepth);
                        if (val > maxval) {
                            //save steps
                            maxx = x;
                            maxr = r;
                            maxval = val;
                        }
                    }
                }
            }
        }
        System.out.println("maxx=" + maxx + " maxr=" + maxr + " maxval=" + maxval);
        System.out.println("-------------------------------------------------------");
        return new TerixAiCommand(maxx, maxr, maxval);
    }
}
