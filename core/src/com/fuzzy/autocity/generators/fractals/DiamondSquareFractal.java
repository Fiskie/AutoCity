package com.fuzzy.autocity.generators.fractals;

import java.util.Random;

/**
 * Implements the diamond-square algorithm.
 */
public class DiamondSquareFractal {
    /**
     * Roughness should be kept to a really low number.
     * If it is too high it will not generate decent algos.
     */
    private double roughness = 0.01;
    private int size = 16;
    private Random random;
    private Double[][] map;
    private Double topLeftValue;
    private Double topRightValue;
    private Double bottomLeftValue;
    private Double bottomRightValue;

    public DiamondSquareFractal() {
        random = new Random();
    }

    public int getSize() {
        return size;
    }

    /**
     * Sets the fractal's size to the specified size.
     * For best results, the inputted number should be be a square of two.
     *
     * @param size the size of the fractal
     */
    public void setSize(int size) {
        int internalSize = 2;

        while (internalSize < size) {
            internalSize *= 2;
        }

        this.size = internalSize;
    }

    public double getRoughness() {
        return roughness;
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    /**
     * Generate the fractal.
     *
     * @return a 2D array of Double wrappers.
     */
    public Double[][] generate() {
        map = new Double[size + 1][size + 1];

        // Set the values of each corner to 1 minus our variation
        map[0][0] = 0.5 + getDeviation();
        map[0][size] = 0.5 + getDeviation();
        map[size][0] = 0.5 + getDeviation();
        map[size][size] = 0.5 + getDeviation();

        this.divide(size);
        this.truncate();

        return map;
    }

    private void truncate() {
        for (int x = 0; x <= size; x++) {
            for (int y = 0; y <= size; y++) {
                map[x][y] = Math.min(1, Math.max(0, map[x][y]));
            }
        }
    }

    private void divide(double sub) {
        double half = sub / 2;
        double scale = roughness * sub;

        if (half < 1) {
            return;
        }

        // Square
        for (double y = half; y < size; y += sub) {
            for (double x = half; x < size; x += sub) {
                square((int) x, (int) y, (int) half, random.nextDouble() * scale * 2 - scale);
            }
        }

        // Diamond
        for (double y = 0; y <= size; y += half) {
            for (double x = (y + half) % sub; x <= size; x += sub) {
                diamond((int) x, (int) y, (int) half, Math.random() * scale * 2 - scale);
            }
        }

        this.divide(sub / 2);
    }

    private void square(int x, int y, int sub, double offset) {
        Double[] averages = {this.get(x + sub, y - sub), this.get(x - sub, y + sub), this.get(x - sub, y - sub), this.get(x + sub, y + sub)};

        map[x][y] = this.average(averages) + offset;
    }

    private void diamond(int x, int y, int sub, double offset) {
        Double[] averages = {this.get(x, y - sub), this.get(x, y + sub), this.get(x - sub, y), this.get(x + sub, y)};

        map[x][y] = this.average(averages) + offset;
    }

    private double average(Double[] nums) {
        double sum = 0;
        int count = 0;

        for (Double num : nums) {
            if (num == null) {
                continue;
            }

            sum += num;
            count++;
        }
        return sum / count;
    }

    private Double get(int x, int y) {
        try {
            return map[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private double getDeviation() {
        return (random.nextInt(2) == 0 ? random.nextDouble() * this.roughness : 0 - random.nextDouble() * this.roughness);
    }
}
