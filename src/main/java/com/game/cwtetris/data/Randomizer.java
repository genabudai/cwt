package com.game.cwtetris.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by gena on 12/15/2016.
 */
public class Randomizer {

    static ArrayDeque<Integer> colors = new ArrayDeque<>();

    public static int randInt(int min, int max) {
        return (min>=max)?min:new Random().nextInt((max - min) + 1) + min;
    }

    private static ArrayDeque<Integer> getColorSet() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
        Collections.shuffle(list);
        return new ArrayDeque<>(list);
    }

    public static void refreshRandomColors() {
        colors.clear();
    }

    public static int getRandomColor() {
        if (colors.size()==0){
            colors = getColorSet();
        }
        return colors.pop();
    }

}
