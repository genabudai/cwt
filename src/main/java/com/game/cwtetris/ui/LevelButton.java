package com.game.cwtetris.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.CellPoint;
import com.game.cwtetris.data.GameState;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.UserSettings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gena on 1/7/2017.
 */

public class LevelButton extends ImageElement implements View.OnTouchListener {

    private int levelNumber;
    private int levelColor;
    private int cellSize;
    private boolean isTwoDigit = false;
    private final Paint paintFill = new Paint();
    private final Paint paintBorder = new Paint();
    private Set<CellPoint> cells;
    private final Set<CellPoint> selectedCells = new HashSet<>();

    public LevelButton(Point p, int cellSize, int levelNumber, int color) {
        super(null, p, cellSize * 9);
        this.levelNumber = levelNumber;
        this.cellSize = cellSize;
        this.levelColor = color;
        isTwoDigit = (levelNumber > 9);
        width = cellSize * (isTwoDigit?11:7);

        cells = LevelButtonNumber.getNumberCells(levelNumber, color);

        paintFill.setStyle(Paint.Style.FILL);

        paintBorder.setAntiAlias(true);
        paintBorder.setColor(Color.BLACK);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeJoin(Paint.Join.ROUND);
        paintBorder.setStrokeWidth(4f);
    }

    @Override
    public void draw(CanvasView view) {
        for (int i = 0; i < 8; i++) {
            int x1 = x - cellSize/2;
            int y1 = y + cellSize / 2 + cellSize * i;
            view.mCanvas.drawLine( x1, y1, x1 + width, y1, paintBorder);
        }
        view.mCanvas.drawLine( x - cellSize/2, y - cellSize/2,
                x + cellSize + cellSize/2, y - cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + width - 2 * cellSize - cellSize/2, y - cellSize/2,
                x + width - cellSize/2, y - cellSize/2, paintBorder);

        view.mCanvas.drawLine( x - cellSize/2, y + height - cellSize/2,
                x + cellSize + cellSize/2, y + height - cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + width - 2 * cellSize - cellSize/2, y + height - cellSize/2,
                x + width - cellSize/2, y + height - cellSize/2, paintBorder);

// y
        view.mCanvas.drawLine( x - cellSize/2, y - cellSize/2,
                x - cellSize/2, y + cellSize + cellSize/2, paintBorder);
        view.mCanvas.drawLine( x - cellSize/2, y + cellSize * 2 + cellSize/2,
                x - cellSize/2, y + cellSize * 5 + cellSize/2, paintBorder);
        view.mCanvas.drawLine( x - cellSize/2, y + cellSize * 6 + cellSize/2,
                x - cellSize/2, y + cellSize * 8 + cellSize/2, paintBorder);

        view.mCanvas.drawLine( x  + width - cellSize/2, y - cellSize/2,
                x + width - cellSize/2, y + cellSize + cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + width - cellSize/2, y + cellSize * 2 + cellSize/2,
                x + width - cellSize/2, y + cellSize * 5 + cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + width - cellSize/2, y + cellSize * 6 + cellSize/2,
                x + width - cellSize/2, y + cellSize * 8 + cellSize/2, paintBorder);

        view.mCanvas.drawLine( x + cellSize/2, y - cellSize/2,
                x + cellSize/2, y + height - cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + cellSize + cellSize/2, y - cellSize/2,
                x + cellSize + cellSize/2, y + height - cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + cellSize * 2 + cellSize/2, y + cellSize/2,
                x + cellSize * 2 + cellSize/2, y + height - cellSize - cellSize/2, paintBorder);

        view.mCanvas.drawLine( x + width - cellSize - cellSize/2, y - cellSize/2,
                x + width - cellSize - cellSize/2, y + height - cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + width - cellSize * 2  - cellSize/2, y - cellSize/2,
                x + width - cellSize * 2 - cellSize/2, y + height - cellSize/2, paintBorder);
        view.mCanvas.drawLine( x + width - cellSize * 3  - cellSize/2, y + cellSize/2,
                x + width - cellSize * 3 - cellSize/2, y + height - cellSize - cellSize/2, paintBorder);


        if (isTwoDigit) {
//x
            view.mCanvas.drawLine( x + cellSize * 3 + cellSize/2, y - cellSize/2,
                    x + cellSize * 6 + cellSize/2, y - cellSize/2, paintBorder);
            view.mCanvas.drawLine( x + cellSize * 3 + cellSize/2, y + height - cellSize/2,
                    x + cellSize * 6 + cellSize/2, y + height - cellSize/2, paintBorder);
//y
            view.mCanvas.drawLine( x + cellSize * 3 + cellSize/2, y - cellSize/2,
                    x + cellSize * 3 + cellSize/2, y + height - cellSize/2, paintBorder);
            view.mCanvas.drawLine( x + cellSize * 4 + cellSize/2, y - cellSize/2,
                    x + cellSize * 4 + cellSize/2, y + height - cellSize/2, paintBorder);
            view.mCanvas.drawLine( x + cellSize * 5 + cellSize/2, y - cellSize/2,
                    x + cellSize * 5 + cellSize/2, y + height - cellSize/2, paintBorder);
            view.mCanvas.drawLine( x + cellSize * 6 + cellSize/2, y - cellSize/2,
                    x + cellSize * 6 + cellSize/2, y + height - cellSize/2, paintBorder);
        }

        paintFill.setColor(ImageCache.getColor(isLevelEnabled()?levelColor:0));

        for (CellPoint cell : getCells()) {
            int r = cellSize / 2;
            RectF rect = new RectF( x + cell.x * cellSize - r,
                                    y + cell.y * cellSize - r,
                                    x + cell.x * cellSize + r,
                                    y + cell.y * cellSize + r);
            view.mCanvas.drawRect(rect, paintFill);
        }

    }

