package com.game.cwtetris.ui;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.GameState;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.SoundPlayer;
import com.game.cwtetris.data.UserSettings;

import java.util.ArrayList;
import java.util.List;

import static com.game.cwtetris.CWTApp.getSoundPlayer;

/**
 * Created by gena on 12/12/2016.
 */

public class Menu implements View.OnTouchListener, IMenu {

    private List<Button> buttons = new ArrayList<Button>();

    public Menu(CanvasView view) {
        int r = view.cellSize/2;
        buttons.add(new Button(ImageCache.getImage("hbutton"),
                new Point(r, r), r + r/2 , ButtonType.BUTTON_HAMBURGER){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    CanvasView view = (CanvasView) v;
                    view.setState(view.getState() == GameState.LEVEL_CHOICE ? GameState.GAME : GameState.LEVEL_CHOICE);
                }
                return result;
            }
        });
        buttons.add(new Button(ImageCache.getImage("sound_" + (UserSettings.getSoundOnValueFromDb()?"on":"off")),
                new Point(r*3, r), r + r/2,ButtonType.BUTTON_SOUND) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    Context c = v.getContext();
                    UserSettings.setSoundOnValueToDb(!UserSettings.getSoundOnValueFromDb());
                    setBitmap(ImageCache.getImage("sound_" + (UserSettings.getSoundOnValueFromDb()?"on":"off")));
                }
                return result;
            }
        });
        buttons.add(new Button(ImageCache.getImage("music_" + (UserSettings.getMusicOnValueFromDb()?"on":"off")),
                new Point(r*5, r), r + r/2,ButtonType.BUTTON_MUSIC) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    Context c = v.getContext();
                    boolean isMusicOn = !UserSettings.getMusicOnValueFromDb();
                    UserSettings.setMusicOnValueToDb(isMusicOn);
                    if (isMusicOn) {
                        getSoundPlayer().playBackgroundMusic();
                    } else {
                        getSoundPlayer().stopBackgroundMusic();
                    }
                    setBitmap(ImageCache.getImage("music_" + (isMusicOn?"on":"off")));
                }
                return result;
            }
        });
        buttons.add(new Button(ImageCache.getImage( "undo"),
                new Point(view.width - r*3, r), r + r/2, ButtonType.BUTTON_UNDO,
                view.getGrid()){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    CanvasView view = (CanvasView) v;
                    if (view.getData().undo()) {
                        getSoundPlayer().undo();
                    }
                }
                return result;
            }
        });
        buttons.add(new Button(ImageCache.getImage("ibutton"),
                new Point(view.width - r, r), r + r/2, ButtonType.BUTTON_HELP,
                view.getGrid()){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    CanvasView view = (CanvasView) v;
                    view.help.show( view );
                }
                return result;
            }
        });

        buttons.add(new Button(ImageCache.getImage("back"),
                new Point(view.width - r*3, r), r + r/2, ButtonType.BUTTON_BACK,
                view.getLevelChoice()){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    CanvasView view = (CanvasView) v;
                    LevelChoice levelChoice = view.getLevelChoice();
                    levelChoice.doBack();
                }
                return result;
            }
        });
            buttons.add(new Button(ImageCache.getImage("next"),
                new Point(view.width - r, r), r + r/2, ButtonType.BUTTON_NEXT,
                view.getLevelChoice()){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = super.onTouch(v, event);
                if (result) {
                    CanvasView view = (CanvasView) v;
                    LevelChoice levelChoice = view.getLevelChoice();
                    levelChoice.doNext();
                }
                return result;
            }
        });
    }

    public void draw(CanvasView view)  {
        for (Button button:buttons) {
            button.draw(view);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean result = false;
        for (Button button:buttons) {
            result = button.onTouch(v, event);
            if (result)
                return true;
        }
        return false;
    }

    @Override
    public void setButtonVisible (ButtonType buttonType, boolean value) {
        for (Button button:buttons) {
            if (button.getType() == buttonType) {
                button.setVisible(value);
            };
        }
    }

}
