package com.game.cwtetris.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.HashMap;

import static com.game.cwtetris.CWTApp.getAppContext;

/**
 * Created by gena on 12/12/2016.
 */

public class ImageCache {
    private static HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();

    public static Bitmap getImage(String pictureName){
        Context context = getAppContext();
        Bitmap bitmap = cache.get(pictureName);
        if (bitmap == null) {
            int drawableResourceId = context.getResources().getIdentifier(pictureName, "drawable", context.getPackageName());
            bitmap = BitmapFactory.decodeResource(context.getResources(), drawableResourceId);
            addImage(pictureName, bitmap);
        }
        return bitmap;
    }

    public static Bitmap addImage(String pictureName, Bitmap bitmap){
        return cache.put(pictureName, bitmap);
    }

    public static int getColor(int color){
        switch (color) {
            case 1: return Color.rgb(255, 0, 0);
            case 2: return Color.rgb(181, 230, 29);
            case 3: return Color.rgb(163, 73, 164);
            case 4: return Color.rgb(255, 201, 27);
            case 5: return Color.rgb(63, 72, 204);
            case 6: return Color.rgb(255, 87, 27);
            case 7: return Color.rgb(200, 0, 125);
            case 8: return Color.rgb(40, 215, 166);
            case 9: return Color.rgb(70, 5, 110);
            case 10: return Color.rgb(0, 200, 25);
            case 11: return Color.rgb(0, 255, 255);
            case 12: return Color.rgb(150, 0, 255);
        }
        return Color.rgb(195, 195, 195);
    };

}
