package mher.point.mhers.points.util;

import android.content.Context;
import android.media.MediaPlayer;

import mher.point.mhers.points.R;


public class Sounds {
public  static Sounds  INSTANCE = null;

    public static Sounds getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Sounds();
        }
        return INSTANCE;
    }


    private MediaPlayer sound;
    private MediaPlayer positivsound;
    public void playsound(Context c){
        sound = MediaPlayer.create(c.getApplicationContext(), R.raw.sound);
        sound.setLooping(true);
        sound.setVolume(10.0f, 3.0f);
        sound.start();
    }
    public  void stopsound(){
        if(sound!=null)
            sound.stop();
    }
    public void playPositivSound(Context c){
        positivsound = MediaPlayer.create(c.getApplicationContext(), R.raw.pozitiv);
        positivsound.setVolume(0.5f,0.5f);
        positivsound.start();
    }

}
