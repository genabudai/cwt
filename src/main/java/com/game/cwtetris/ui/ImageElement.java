package com.game.cwtetris.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.Point;

/**
 * Created by gena on 1/4/2017.
 */

public class ImageElement extends UIElement {

    protected Bitmap bitmap;

    public ImageElement(Bitmap bitmap, Point p, int width) {
        this.bitmap = bitmap;
        this.x = p.x;
        this.y = p.y;
        this.width = width;
        this.height = width;
    }


    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void drawBitmap(Canvas canvas, int x, int y, int height, int width){
        if (bitmap !=null) {
            canvas.drawBitmap(bitmap, null, new RectF(x - width / 2, y - height / 2, x + width / 2, y + height / 2), null);
        }
    }

    @Override
    public void draw(CanvasView view){
        if (!isVisible()) return;
        drawBitmap(view.mCanvas, x, y, height, width);
    }


}