    @Override
    public boolean isSelected(float eventX, float eventY) {
        return super.isSelected(eventX - width/2 + cellSize / 2, eventY - height/2 + cellSize / 2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean result =  super.onTouch(v, event);
        CanvasView view = (CanvasView) v;
        if (result && isLevelEnabled()) {
            UserSettings.setCurrentLevelToDb( levelNumber );
            view.setState(GameState.GAME);
            view.newGame(0);
        }
        return result;
    }

    public boolean isLevelEnabled() {
        return UserSettings.getLevelCompletedValueFromDb() >= levelNumber - 1;
    }

    public Set<CellPoint> getCells() {
        if (levelNumber != UserSettings.getCurrentLevelFromDb()) {
            return cells;
        }
        Set<CellPoint> result = new HashSet<CellPoint>();
        result.addAll(cells);
        result.addAll(getSelectedCells());
        return result;
    }

    public Set<CellPoint> getSelectedCells() {
        if (selectedCells.size() == 0) {
            selectedCells.add(new CellPoint(0, 0, levelColor));
            selectedCells.add(new CellPoint(1, 0, levelColor));
            selectedCells.add(new CellPoint(0, 1, levelColor));

            selectedCells.add(new CellPoint(0, 7, levelColor));
            selectedCells.add(new CellPoint(0, 8, levelColor));
            selectedCells.add(new CellPoint(1, 8, levelColor));

            int x = isTwoDigit?9:5;

            selectedCells.add(new CellPoint(x, 0, levelColor));
            selectedCells.add(new CellPoint(x+1, 0, levelColor));
            selectedCells.add(new CellPoint(x+1, 1, levelColor));

            selectedCells.add(new CellPoint(x+1, 7, levelColor));
            selectedCells.add(new CellPoint(x, 8, levelColor));
            selectedCells.add(new CellPoint(x+1, 8, levelColor));

        }
        return selectedCells;
    }
}
