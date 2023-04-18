package com.example.kiwienglish.Database;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Speaking extends RealmObject {
    @PrimaryKey
    private ObjectId SpeakingID;
    private String Word;
    private String Answer;

    public ObjectId getSpeakingID() {
        return SpeakingID;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
