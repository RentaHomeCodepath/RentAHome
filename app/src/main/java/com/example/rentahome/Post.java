package com.example.rentahome;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";     //image should be 'uploaded'
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt"; //have to figure out how to implement
    public static final String KEY_address = "address";
    public static final String KEY_price = "price";
    public static final String KEY_reviews = "Reviews";
    public static final String KEY_objectID = "objectID";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){put(KEY_USER, user);}

    public String getAddress() {return getString(KEY_address);}
    public void setAddress(String address){ put(KEY_address, address);}

    public int getPrice(){return getInt(KEY_price);}
    public void setPrice(int price){put(KEY_price,price);}
    public String getobjectID(){return getString(KEY_objectID);}

    public ParseRelation<ParseObject>  getrelation() {return getRelation(KEY_reviews);}
    public void setrelation(ParseRelation<ParseObject>  relation){put(KEY_reviews, relation);}

    //public ParseRelation<ParseObject> getReviews(){return getRelation(KEY_reviews);}
    //public void setReviews(ParseRelation<ParseObject> reviews){put(KEY_reviews, reviews);}

    //public Date getCreatedAt() { return getDate(KEY_CREATED_KEY);}







}
