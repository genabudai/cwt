package com.game.cwtetris.data;

import android.content.Context;
import android.util.Log;

import com.game.cwtetris.R;
import com.game.cwtetris.ui.shape.ShapeType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static com.game.cwtetris.CWTApp.getAppContext;

/**
 * Created by gena on 1/3/2017.
 */

public class GameDataLoader {

    private static int maxLevelNumber = 0;

    private static final String LOG_TAG = "XML_Praser";

    public static GameData getLevel(int level) {
        GameData result = new GameData();
        try {
            XmlPullParser xpp = prepareXpp(getLevelResourceId(level));

            String name;
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        name = xpp.getName();
                        if (name.equals("shape")) {
                            result.getShapes().add( new ShapeData( ShapeType.valueOf(xpp.getAttributeValue(null,"type")),
                                    Integer.parseInt(xpp.getAttributeValue(null,"count"))));
                        } else if (name.equals("block")) {
                            CellPoint block = new CellPoint(Integer.parseInt(xpp.getAttributeValue(null, "x")),
                                    Integer.parseInt(xpp.getAttributeValue(null, "y")), 100);
                            if (result.getCells().contains(block)) {
                                result.getCells().remove(block);
                            }
                            result.getCells().add(block);
                        } if (name.equals("cell")) {
                            int x = Integer.parseInt(xpp.getAttributeValue(null, "x"));
                            int x2 = (( xpp.getAttributeValue(null, "x2")==null)?
                                        x:Integer.parseInt(xpp.getAttributeValue(null, "x2")));
                            for (int i=x; i<=x2; i++) {
                                int y = Integer.parseInt(xpp.getAttributeValue(null, "y"));
                                result.getCells().add(new CellPoint(i,y,0));
                            }
                        }
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static XmlPullParser prepareXpp(int level) {
        return getAppContext().getResources().getXml(level);
    }

    public static int getMaxLevelNumber() {
        Context context = getAppContext();
        if (maxLevelNumber > 0) return maxLevelNumber;
        int level = 1;
        while (true) {
            if (context.getResources().getIdentifier("level"+level, "xml", context.getPackageName()) == 0) {
                maxLevelNumber = level-1;
                break;
            }
            level++;
        }
        return maxLevelNumber;
    }

    public static int getLevelResourceId(int level) {
        Context context = getAppContext();
        int levelResId = 0;
        while (levelResId == 0) {
            levelResId = context.getResources().getIdentifier("level"+level, "xml", context.getPackageName());
            level--;
        }
        return levelResId;
    }


}
