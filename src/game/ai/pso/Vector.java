/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game.ai.pso;

import java.util.Random;

/**
 *
 * @author wssccc
 */
public class Vector {

    float[] array;
    int dimision;

    public Vector(int dimision) {
        this.dimision = dimision;
        array = new float[dimision];
    }

    public float distance(Vector v) {
        float d2 = 0;
        for (int i = 0; i < dimision; ++i) {
            d2 += (v.array[i] - array[i]) * (v.array[i] - array[i]);
        }
        return (float) Math.sqrt(d2);
    }

    public Vector(float[] data) {
        this.dimision = data.length;
        array = data;
    }

    public void assgin(Vector v) {
        for (int i = 0; i < dimision; ++i) {
            array[i] = v.array[i];
        }
    }

    public void randomInit(Random random) {
        for (int i = 0; i < dimision; ++i) {
            array[i] = random.nextFloat();
        }
    }

    public void add(Vector v) {
        for (int i = 0; i < dimision; ++i) {
            array[i] += v.array[i];
        }
    }

    public void mul(float f) {
        for (int i = 0; i < dimision; ++i) {
            array[i] *= f;
        }
    }

    public void sub(Vector v) {
        for (int i = 0; i < dimision; ++i) {
            array[i] -= v.array[i];
        }
    }

    public float length() {
        float l2 = 0;
        for (int i = 0; i < dimision; ++i) {
            l2 += array[i] * array[i];
        }
        return (float) Math.sqrt(l2);
    }

    @Override
    public String toString() {
        String str = "Vector{";
        for (int i = 0; i < dimision; ++i) {
            str += array[i] + ",";
        }
        str += '}';
        return str;
    }
}
