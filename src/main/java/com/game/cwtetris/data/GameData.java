package com.game.cwtetris.data;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.R;
import com.game.cwtetris.ui.Shape;
import com.game.cwtetris.ui.shape.ShapeOrientation;
import com.game.cwtetris.ui.shape.ShapeType;

/**
 * Created by gena on 12/13/2016.
 */

public class GameData implements Serializable {

    private Set<CellPoint> cells = new HashSet<CellPoint>();
    private Set<ShapeData> shapes = new HashSet<ShapeData>();
    private final ArrayDeque<GameDataUndo> undo = new ArrayDeque<GameDataUndo>();

    public Set<CellPoint> getCells() {
        return cells;
    }

    public CellPoint getCell(int xCell, int yCell) {
        for ( CellPoint cell : cells ) {
            if ( cell.x == xCell && cell.y == yCell )
                return cell;
        }
        return null;
    }


    public void saveUndoStep(){
        Set<CellPoint> undoCells = new HashSet<CellPoint>();
        for (CellPoint cell : this.cells) {
            undoCells.add(new CellPoint(cell.x, cell.y, cell.value));
        }

        Set<ShapeData> undoShapes = new HashSet<>();
        for (ShapeData shape : this.shapes) {
            undoShapes.add(new ShapeData(shape.getType(), shape.getOrientation(), shape.getCount()));
        }
        undo.push(new GameDataUndo(undoCells, undoShapes));
    }

    public boolean undo(){
        if (undo.size() <= 0) return false;
        GameDataUndo gameDataUndo = undo.pop();
        this.cells = gameDataUndo.getCells();
        for (ShapeData usd : gameDataUndo.getShapes()) {
            for (ShapeData csd : this.shapes) {
                if (usd.equals(csd)){
                    csd.setCount(usd.getCount());
                    break;
                }
            }
        }
        return true;
    }

    public Set<ShapeData> getShapes() {
        return shapes;
    }

    public ShapeData getShape(ShapeType shapeType) {
        for (ShapeData shape : this.shapes) {
            if (shape.getType() == shapeType){
                return shape;
            }
        }
        return null;
    }
}
