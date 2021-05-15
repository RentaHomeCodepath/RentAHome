package com.example.rentahome;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Reviews")
public class Reviews extends ParseObject {
    public static final String KEY_author = "author";
    public static final String KEY_description = "Description";
    public static final String KEY_likesCount = "likesCount";
    public static final String KEY_dislikesCount = "dislikesCount";
    public static final String KEY_rating = "rating";



    public String getDescription(){return getString(KEY_description);}
    public void setDescription(String description){put(KEY_description, description);}
    public ParseUser getAuthor(){return getParseUser(KEY_author); }
    public void setAuthor(ParseUser author){put(KEY_author,author);}
    public float getRating(){return (float)getDouble(KEY_rating);}
    public void setRating(float rating) {put(KEY_rating, rating);}
    public int getlikesCount() {return getInt(KEY_likesCount);}
    public void setlikesCount(int likesCount){put(KEY_likesCount, likesCount);}
    public int getdislikesCount() {return getInt(KEY_dislikesCount);}
    public void setdislikesCount(int dislikesCount){put(KEY_dislikesCount, dislikesCount);}
}
