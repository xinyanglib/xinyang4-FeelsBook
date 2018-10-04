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

/**
 * to manage the data form.
 */
public class DataManager {
    private static DataManager ourInstance = null;
    private static final String FILENAME = "feelsbook.json";

    /**
     * to make the new data if there is not have
     * if it is , get the file.
     */
    public static DataManager getInstance(Context context) {
        if (ourInstance == null){
            if (!DataManager.loadFromFile(context)){
                ourInstance = new DataManager();
            }
        }
        return ourInstance;
    }
    /**
     * use json to make some difficult string type.
     * to open the file to get the useful data.
     */
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
        /**
         * to check exception. use the lab resource.
         */
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    /**
     *  save the file in the lab resource.
     */
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
