package com.game.cwtetris.ui;

import com.game.cwtetris.data.CellPoint;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.Randomizer;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gena on 1/11/2017.
 */

public class FlyingCube {

    public int color;
    public int x;
    public int y;
    public int dX;
    public int dY;
    private int step = 1;

    public FlyingCube( Point p, int color, int dX, int dY ) {
        this.color = color;
        x = p.x;
        y = p.y;
        this.dX = dX;
        this.dY = dY;
    }


    public void update (boolean isNeighborFound, int left, int right, int top, int bottom) {
        dX = (isNeighborFound || x >= right  || x <= left)? dX*-1:dX;
        dY = (isNeighborFound || y >= bottom || y <= top) ? dY*-1:dY;
        x += dX;
        y += dY;
    }

}
