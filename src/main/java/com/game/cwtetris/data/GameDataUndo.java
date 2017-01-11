package com.game.cwtetris.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gena on 12/29/2016.
 */

public class GameDataUndo implements Serializable{

    private Set<CellPoint> cells = new HashSet<CellPoint>();
    private Set<ShapeData> shapes = new HashSet<>();

    public GameDataUndo(Set<CellPoint> cells, Set<ShapeData> shapes) {
        this.cells = cells;
        this.shapes = shapes;
    }

    public Set<CellPoint> getCells() {
        return cells;
    }

    public Set<ShapeData> getShapes() {
        return shapes;
    }

}
