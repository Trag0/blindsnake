package com.blindsnake;

import java.util.Random;

public final class Benchmark {

    public static void main(String[] args) {
        int trials;
        if (args.length >= 1) {
            trials = Integer.parseInt(args[0]);
        } else {
            trials = 100;
        }
        long seed;
        if (args.length >= 2) {
            seed = Long.parseLong(args[1]);
        } else {
            seed = 12345L;
        }
        int sMax;
        if (args.length >= 3) {
            sMax = Integer.parseInt(args[2]);
        } else {
            sMax = 50000; // cap S to keep runs quick
        }

        Random rnd = new Random(seed);

        Stats driftStats = new Stats();
        Stats spiralStats = new Stats();

        for (int t = 0; t < trials; t++) {
            int[] dims = pickDims(rnd, sMax);
            int A = dims[0];
            int B = dims[1];
            long s1 = rnd.nextLong();
            long s2 = s1; 

            // initital approach
            Engine e1 = new Engine(A, B, s1);
            BlindSnakeBot drift = new BlindSnakeBot(e1);
            boolean w1 = drift.playWithCap(e1.getCap());
            driftStats.record(w1, e1.getPresses());

            // Spiral
            Engine e2 = new Engine(A, B, s2);
            SpiralSnakeBot spiral = new SpiralSnakeBot(e2);
            boolean w2 = spiral.playWithCap(e2.getCap());
            spiralStats.record(w2, e2.getPresses());
        }

        System.out.println("Trials: " + trials + "  Smax=" + sMax);
        System.out.println("Drift (30,1): winRate=" + pct(driftStats.wins, trials) + "% avgStepsOnWins=" + avg(driftStats.stepsOnWins, driftStats.wins));
        System.out.println("Spiral:       winRate=" + pct(spiralStats.wins, trials) + "% avgStepsOnWins=" + avg(spiralStats.stepsOnWins, spiralStats.wins));
    }

    private static int[] pickDims(Random rnd, int sMax) {
        // Choose A and B from floor(sMax) to ensure A*B are always less than sMax.
        int A = 1 + rnd.nextInt(sMax);
        int maxB = Math.max(1, sMax / A);
        int B = 1 + rnd.nextInt(maxB);
        return new int[] { A, B };
    }

    private static String pct(long wins, long trials) {
        if (trials == 0) return "0.0";
        double v = (100.0 * wins) / trials;
        return String.format("%.1f", v);
    }

    private static String avg(long sum, long count) {
        if (count == 0) return "-";
        double v = (double) sum / count;
        return String.format("%.0f", v);
    }

    private static final class Stats {
        long wins;
        long stepsOnWins;

        void record(boolean win, long steps) {
            if (win) {
                wins++;
                stepsOnWins += steps;
            }
        }
    }
}


