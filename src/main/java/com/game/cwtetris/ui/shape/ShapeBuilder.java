package com.game.cwtetris.ui.shape;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.ShapeData;
import com.game.cwtetris.ui.Shape;

/**
 * Created by gena on 12/15/2016.
 */

public class ShapeBuilder {

    public static Shape getInstance(CanvasView view, ShapeType shapeType, int xCell, int yCell, int color){
        switch (shapeType) {
            case S010_111_000: return new Shape_010_111_000(view, shapeType, xCell, yCell, color);
            case S100_111_000: return new Shape_100_111_000(view, shapeType, xCell, yCell, color);
            case S001_111_000: return new Shape_001_111_000(view, shapeType, xCell, yCell, color);
            case S110_010_011: return new Shape_110_010_011(view, shapeType, xCell, yCell, color);
            case S110_011_000: return new Shape_110_011_000(view, shapeType, xCell, yCell, color);
            case S011_110_000: return new Shape_011_110_000(view, shapeType, xCell, yCell, color);
            case S010_111_010: return new Shape_010_111_010(view, shapeType, xCell, yCell, color);
            case S100_011_001: return new Shape_100_011_001(view, shapeType, xCell, yCell, color);
            case S010_010_010: return new Shape_010_010_010(view, shapeType, xCell, yCell, color);
            case S010_011_000: return new Shape_010_011_000(view, shapeType, xCell, yCell, color);
            case S010_111_101: return new Shape_010_111_101(view, shapeType, xCell, yCell, color);
        }
        return null;
    };
}
