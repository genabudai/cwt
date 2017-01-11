package com.game.cwtetris;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.game.cwtetris.data.GameDataLoader;
import com.game.cwtetris.data.SoundPlayer;
import com.game.cwtetris.data.UserSettings;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.game.cwtetris.R.id.adView;

public class CWTApp extends AppCompatActivity {

    private static Context context;
    private static SoundPlayer sp;
    private CanvasView customCanvas;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CWTApp.context = getApplicationContext();
        CWTApp.sp = new SoundPlayer();

        setContentView(R.layout.activity_cwt);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        initAds();
    }

    public static Context getAppContext() {
        return CWTApp.context;
    }

    public static SoundPlayer getSoundPlayer() {
        return CWTApp.sp;
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    private void initAds(){
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(adView);
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
        getSoundPlayer().stopBackgroundMusic();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        getSoundPlayer().playBackgroundMusic();
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
        getSoundPlayer().stopBackgroundMusic();
    }
}
