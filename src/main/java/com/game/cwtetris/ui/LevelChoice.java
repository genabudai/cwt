package com.game.cwtetris.ui;

import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.GameDataLoader;
import com.game.cwtetris.data.GameState;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.Randomizer;
import com.game.cwtetris.data.UserSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gena on 1/7/2017.
 */

public class LevelChoice implements View.OnTouchListener, IButtonVisibilityProvider {

    int height, width, cellSize, menuHeight;
    private final int xCellCount = 41;
    private final int yCellCount = 50;
    private int currentPage = 1;
    private List<LevelButton> buttons = new ArrayList<LevelButton>();
    private boolean isVisible = false;

    public LevelChoice(CanvasView view) {
        width = view.width;
        height = view.height;
        menuHeight = view.cellSize;
        cellSize = width / xCellCount;

        if (cellSize * (yCellCount + 1) > height - menuHeight) {
            cellSize = (height - menuHeight) / yCellCount;
        }
    }

    private int getPageNumber(int level) {
        return 1 + (level - 1) / 9;
    }

    public void refreshLevelButtons() {


        buttons.clear();
        int level = 1 + (currentPage - 1) * 9;
        int maxLevel = GameDataLoader.getMaxLevelNumber();
        Randomizer.refreshRandomColors();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int bX = level < 10 ? 6 + 12*x : 3 + 13*x;
                buttons.add(new LevelButton(getCellCenter(bX, 2 + 14*y), cellSize, level, Randomizer.getRandomColor()));
                level++;
                if (level > maxLevel) return;
            }
        }
    }

    public Point getCellCenter(int xCell, int yCell) {
        int rY = menuHeight + cellSize * yCell + cellSize / 2;
        return new Point(width / 2 + (cellSize * (xCell-21)), rY);
    }

    public void draw(CanvasView view)  {
        for (LevelButton button:buttons) {
            button.draw(view);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isVisible()) return false;
        for (LevelButton button:buttons) {
            if ( button.isLevelEnabled() ) {
                if (button.onTouch(v, event)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean showButtonBack(){
        return currentPage>1;
    }

    public boolean showButtonNext(){
        return currentPage<getPageNumber(GameDataLoader.getMaxLevelNumber());
    }

    public void doBack() {
        if (!showButtonBack()) return;
        currentPage--;
        refreshLevelButtons();
    }

    public void doNext() {
        if (!showButtonNext()) return;
        currentPage++;
        refreshLevelButtons();
    }

    @Override
    public boolean isButtonVisible(ButtonType buttonType) {
        if (!isVisible) return false;
        if (buttonType == ButtonType.BUTTON_NEXT) return showButtonNext();
        if (buttonType == ButtonType.BUTTON_BACK) return showButtonBack();
        return false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        if (isVisible) {
            currentPage = getPageNumber(UserSettings.getCurrentLevelFromDb());
            refreshLevelButtons();
        }
    }
}
