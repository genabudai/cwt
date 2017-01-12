package com.game.cwtetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.game.cwtetris.data.GameData;
import com.game.cwtetris.data.GameDataLoader;
import com.game.cwtetris.data.GameState;
import com.game.cwtetris.data.ImageCache;
import com.game.cwtetris.data.Point;
import com.game.cwtetris.data.SoundPlayer;
import com.game.cwtetris.data.UserSettings;
import com.game.cwtetris.ui.GameHelp;
import com.game.cwtetris.ui.Grid;
import com.game.cwtetris.ui.LevelChoice;
import com.game.cwtetris.ui.Menu;

import static android.view.InputDevice.SOURCE_UNKNOWN;
import static com.game.cwtetris.CWTApp.getSoundPlayer;


public class CanvasView extends View {

    public GameHelp help = new GameHelp();
    private GameData data;

    public int width, height, cellSize;
    public Canvas mCanvas;
    private float mX, mY;

    private Menu menu;
    private LevelChoice levelChoice;
    private Grid grid;

    public final int xCellCount = 9;
    public final int yCellCount = 12;

    private Bitmap bitmap;
    private Path mPath;
    private final Paint mPaint = new Paint();
    private final Paint bg_paint = new Paint();

    private static final float TOLERANCE = 2;
    private GameState state = GameState.GAME;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);

        bg_paint.setStyle(Paint.Style.FILL);
        bg_paint.setColor(Color.rgb(255, 248, 239));
        bg_paint.setAlpha(0x80); // optional

        bitmap = ImageCache.getImage("b_0");
    }

    public void setData(GameData data) {
        this.data = data;
    }

    public void newGame(int level) {
        if (level==0) {
            level = UserSettings.getCurrentLevelFromDb();
            getSoundPlayer().start();
        };
        data = GameDataLoader.getLevel(level);
        grid.prepareGrid(this);
        invalidate();
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        synchronized (this) {
            // your Canvas will draw onto the defined Bitmap
            Bitmap mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            width = mCanvas.getWidth();
            height = mCanvas.getHeight();
            cellSize = width / xCellCount;
            if (cellSize * (yCellCount + 1)> height ) {
                cellSize = height / (yCellCount + 1);
            }

            levelChoice = new LevelChoice(this);;
            grid = new Grid(this);
            menu = new Menu(this);

            newGame(0);
        }
    }

    public Point getCellCenter(int xCell, int yCell) {
        int rY = cellSize * yCell + cellSize / 2;
        if (yCell>8) {
            rY = height - cellSize * (yCellCount - yCell) ;
        }
        return new Point(width / 2 + (cellSize * (xCell-4)), rY);
    }

    public Point getCellByPoint(float x, float y) {
        x += (width - (cellSize * 9)) / 2;
        int xCell = (int)(x / cellSize) - 1;
        int yCell = (int)(y / cellSize) - 1;
        return new Point(xCell, yCell);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, width, height, bg_paint);

        if (menu==null) return;
        super.onDraw(canvas);
        synchronized (this) {
            mCanvas = canvas;
            // draw the mPath with the mPaint on the canvas when onDraw
//        canvas.drawPath(mPath, mPaint);
//            mCanvas.drawBitmap(bitmap, mX, mY, mPaint);
//            mCanvas.drawLine(width - 1, height - 1, width - 1, 0, mPaint);
//            mCanvas.drawLine(0, height - 1, width - 1, height - 1, mPaint);
            menu.draw(this);
            if (levelChoice.isVisible()) {
                levelChoice.draw(this);
            } else {
                grid.draw(this);
            }
//            Point startP = getCellCenter(6, 9);
//            startP.y -= cellSize;
//            Point endP = getCellCenter(0, 9);
//            endP.y -= cellSize;
//            mCanvas.drawLine(startP.x, startP.y, endP.x, endP.y, mPaint);
            help.draw( this );
        }
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
//        mPath.lineTo(mX, mY);
        moveTouch(1,1);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("AAA", "ACTION_DOWN");
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("AAA", "ACTION_UP");
        }

        float x = event.getX();
        float y = event.getY();

        if ( help.onTouch(this, event) ) return true;

        boolean result = menu.onTouch(this, event);
        if (!result) {
            if ( event.getAction() == MotionEvent.ACTION_UP && grid.isLevelCompleted(this) ) {
                int level = UserSettings.getCurrentLevelFromDb();
                UserSettings.setLevelCompletedValueToDb(level);
                UserSettings.setCurrentLevelToDb(level+1);
                newGame(0);
                return true;
            }

            grid.onTouch(this, event);
            levelChoice.onTouch(this, event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTouch(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveTouch(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    upTouch();
                    break;
            }
        }
        postInvalidate();
        return true;
    }

    public GameData getData() {
        return data;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        levelChoice.setVisible(state == GameState.LEVEL_CHOICE);
        grid.setVisible(state != GameState.LEVEL_CHOICE);
        this.state = state;
    }

    public LevelChoice getLevelChoice() {
        return levelChoice;
    }


    public Grid getGrid() {
        return grid;
    }

}