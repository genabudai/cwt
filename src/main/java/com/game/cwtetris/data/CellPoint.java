package com.game.cwtetris.data;

import java.io.Serializable;

/**
 * Created by gena on 12/13/2016.
 */

public class CellPoint extends Point implements Serializable{

    public int value = 0;

    public CellPoint(int x, int y) {
        this(x, y, 0);
    }

    public CellPoint(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        return x + y * 11;
    }
}
