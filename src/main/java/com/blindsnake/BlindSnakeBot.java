package com.blindsnake;

import java.util.Objects;


public final class BlindSnakeBot {

    private static final int RIGHT_RUN = 30; // 30 RIGHTs, then 1 DOWN

    private final GameIO io;

    public BlindSnakeBot(GameIO io) {
        this.io = Objects.requireNonNull(io, "io");
    }

    //Running until the engine reports the apple was eaten. This is an infinite loop if the engine never returns true

    public void play() {
        while (true) {
            if (performMacroOnce()) {
                return;
            }
        }
    }

    //checking if it was within the boundaries
    public boolean playWithCap(long maxSteps) {
        long used = 0L;
        while (used < maxSteps) {
            // 30 x RIGHT
            for (int i = 0; i < RIGHT_RUN; i++) {
                used++;
                if (io.sendSignal(Direction.RIGHT)) {
                    return true;
                }
                if (used >= maxSteps) {
                    return false;
                }
            }
            // DOWN
            used++;
            if (io.sendSignal(Direction.DOWN)) {
                return true;
            }
        }
        return false;
    }

    private boolean performMacroOnce() {
        for (int i = 0; i < RIGHT_RUN; i++) {
            if (io.sendSignal(Direction.RIGHT)) {
                return true;
            }
        }
        return io.sendSignal(Direction.DOWN);
    }
}


