package com.example.kiwienglish.Database;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ContentModel extends RealmObject {
    @PrimaryKey
    private ObjectId ContentID;
    @Required
    private String Data;
    @Required
    private String Speech;

    public ObjectId getContentID() {
        return ContentID;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getSpeech() {
        return Speech;
    }

    public void setSpeech(String speech) {
        Speech = speech;
    }
}
