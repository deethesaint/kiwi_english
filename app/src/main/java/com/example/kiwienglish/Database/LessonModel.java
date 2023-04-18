package com.example.kiwienglish.Database;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class LessonModel extends RealmObject {
    @PrimaryKey
    private ObjectId LessonID;
    @Required
    private String LessonDescription;
    @Required
    private String LessonName;

    private RealmList<ContentModel> contentList;
    private RealmList<Choice> choices;
    private RealmList<Filling> fillings;
    private RealmList<Speaking> speakings;

    public String getLessonDescription() {
        return LessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        LessonDescription = lessonDescription;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public RealmList<ContentModel> getContentList() {
        return contentList;
    }

    public void setContentList(RealmList<ContentModel> contentList) {
        this.contentList = contentList;
    }

    public ObjectId getLessonID() {
        return LessonID;
    }

    public RealmList<Choice> getChoices() {
        return choices;
    }

    public void setChoices(RealmList<Choice> choices) {
        this.choices = choices;
    }

    public RealmList<Filling> getFillings() {
        return fillings;
    }

    public void setFillings(RealmList<Filling> fillings) {
        this.fillings = fillings;
    }

    public RealmList<Speaking> getSpeakings() {
        return speakings;
    }

    public void setSpeakings(RealmList<Speaking> speakings) {
        this.speakings = speakings;
    }
}
