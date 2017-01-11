package com.game.cwtetris.ui.shape;

/**
 * Created by gena on 12/15/2016.
 */

public enum ShapeOrientation {
    O_0(0),
    O_90(1),
    O_180(2),
    O_270(3);

    private final int value;

    private ShapeOrientation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ShapeOrientation fromInt(int x) {
        switch(x) {
            case 1:
                return O_90;
            case 2:
                return O_180;
            case 3:
                return O_270;
        }
        return O_0;
    }
}
