package Helpers;

import java.util.Random;

public class Randomiser {
    Random r;

    public Randomiser() {
        r = new Random();
    }

    public double getDouble(double min, double max) {
        return r.nextDouble() * (max - min) + min;
    }

    public int getInt(int min, int max) {
        return r.nextInt(max - min) + min;
    }
}
