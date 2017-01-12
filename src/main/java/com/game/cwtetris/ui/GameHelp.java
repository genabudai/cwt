package com.game.cwtetris.ui;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.data.GameData;
import com.game.cwtetris.data.GameState;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.UserSettings;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.view.InputDevice.SOURCE_UNKNOWN;

/**
 * Created by gena on 1/10/2017.
 */

public class GameHelp implements View.OnTouchListener {

    private final ExecutorService pool = Executors.newFixedThreadPool(1);
    private final Object lock = new Object();
    private float x = 0;
    private float y = 0;
    private Bitmap arrow = ImageCache.getImage("arrow");

    private int screenNumber = 1;
    private GameData gameData;

    public void show (CanvasView view ) {
        screenNumber = 0;
        storeGame(view);
        view.newGame(1);
        view.setState(GameState.HELP);
        showNext( view );
    }

    public void showNext (CanvasView view ) {
        synchronized (lock) {
            screenNumber++;
            switch (screenNumber) {
                case 1:
                    showScreen1(view);
                    break;
                case 2:
                    showScreen2(view);
                    break;
                case 3:
                    showScreen3(view);
                    break;
                case 4:
                    showScreen4(view);
                    break;
                case 5: restoreGame(view);
            }

        }
    }

    private void showScreen1 (final CanvasView view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Log.d("AAA", "showScreen1();");
                moveBlock(view, 4, 10, 4, 4);
                moveBlock(view, 7, 10, 2, 4);
                moveBlock(view, 7, 10, 5, 3);
                Log.d("AAA", "showScreen1(); end");
            }
        };
        pool.submit(t);
    }

    private void showScreen2 (final CanvasView view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                Log.d("AAA", "showScreen2();");
                sleep(500);
                int r = view.cellSize/2;
                Point point = new Point(view.width - r*3, r);
                actionDown(view, point);
                sleep(700);
                actionUp(view, point);
                Log.d("AAA", "showScreen2(); end");
                } catch (InterruptedException e) {
                }
            }
        };
        pool.submit(t);
    }

    private void showScreen3 (final CanvasView view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Log.d("AAA", "showScreen3();");
                    Point point = view.getCellCenter(6, 9);
                    point.y -= view.cellSize;
                    actionDown(view, point);
                    sleep(500);
                    actionUp(view, point);
                    sleep(500);
                    point = view.getCellCenter(0, 9);
                    point.y -= view.cellSize;
                    actionDown(view, point);
                    sleep(500);
                    actionUp(view, point);
                    sleep(500);
                    point = view.getCellCenter(6, 9);
                    point.y -= view.cellSize;
                    actionDown(view, point);
                    sleep(500);
                    actionUp(view, point);
                    Log.d("AAA", "showScreen3(); end");
                } catch (InterruptedException e) {
                }
            }
        };
        pool.submit(t);
    }

    private void showScreen4 (final CanvasView view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Log.d("AAA", "showScreen4();");
                moveBlock(view, 1, 10, 4, 2);
                moveBlock(view, 7, 10, 6, 3);
                moveBlock(view, 1, 10, 4, 5);
                Log.d("AAA", "showScreen4(); end");
            }
        };
        pool.submit(t);
    }

    private void storeGame(final CanvasView view) {
        gameData = view.getData();
    }

    private void restoreGame(final CanvasView view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                synchronized (view) {
                    Log.d("AAA", "showScreen5();");
                    view.setData(gameData);
                    view.setState(GameState.GAME);
                    view.getGrid().prepareGrid(view);
                    view.postInvalidate();
                    Log.d("AAA", "showScreen5(); end");
                }
            }
        };
        pool.submit(t);
    }

    private void moveBlock (final CanvasView view, final float startX, final float startY,
                            final float endX, final float endY) {
        Point startP = view.getCellCenter((int) startX, (int) startY);
        Point endP = view.getCellCenter((int) endX, (int) endY);

        actionDown(view, startP);

        MotionEvent event = MotionEvent.obtain(0, 0, MotionEvent.ACTION_MOVE, startP.x, startP.y, 0);
        event.setSource(SOURCE_UNKNOWN);
        Long time = Calendar.getInstance().getTimeInMillis();
        Long timeDif = new Long(0);
        while (timeDif < 1000) {
            timeDif = Calendar.getInstance().getTimeInMillis() - time;
            float x = startP.x - ((startP.x - endP.x) * timeDif / 1000);
            float y = startP.y - ((startP.y - endP.y) * timeDif / 1000);
            event.setLocation(x, y);
            view.onTouchEvent(event);
        }

        actionUp(view, endP);
    }

    private void actionDown(CanvasView view, Point point) {
        MotionEvent event = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, point.x, point.y, 0);
        event.setSource(SOURCE_UNKNOWN);
        view.onTouchEvent(event);
        view.postInvalidate();
    }

    private void actionUp(CanvasView view, Point point) {
        MotionEvent event = MotionEvent.obtain(0, 0, MotionEvent.ACTION_UP, point.x, point.y, 0);
        event.setSource(SOURCE_UNKNOWN);
        view.onTouchEvent(event);
        view.postInvalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        CanvasView view = (CanvasView)v;
        if (view.getState() != GameState.HELP) return false;
        x = event.getX();
        y = event.getY();

        if (event.getSource() != SOURCE_UNKNOWN) {
            x = 0;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showNext(view);
                return true;
            }
        }
        return false;
    }

    public void draw(CanvasView view){
        if (view.getState() != GameState.HELP || x == 0) return;
        int r = view.cellSize * 3;
        if (arrow != null) {
            view.mCanvas.drawBitmap(arrow, null, new RectF(x, y, x + r, y + r), null);
        }
    };

}
