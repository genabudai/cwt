package com.game.cwtetris.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.CellPoint;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.Randomizer;
import com.game.cwtetris.data.ShapeData;
import com.game.cwtetris.data.SoundPlayer;
import com.game.cwtetris.ui.shape.ShapeBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.game.cwtetris.CWTApp.getSoundPlayer;

/**
 * Created by gena on 12/12/2016.
 */

public class Grid implements View.OnTouchListener, IButtonVisibilityProvider {

    private final Paint paintBorder = new Paint();
    private final Paint paintFill = new Paint();
    private ImageElement youWinImage;
    private final List<Shape> shapeElements = new ArrayList<Shape>();
    private final Map<Point, Point> lines = new ArrayMap<Point, Point>();
    private boolean isVisible = true;

    public Grid(CanvasView view)  {
        paintFill.setStyle(Paint.Style.FILL_AND_STROKE);
        paintFill.setColor(Color.BLACK);

        paintBorder.setAntiAlias(true);
        paintBorder.setColor(Color.BLACK);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeJoin(Paint.Join.ROUND);
        paintBorder.setStrokeWidth(4f);

        youWinImage = new ImageElement( ImageCache.getImage("you_win"),
                                        view.getCellCenter(7, 10), view.cellSize * 3 );
    }

    public void prepareGrid( CanvasView view ){
        shapeElements.clear();
        lines.clear();

        Randomizer.refreshRandomColors();
        int i=0;
        for (ShapeData sd :  view.getData().getShapes()) {
            shapeElements.add(ShapeBuilder.getInstance(view, sd.getType(), i*3, 9, i+1));
            i++;
        }


        final Set<CellPoint> cells = view.getData().getCells();
        final int r = view.cellSize / 2;
        for (CellPoint cell : cells){
            Point p = getGridCellCenter(view, cell.x, cell.y);
            lines.put(new Point(p.x - r, p.y - r), new Point(p.x + r, p.y - r));
            if (!cells.contains(new CellPoint(cell.x, cell.y + 1))) {
                lines.put(new Point(p.x - r, p.y + r), new Point(p.x + r, p.y + r));
            }

            lines.put(new Point(p.x - r, p.y - r), new Point(p.x - r, p.y + r));
            if (!cells.contains(new CellPoint(cell.x+1, cell.y))) {
                lines.put(new Point(p.x + r, p.y - r), new Point(p.x + r, p.y + r));
            }
        }
    }

    public void draw(CanvasView view)  {
        for (Map.Entry<Point,Point> line : lines.entrySet()){
            view.mCanvas.drawLine( line.getKey().x, line.getKey().y,
                    line.getValue().x, line.getValue().y,
                    paintBorder);
        }
        int gap = view.cellSize / 25;
        for (CellPoint cellPoint:view.getData().getCells()) {
            Point p = getGridCellCenter(view, cellPoint.x,cellPoint.y);
            int r = view.cellSize / 2;
            RectF rect = new RectF(p.x - r + gap, p.y - r + gap, p.x + r - gap, p.y + r - gap);

            if (cellPoint.value == 100) {
                paintFill.setColor(Color.BLACK);
                view.mCanvas.drawRect(rect, paintFill);
            } else if (cellPoint.value != 0){
//                drawImageInCell(view, "b_" + cellPoint.value, rect);
                paintFill.setColor(ImageCache.getColor(cellPoint.value));
                view.mCanvas.drawRoundRect(rect, 15, 15, paintFill);
            }
        }

        for (Shape shape: shapeElements){
            if (!shape.isTouched()) {
                shape.draw(view);
            }
        }

        Shape ts = getTouchedShape(view);
        if ( ts != null ) {
            ts.draw(view);
        }

        if (isLevelCompleted(view)) {
            youWinImage.draw(view);
        }
    }

    protected void drawImageInCell(CanvasView view, String imgName, RectF r) {
        Bitmap bm = ImageCache.getImage(imgName);
        if (bm == null) return;
        view.mCanvas.drawBitmap(bm, null, r, null);
    }

    protected void drawImage(CanvasView view, int xCell, int yCell, String imgName) {
        Point p = view.getCellCenter(xCell,yCell);
        int r = view.cellSize / 2;
        drawImageInCell(view, imgName, new RectF(p.x - r, p.y - r, p.x + r, p.y + r));
    }

    public Point getGridCellCenter(CanvasView view, int x, int y)  {
        return view.getCellCenter(x, y+1);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isVisible()) return false;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Shape shape = getTouchedShape((CanvasView)v);
            putShapeToGrid((CanvasView) v, shape);
        }

        for (Shape shape: shapeElements){
            shape.onTouch(v, event);
        }

        return false;
    }

    private Shape getTouchedShape(CanvasView view) {
        for (Shape shape : shapeElements) {
            if (shape.isTouched() && shape.isVisible(view))
                return shape;
        }
        return null;
    }

    private boolean putShapeToGrid(CanvasView view, Shape shape){
        if (shape == null) return false;
        Point p = shape.getMovingXY();
        Point cPoint = view.getCellByPoint(p.x, p.y);
        Set<CellPoint> targetCells = new HashSet<CellPoint>();
        for (CellPoint cell : shape.getCells()) {
            CellPoint targetCell = view.getData().getCell(cell.x + cPoint.x, cell.y + cPoint.y);
            if ( targetCell != null && targetCell.value == 0 ) {
                targetCells.add(targetCell);
            } else {
                return false;
            }
        }
        view.getData().saveUndoStep();
        for (CellPoint targetCell : targetCells) {
            targetCell.value = shape.getColor();
        }
        shape.decCount(view);
        getSoundPlayer().drop();
        return true;
    }

    public boolean isLevelCompleted(CanvasView view) {
        for (CellPoint cell : view.getData().getCells()) {
            if (cell.value == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public boolean isButtonVisible(ButtonType buttonType) {
        return isVisible();
    }
}
