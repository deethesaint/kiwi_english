package com.example.kiwienglish.Database;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Filling extends RealmObject {
    @PrimaryKey
    private ObjectId FillingID;
    private String Question;
    private String Answer;

    public ObjectId getFillingID() {
        return FillingID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
