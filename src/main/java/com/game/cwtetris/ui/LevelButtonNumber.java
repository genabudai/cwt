package com.game.cwtetris.ui;

import com.game.cwtetris.data.CellPoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gena on 1/7/2017.
 */

public class LevelButtonNumber {

    public static Set<CellPoint> getNumberCells(int number, int color) {
        Set<CellPoint> cells = new HashSet<CellPoint>();
        int pos = 0;
        int num = number / 10;
        if (number > 9) {
            cells.addAll(getNumber(num, color, pos));
            pos = 4;
        }
        num = number % 10;
        cells.addAll(getNumber(num, color, pos));
        for (CellPoint cell:cells) {
            cell.x += 2;
            cell.y += 2;
        }
        return cells;
    }

    private static List<CellPoint> getNumber(int number, int color, int x) {
        switch (number){
            case 1: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+1, 1, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+1, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 2: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 3: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 4: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x, 1, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x+2, 4, color));
            case 5: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 6: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x, 3, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 7: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x+2, 4, color));
            case 8: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x, 1, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x, 3, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 9: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x, 1, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+1, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
            case 0: return Arrays.asList( new CellPoint(x, 0, color),
                                          new CellPoint(x+1, 0, color),
                                          new CellPoint(x+2, 0, color),
                                          new CellPoint(x, 1, color),
                                          new CellPoint(x+2, 1, color),
                                          new CellPoint(x, 2, color),
                                          new CellPoint(x+2, 2, color),
                                          new CellPoint(x, 3, color),
                                          new CellPoint(x+2, 3, color),
                                          new CellPoint(x, 4, color),
                                          new CellPoint(x+1, 4, color),
                                          new CellPoint(x+2, 4, color));
        }
        return Collections.emptyList();
    }
}
