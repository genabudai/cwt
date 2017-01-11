package com.game.cwtetris.data;

import java.io.Serializable;

/**
 * Created by gena on 12/12/2016.
 */

public class Point implements Serializable{

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
