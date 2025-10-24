package com.blindsnake;

import java.util.Random;

public final class Engine implements GameIO {

    private final int width;
    private final int height;
    private final long cap;
    private int snakeX;
    private int snakeY;
    private int appleX;
    private int appleY;
    private long presses;
    private boolean won;

    public Engine(int width, int height, long seed) {
        if (width <= 0 || height <= 0) throw new IllegalArgumentException("Positive sizes required");
        this.width = width;
        this.height = height;
        this.cap = 35L * width * (long) height;

        Random rnd = new Random(seed);
        snakeX = rnd.nextInt(width);
        snakeY = rnd.nextInt(height);

        do {
            appleX = rnd.nextInt(width);
            appleY = rnd.nextInt(height);
        } while (appleX == snakeX && appleY == snakeY);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public long getCap() { return cap; }
    public long getPresses() { return presses; }
    public boolean isWon() { return won; }
    public int getSnakeX() { return snakeX; }
    public int getSnakeY() { return snakeY; }
    public int getAppleX() { return appleX; }
    public int getAppleY() { return appleY; }

    @Override
    public boolean sendSignal(Direction direction) {
        if (won) return true; 
        if (presses >= cap) return false; // cap reached means lost

        presses++;

        switch (direction) {
            case UP:
                snakeY = wrap(snakeY - 1, height);
                break;
            case DOWN:
                snakeY = wrap(snakeY + 1, height);
                break;
            case LEFT:
                snakeX = wrap(snakeX - 1, width);
                break;
            case RIGHT:
                snakeX = wrap(snakeX + 1, width);
                break;
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }

        if (snakeX == appleX && snakeY == appleY) {
            won = true;
            return true;
        }
        return false;
    }

    private static int wrap(int v, int mod) {
        int r = v % mod;
        if (r < 0) {
            return r + mod;
        } else {
            return r;
        }
    }
}


