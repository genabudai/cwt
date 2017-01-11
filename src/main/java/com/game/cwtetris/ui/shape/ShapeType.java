package com.game.cwtetris.ui.shape;

/**
 * Created by gena on 12/15/2016.
 */

public enum ShapeType {
    S010_111_000(0),
    S100_111_000(1),
    S001_111_000(2),
    S110_010_011(3),
    S110_011_000(4),
    S011_110_000(5),
    S010_111_010(6),
    S100_011_001(7),
    S010_010_010(8),
    S010_011_000(9),
    S010_111_101(10);

    private final int value;

    private ShapeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
