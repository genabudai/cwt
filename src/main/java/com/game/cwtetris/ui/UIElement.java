package com.game.cwtetris.ui;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.CanvasView;

/**
 * Created by gena on 12/15/2016.
 */

public abstract class UIElement implements View.OnTouchListener {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private boolean visible = true;
    private boolean touched = false;

    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean isSelected(float eventX, float eventY){
        if (eventX >= (x - width/2) && (eventX <= (x + width/2))) {
            if (eventY >= (y - height/2) && (eventY <= (y + height/2))) {
                return true;
            }
        }
        return false;
    }

    public abstract void draw(CanvasView view);

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isVisible() == false) {
            return false;
        }
        boolean isSelected = isSelected( event.getX(), event.getY() );
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setTouched(isSelected);
                break;
            case MotionEvent.ACTION_UP:
                boolean result = isSelected && isTouched();
                setTouched(false);
                return result;
        }
        return false;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
