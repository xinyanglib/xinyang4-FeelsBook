package com.xinyang4.ualberta.feelsbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by apple on 2018/9/22.
 */

public class Emotion {

    public final static String[] CATALOG = {
            "Sadness",
            "Joy",
            "Love",
            "Fear",
            "Anger",
            "Surprise"
    };

    private int type;
    private Date date;
    private String comment;

    public Emotion(int type, String comment) {
        this.type = type;
        this.date = new Date();
        this.comment = comment;
    }

    public String getType() {
        return Emotion.CATALOG[this.type];
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeIndex(){
        return this.type;
    }

    public String getFormatedDate() {
        return Emotion.formatDate(this.date);
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CANADA);
        return sdf.format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
