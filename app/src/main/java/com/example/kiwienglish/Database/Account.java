package com.example.kiwienglish.Database;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Account extends RealmObject {
    @PrimaryKey
    private ObjectId accountID;
    @Required
    private String username;
    @Required
    private String password;
    private String email;
    private String phoneNumber;
    private String imgPath;
    private boolean isNew;
    private int CoursesAttended;
    private int CorrectAnswers;
    private int WrongAnswers;
    private RealmList<CourseModel> courseModels;
    private RealmList<String> courseFileNames;
    public RealmList<CourseModel> getCourseModels() {
        return courseModels;
    }

    public void setCourseModels(RealmList<CourseModel> courseModels) {
        this.courseModels = courseModels;
    }

    public RealmList<String> getCourseFileNames() {
        return courseFileNames;
    }

    public void setCourseFileNames(RealmList<String> courseFileNames) {
        this.courseFileNames = courseFileNames;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public ObjectId getAccountID() {
        return accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getCoursesAttended() {
        return CoursesAttended;
    }

    public void setCoursesAttended(int coursesAttended) {
        CoursesAttended = coursesAttended;
    }

    public int getCorrectAnswers() {
        return CorrectAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        CorrectAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return WrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        WrongAnswers = wrongAnswers;
    }
}
