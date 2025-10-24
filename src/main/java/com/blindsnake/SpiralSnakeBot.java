package com.blindsnake;

import java.util.Objects;

public final class SpiralSnakeBot {

    private final GameIO io;

    public SpiralSnakeBot(GameIO io) {
        this.io = Objects.requireNonNull(io, "io");
    }


    // Runs until win or the given cap of button presses is reached.

    public boolean playWithCap(long maxSteps) {
        long used = 0L;
        long legLenUp = 1L;
        long legLenRight = 1L;
        long legLenDown = 2L;
        long legLenLeft = 2L;

        while (used < maxSteps) {
            // UP
            if (run(Direction.UP, legLenUp, maxSteps, used)) return true;
            used += legLenUp;
            if (used >= maxSteps) return false;

            // RIGHT
            if (run(Direction.RIGHT, legLenRight, maxSteps, used)) return true;
            used += legLenRight;
            if (used >= maxSteps) return false;

            // DOWN
            if (run(Direction.DOWN, legLenDown, maxSteps, used)) return true;
            used += legLenDown;
            if (used >= maxSteps) return false;

            // LEFT
            if (run(Direction.LEFT, legLenLeft, maxSteps, used)) return true;
            used += legLenLeft;
            if (used >= maxSteps) return false;

            // increment by +2 each cycle
            legLenUp += 2L;
            legLenRight += 2L;
            legLenDown += 2L;
            legLenLeft += 2L;
        }
        return false;
    }

    private boolean run(Direction direction, long steps, long cap, long usedSoFar) {
        long canDo = Math.min(steps, Math.max(0L, cap - usedSoFar));
        for (long i = 0; i < canDo; i++) {
            if (io.sendSignal(direction)) {
                return true;
            }
        }
        return false;
    }
}


