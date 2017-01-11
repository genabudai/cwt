package com.game.cwtetris.data;

import com.game.cwtetris.ui.shape.ShapeOrientation;
import com.game.cwtetris.ui.shape.ShapeType;

import java.io.Serializable;

/**
 * Created by gena on 12/15/2016.
 */

public class ShapeData implements Serializable{

    private ShapeType type;
    private ShapeOrientation orientation;
    private int count;

    public ShapeData(ShapeType type, int count) {
        this(type, ShapeOrientation.O_0, count);
    }

    public ShapeData(ShapeType type, ShapeOrientation orientation, int count) {
        this.type = type;
        this.orientation = orientation;
        this.count = count;
    }

    public ShapeType getType() {
        return type;
    }

    public ShapeOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(ShapeOrientation orientation) {
        this.orientation = orientation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void decCount() {
        this.count--;
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return type.getValue();
    }
}
