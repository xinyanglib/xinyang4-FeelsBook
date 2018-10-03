package com.xinyang4.ualberta.feelsbook;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by apple on 2018/9/29.
 */

public class DataManager {
    private static DataManager ourInstance = null;
    private static final String FILENAME = "feelsbook.json";

    public static DataManager getInstance(Context context) {
        if (ourInstance == null){
            if (!DataManager.loadFromFile(context)){
                ourInstance = new DataManager();
            }
        }
        return ourInstance;
    }

    private static boolean loadFromFile(Context context){
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            Gson gson = new Gson();
            ourInstance =  gson.fromJson(reader,DataManager.class);
            if (ourInstance == null){
                return false;
            }
            return true;
        }

        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static void saveToFile(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(ourInstance,writer);
            writer.flush();
            fos.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Emotion> emotions;

    private DataManager() {
        emotions = new ArrayList<>();
    }

    public ArrayList<Emotion> getEmotions() {
        return emotions;
    }
}
