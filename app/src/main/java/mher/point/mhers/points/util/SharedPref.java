package mher.point.mhers.points.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import mher.point.mhers.points.model.Btn;

import static mher.point.mhers.points.key.ConsKey.BEST_KEY;
import static mher.point.mhers.points.key.ConsKey.BTN_LIST_KEY;
import static mher.point.mhers.points.key.ConsKey.NEW_GAME_KEY;
import static mher.point.mhers.points.key.ConsKey.SCORE_KEY;
import static mher.point.mhers.points.key.ConsKey.SOUND_KEY;
import static mher.point.mhers.points.key.ConsKey.SP_NAME;


public class SharedPref {
public static SharedPref INSTANCE=null;


    public static SharedPref getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new SharedPref();
        }
        return INSTANCE;
    }




    public void savefirstgame(Context context,boolean a){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(NEW_GAME_KEY,a);
        editor.commit();
    }
    public boolean getfirstGame(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
        return sharedPreferences.getBoolean(NEW_GAME_KEY,false);

    }
    public void saveSound(Context context,boolean a){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SOUND_KEY,a);
        editor.commit();
    }
    public boolean getSound(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        return sharedPreferences.getBoolean(SOUND_KEY,true);
    }
    public void saveBest(Context context,int a){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BEST_KEY,a);
        editor.commit();
    }
    public int getBest(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        return sharedPreferences.getInt(BEST_KEY,0);
    }
    public void saveScore(Context context,int a){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SCORE_KEY,a);
        editor.commit();
    }
    public int getScore(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        return sharedPreferences.getInt(SCORE_KEY,0);

    }
    public void saveBtnList(Context context,List<Btn> mybuttneList){
        Gson g = new Gson();
        String json= g.toJson(mybuttneList);
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BTN_LIST_KEY,json);
        editor.commit();
    }
    public List<Btn> getbtnList(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SP_NAME, 0);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Btn>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(BTN_LIST_KEY,null),type );






    }


}
