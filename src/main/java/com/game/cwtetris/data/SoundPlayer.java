package com.game.cwtetris.data;

import android.media.MediaPlayer;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.game.cwtetris.CanvasView;
import com.game.cwtetris.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static com.game.cwtetris.CWTApp.getAppContext;

/**
 * Created by gena on 1/2/2017.
 */

public class SoundPlayer {

    private HashMap<Integer, MediaPlayer> mpMap = new HashMap<Integer, MediaPlayer>();

    private MediaPlayer playSound(int sound) {
        MediaPlayer mp = mpMap.get( sound );
        if (mp != null){
            mp.pause();
            mp.seekTo(0);
        } else {
            mp = MediaPlayer.create(getAppContext(), sound);
            mp.setLooping( sound == R.raw.music );
            mpMap.put( sound, mp );
        }
        mp.start();
        return mp;
    }

    public void rotate() {
        if (!UserSettings.getSoundOnValueFromDb()) return;
        playSound(R.raw.rotate);
    }

    public void undo() {
        if (!UserSettings.getSoundOnValueFromDb()) return;
        playSound(R.raw.undo);
    }

    public void drop() {
        if (!UserSettings.getSoundOnValueFromDb()) return;
        playSound(R.raw.drop);
    }

    public void playBackgroundMusic() {
        if (!UserSettings.getMusicOnValueFromDb()) return;
        playSound(R.raw.music);
    }

    public void stopBackgroundMusic() {
        MediaPlayer mp = mpMap.get( R.raw.music );
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }
}
