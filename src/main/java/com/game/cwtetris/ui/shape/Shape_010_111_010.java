package com.game.cwtetris.ui.shape;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.CellPoint;
import com.game.cwtetris.ui.Shape;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gena on 12/14/2016.
 */

public class Shape_010_111_010 extends Shape {

    public Shape_010_111_010(CanvasView view, ShapeType shapeType, int xCell, int yCell, int color) {
        super(view, shapeType, xCell, yCell, color);
    }

    protected List<CellPoint> getShapeElement(ShapeOrientation orientation){
        int color = getOrientationColor(0);

        return Arrays.asList( new CellPoint(1, 0, color),
                              new CellPoint(0, 1, color),
                              new CellPoint(1, 1, color),
                              new CellPoint(2, 1, color),
                              new CellPoint(1, 2, color));
    }

}
