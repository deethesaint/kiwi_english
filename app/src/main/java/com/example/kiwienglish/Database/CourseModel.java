package com.example.kiwienglish.Database;

import org.bson.types.ObjectId;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CourseModel extends RealmObject implements Serializable {
    @PrimaryKey
    private ObjectId ID;
    @Required
    private String Name;
    private String Description;

    private RealmList<LessonModel> lessons;

    public RealmList<LessonModel> getLessons() {
        return lessons;
    }

    public void setLessons(RealmList<LessonModel> lessons) {
        this.lessons = lessons;
    }

    public ObjectId getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
