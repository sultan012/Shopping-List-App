package com.hotmail.a_asultan.androidshoppinglistaapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {

    private String mName;
    boolean mChecked = false;

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    private static final String JSON_NAME = "name";
    private static final String JSON_CHECKED= "checked";


    //Constructor.
    public Item(JSONObject jo) throws JSONException {
        mName = jo.getString(JSON_NAME);
        mChecked = jo.getBoolean(JSON_CHECKED);

    }

    //Empty default constructor.
    public Item(){
    }

    public JSONObject convertToJSON() throws JSONException{
        JSONObject jo = new JSONObject();

        jo.put(JSON_NAME, mName);
        jo.put(JSON_CHECKED,mChecked);

        return jo;
    }


    public String getName(){
        return mName;
    }

    public void setName(String mName){
        this.mName = mName;
    }



}
