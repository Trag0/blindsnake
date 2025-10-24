package com.blindsnake;

public final class Main {
    public static void main(String[] args) {
        int A;
        if (args.length >= 1) {
            A = Integer.parseInt(args[0]);
        } else {
            A = 1000;
        }
        int B;
        if (args.length >= 2) {
            B = Integer.parseInt(args[1]);
        } else {
            B = 1000;
        }
        long seed;
        if (args.length >= 3) {
            seed = Long.parseLong(args[2]);
        } else {
            seed = System.currentTimeMillis();
        }

        Engine engine = new Engine(A, B, seed);
        BlindSnakeBot bot = new BlindSnakeBot(engine);

        boolean won = bot.playWithCap(engine.getCap());
        System.out.println("Board: " + A + "x" + B + "  cap=" + engine.getCap());
        String resultStr;
        if (won) {
            resultStr = "WIN";
        } else {
            resultStr = "LOSS (cap)";
        }
        System.out.println("Result: " + resultStr + " after " + engine.getPresses() + " presses");
        if (!won) {
            System.out.println("Note: This (30,1) drift misses boards where gcd(A,B) has a prime ≥ 7.");
        }
    }
}



