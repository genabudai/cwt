package com.game.cwtetris.ui;

import android.graphics.Paint;
import android.graphics.RectF;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.CellPoint;
import com.game.cwtetris.data.GameState;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by gena on 1/12/2017.
 */

public class WinnerSceen {

    private final List<FlyingCube> flyingCubes = new ArrayList<FlyingCube>();
    private Long time = Calendar.getInstance().getTimeInMillis();

    public void prepareFlyingBlocks ( CanvasView view ) {
        Set<CellPoint>  cells = view.getData().getCells();
        int minY = 10;
        for (CellPoint cell:cells) {
            minY = (cell.y<minY)?cell.y:minY;
        }
        for (CellPoint cell:cells) {
            if (cell.value == 100 ) continue;
            int dX = 0;
            if (!cells.contains(new CellPoint(cell.x-1, cell.y))) {
                dX = -1;
            } else if (!cells.contains(new CellPoint(cell.x+1, cell.y))) {
                dX = 1;
            }
            flyingCubes.add(new FlyingCube( view.getGrid().getGridCellCenter(view, cell.x, cell.y),
                    cell.value, dX, (minY==cell.y)?-2:cell.y+1));
        }
        Collections.sort( flyingCubes, new Comparator<FlyingCube>() {
            public int compare(FlyingCube o1, FlyingCube o2) {
                return (o2.y*10 + o2.x) - (o1.y*10 + o1.x);
            }
        });

//        for (CellPoint cell:view.getData().getCells()) {
//            int dX=0;
//            if (!cells.contains(new CellPoint(cell.x-1, cell.y))) {
//                dX = -1 * FlyingCube.step;
//            } else if (!cells.contains(new CellPoint(cell.x+1, cell.y))) {
//                dX = FlyingCube.step;
//            }
//            int dY=0;
//            if (!cells.contains(new CellPoint(cell.x, cell.y-1))) {
//                dY = -1 * FlyingCube.step;
//            } else if (!cells.contains(new CellPoint(cell.x, cell.y+1))) {
//                dY = FlyingCube.step;
//            }
//
//            flyingCubes.add(new FlyingCube( view.getGrid().getGridCellCenter(view, cell.x, cell.y), cell.value, dX, dY));
//        }
    }

    public void draw(CanvasView view, Paint paintFill){
        if (view.getState() != GameState.GAME) return;
        updateFlyingBlocks( view );
        int gap = view.cellSize / 25;
        for (FlyingCube cube : flyingCubes) {
            int r = view.cellSize / 2;
            RectF rect = new RectF(cube.x - r + gap, cube.y - r + gap, cube.x + r - gap, cube.y + r - gap);
            paintFill.setColor(ImageCache.getColor(cube.color));
            view.mCanvas.drawRoundRect(rect, 15, 15, paintFill);
        }
    }

    private void updateFlyingBlocks( CanvasView view ) {
        if (flyingCubes.size() == 0) {
            prepareFlyingBlocks( view );
        }
        if (Calendar.getInstance().getTimeInMillis() - time > 15) {
            time = Calendar.getInstance().getTimeInMillis();
            int r = view.cellSize / 2;
            for (FlyingCube cube : flyingCubes) {
                cube.update(getNeighbor(cube, view.cellSize), 0 + r, view.width - r, view.cellSize + r, view.height - r);
            }
        }
        view.postInvalidate();
    }

//    public void update(int left, int right, int top, int bottom, Set<FlyingCube> cubes, int cellSize) {
//        Set<FlyingCube> tmpCubes = new HashSet<>();
//        tmpCubes.addAll(cubes);
//        FlyingCube neighbor = getNeighbor(tmpCubes, cellSize);
////        undateCube (this, neighbor == null, left, right, top, bottom);
//        tmpCubes.remove(this);
////        undateCube (neighbor, neighbor == null, left, right, top, bottom);
//    }

    private boolean getNeighbor(FlyingCube currentCube, int cellSize) {
        for (FlyingCube cube : flyingCubes) {
            if (!currentCube.equals(cube) &&
                    Math.abs(currentCube.x - cube.x) < cellSize &&
                    Math.abs(currentCube.y - cube.y) < cellSize ) {
                return true;
            }
        }
        return false;
    }

    public void clear(){
        flyingCubes.clear();
    }
}
