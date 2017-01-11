package com.game.cwtetris.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;


import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.CellPoint;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.Randomizer;
import com.game.cwtetris.data.SoundPlayer;
import com.game.cwtetris.ui.shape.ShapeOrientation;
import com.game.cwtetris.ui.shape.ShapeType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.game.cwtetris.CWTApp.getSoundPlayer;

/**
 * Created by gena on 12/14/2016.
 */

public abstract class Shape extends UIElement implements View.OnTouchListener {

    protected List<CellPoint> cells = new ArrayList<CellPoint>();
    protected int x, y, width = 0;
    protected float tX, tY, cX, cY = 0;
    protected LinkedHashSet<Integer> colors;
    protected Button rotateButton;
    private Paint countPaint = new Paint();
    private final Paint paintFill = new Paint();
    private ShapeType shapeType;


    public Shape(CanvasView view, ShapeType shapeType, int xCell, int yCell, int color) {
        paintFill.setStyle(Paint.Style.FILL_AND_STROKE);
        paintFill.setColor(Color.BLACK);

        Point p = view.getCellCenter(xCell, yCell);
        this.shapeType = shapeType;
        this.width = view.cellSize;
        this.height = width;
        int rotateButtonWidth = width - width/4;
        this.x = p.x;
        this.y = p.y;
        this.colors = getColors(color);

        setOrientation(view, ShapeOrientation.O_0);
        rotateButton = new Button(ImageCache.getImage("rbutton"),
                new Point(p.x, p.y - width), rotateButtonWidth, ButtonType.BUTTON_ROTATE){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    incOrientation((CanvasView) v);
                }
                return result;
            }
        };

        countPaint.setARGB(255, 190, 178, 140);
        countPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        countPaint.setTextAlign(Paint.Align.CENTER);
        prepareTextSize(countPaint, "W", rotateButtonWidth);
    }

    public static LinkedHashSet<Integer> getColors(int color) {
        LinkedHashSet<Integer> result = new LinkedHashSet<>();
        for (int i = 0; i <= 3; i++) {
            result.add(Randomizer.getRandomColor());
        }
        return result;
    }

    protected int getOrientationColor(int index) {
        int i = 0;
        for (Integer color : colors) {
            if (i==index) {
                return color;
            }
            i++;
        }
        return 0;
    }

    @Override
    public void draw(CanvasView view) {
        if (!isVisible(view)) return;
//        Bitmap bitmap = null;
        int gap = view.cellSize / 25;
        rotateButton.draw(view);
        drawCount(view, countPaint);
        for (CellPoint cellPoint : getCells()) {
//            if (bitmap == null) {
//                bitmap = ImageCache.getImage(view.context, "b_" + cellPoint.value);
//            }
            RectF r = new RectF( x - width / 2 + width * cellPoint.x + gap,
                                 y - width / 2 + width * cellPoint.y + gap,
                                 x + width / 2 + width * cellPoint.x - gap,
                                 y + width / 2 + width * cellPoint.y - gap);
//            if (bitmap != null ) {
                if (view.getData().getShape(shapeType).getCount() > 1 || !isTouched()) {
//                    view.mCanvas.drawBitmap(bitmap, null, r, null);
                    paintFill.setColor(ImageCache.getColor(cellPoint.value));
                    view.mCanvas.drawRoundRect(r, 15, 15, paintFill);
                }
                if (isTouched()) {
                    r.set(r.left - tX + cX + gap, r.top - tY + cY + gap, r.right - tX + cX - gap, r.bottom - tY + cY - gap);
                    paintFill.setColor(ImageCache.getColor(cellPoint.value));
                    view.mCanvas.drawRoundRect(r, 15, 15, paintFill);
//                    view.mCanvas.drawBitmap(bitmap, null, r, null);
                }
//            }
        }
    }

    public List<CellPoint> getCells() {
        return cells;
    }

    protected abstract List<CellPoint> getShapeElement(ShapeOrientation orientation);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isVisible((CanvasView) v)&&!isTouched()) return false;
        boolean rotateButtonTouched = rotateButton.onTouch(v, event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!rotateButtonTouched&&!isTouched()) {
                    tX = event.getX();
                    tY = event.getY();
                    cX = event.getX();
                    cY = event.getY();
                    setTouched(isSelected(tX, tY));
                }
                return isTouched();
            case MotionEvent.ACTION_MOVE:
                if (isTouched()) {
                    cX = event.getX();
                    cY = event.getY();
                }
                return isTouched();
            case MotionEvent.ACTION_UP:
                setTouched(false);
                return isTouched();
        }
        return isTouched() || rotateButtonTouched;
    }

    public void setOrientation(CanvasView view, ShapeOrientation orientation) {
        view.getData().getShape(shapeType).setOrientation(orientation);
        this.cells = getShapeElement(orientation);
    }

    public void incOrientation(CanvasView view) {
        int o = view.getData().getShape(shapeType).getOrientation().getValue();
        o = (o == 3)?0:o+1;
        setOrientation(view, ShapeOrientation.fromInt(o));
        getSoundPlayer().rotate();
    }

    public void prepareTextSize(Paint paint, String text, int width){
        if (text.equals("")) return;
        int textSize = 1;
        paint.setTextSize(1);
        while (paint.measureText(text) < width) {
            textSize++;
            paint.setTextSize(textSize);
        }
    }

    protected void drawCount(CanvasView view, Paint paint){
        String label = String.valueOf(view.getData().getShape(shapeType).getCount());
        if (!label.equals("") && paint != null) {
            int y = this.y - width;
            int yPos = (int) (y - ((paint.descent() + paint.ascent()) / 2)) ;
            view.mCanvas.drawText(label, x + 1 * width, yPos, paint);
        }
    }

    @Override
    public boolean isSelected(float eventX, float eventY) {
        for (CellPoint cell : cells) {
            if (isCellSelected(eventX, eventY, x + cell.x * width, y + cell.y * width)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCellSelected(float eventX, float eventY, int xCell, int yCell){
        if (eventX >= (xCell - width/2) && (eventX <= (xCell + width/2))) {
            if (eventY >= (yCell - height/2) && (eventY <= (yCell + height/2))) {
                return true;
            }
        }
        return false;
    }

    public Point getMovingXY () {
        Point p  = new Point(0,0);
        p.x = Math.round(x - (tX - cX));
        p.y = Math.round(cY - (tY - y));
        return p;
    }

    public void decCount(CanvasView view){
        view.getData().getShape(shapeType).decCount();
    }

    public boolean isVisible(CanvasView view) {
        return view.getData().getShape(shapeType).getCount() > 0;
    }

    public int getColor() {
        for (CellPoint cell:cells) {
            return cell.value;
        }
        return 0;
    }

}
