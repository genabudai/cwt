package com.game.cwtetris.ui;

import android.graphics.Bitmap;

import com.game.cwtetris.data.Point;

/**
 * Created by gena on 19.06.2015.
 */
public class Button extends ImageElement {

    private ButtonType type;
    private IButtonVisibilityProvider buttonVisibilityProvider;

    public Button(Bitmap bitmap, Point p, int width, ButtonType type) {
        super(bitmap, p, width);
        this.type = type;
    }

    public Button(Bitmap bitmap, Point p, int width, ButtonType type,
                  IButtonVisibilityProvider buttonVisibilityProvider) {
        this(bitmap, p, width, type);
        this.buttonVisibilityProvider = buttonVisibilityProvider;
    }

    public ButtonType getType() {
        return type;
    }

    @Override
    public boolean isVisible() {
        return buttonVisibilityProvider==null?super.isVisible():buttonVisibilityProvider.isButtonVisible(type);
    }
}
