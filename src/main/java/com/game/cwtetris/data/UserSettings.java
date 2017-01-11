package com.game.cwtetris.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.game.cwtetris.CWTApp.getAppContext;

/**
 * Created by gena on 1/3/2017.
 */

public class UserSettings {

    public static boolean getSoundOnValueFromDb(){
        return PreferenceManager.getDefaultSharedPreferences(getAppContext()).getBoolean("SOUND_ON",true);
    }

    public static void setSoundOnValueToDb(boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getAppContext()).edit();
        editor.putBoolean("SOUND_ON", value);
        editor.commit();
    }

    public static boolean getMusicOnValueFromDb(){
        return PreferenceManager.getDefaultSharedPreferences(getAppContext()).getBoolean("MUSIC_ON",false);
    }

    public static void setMusicOnValueToDb(boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getAppContext()).edit();
        editor.putBoolean("MUSIC_ON", value);
        editor.commit();
    }

    private static void setIntValueToDb(String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getAppContext()).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setLevelCompletedValueToDb(int level) {
        if (getLevelCompletedValueFromDb()>=level) return;
        setIntValueToDb("LEVEL_COMPLETED", level);
    }

    public static int getLevelCompletedValueFromDb(){
        return PreferenceManager.getDefaultSharedPreferences(getAppContext()).getInt("LEVEL_COMPLETED", 0);
    }

    public static void setCurrentLevelToDb(int level) {
        int maxLevelNumber = GameDataLoader.getMaxLevelNumber();
        setIntValueToDb("CURRENT_LEVEL", level>maxLevelNumber?maxLevelNumber:level);
    }

    public static int getCurrentLevelFromDb(){
        return PreferenceManager.getDefaultSharedPreferences(getAppContext()).getInt("CURRENT_LEVEL", 1);
    }

}
