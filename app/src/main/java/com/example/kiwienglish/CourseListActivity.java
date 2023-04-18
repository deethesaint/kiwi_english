package com.example.kiwienglish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;

import com.example.kiwienglish.CourseHandling.CourseActivity;
import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.Database.Choice;
import com.example.kiwienglish.Database.ContentModel;
import com.example.kiwienglish.Database.CourseModel;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.Database.Filling;
import com.example.kiwienglish.Database.LessonModel;
import com.example.kiwienglish.Database.Speaking;
import com.example.kiwienglish.Interface.IClickItemListener;

import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class CourseListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        Realm realm = Database.getDBInstance();
        realm.beginTransaction();

        Account account = realm.where(Account.class)
                .equalTo("accountID", CurrentAccount.getInstance().getAccountID())
                .findFirst();
        RealmList<String> courseFileNames = account.getCourseFileNames();
        if (courseFileNames.contains("course.json")) {
            realm.commitTransaction();
            realm.close();
        }
        else {
            account.getCourseFileNames().add("course.json");
            try {
                InputStream inputStream = getAssets().open("course.json");
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    CourseModel course = realm.createObject(CourseModel.class, new ObjectId());
                    while (jsonReader.hasNext()) {
                        String name = jsonReader.nextName();
                        switch (name) {
                            case "name":
                                course.setName(jsonReader.nextString());
                                break;
                            case "description":
                                course.setDescription(jsonReader.nextString());
                                break;
                            case "lessons":
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    jsonReader.beginObject();
                                    LessonModel lesson = realm.createObject(LessonModel.class, new ObjectId());
                                    while (jsonReader.hasNext()) {
                                        String lessonName = jsonReader.nextName();
                                        switch (lessonName) {
                                            case "lesson_name":
                                                lesson.setLessonName(jsonReader.nextString());
                                                break;
                                            case "lesson_description":
                                                lesson.setLessonDescription(jsonReader.nextString());
                                                break;
                                            case "content_list":
                                                jsonReader.beginArray();
                                                while (jsonReader.hasNext()) {
                                                    jsonReader.beginObject();
                                                    ContentModel content = realm.createObject(ContentModel.class, new ObjectId());
                                                    while (jsonReader.hasNext()) {
                                                        String contentName = jsonReader.nextName();
                                                        switch (contentName) {
                                                            case "data":
                                                                content.setData(jsonReader.nextString());
                                                                break;
                                                            case "speech":
                                                                content.setSpeech(jsonReader.nextString());
                                                                break;
                                                            default:
                                                                jsonReader.skipValue();
                                                                break;
                                                        }
                                                    }
                                                    lesson.getContentList().add(content);
                                                    jsonReader.endObject();
                                                }
                                                jsonReader.endArray();
                                                break;
                                            case "choice":
                                                jsonReader.beginArray();
                                                while (jsonReader.hasNext()) {
                                                    jsonReader.beginObject();
                                                    Choice choice = realm.createObject(Choice.class, new ObjectId());
                                                    while (jsonReader.hasNext()) {
                                                        String contentName = jsonReader.nextName();
                                                        switch (contentName) {
                                                            case "first":
                                                                choice.setFirst(jsonReader.nextString());
                                                                break;
                                                            case "option1":
                                                                choice.setOption1(jsonReader.nextString());
                                                                break;
                                                            case "option2":
                                                                choice.setOption2(jsonReader.nextString());
                                                                break;
                                                            case "option3":
                                                                choice.setOption3(jsonReader.nextString());
                                                                break;
                                                            case "option4":
                                                                choice.setOption4(jsonReader.nextString());
                                                                break;
                                                            case "answer":
                                                                choice.setAnswer(jsonReader.nextString());
                                                                break;
                                                            default:
                                                                jsonReader.skipValue();
                                                                break;
                                                        }
                                                    }
                                                    lesson.getChoices().add(choice);
                                                    jsonReader.endObject();
                                                }
                                                jsonReader.endArray();
                                                break;
                                            case "filling":
                                                jsonReader.beginArray();
                                                while (jsonReader.hasNext()) {
                                                    jsonReader.beginObject();
                                                    Filling filling = realm.createObject(Filling.class, new ObjectId());
                                                    while (jsonReader.hasNext()) {
                                                        String contentName = jsonReader.nextName();
                                                        switch (contentName) {
                                                            case "question":
                                                                filling.setQuestion(jsonReader.nextString());
                                                                break;
                                                            case "answer":
                                                                filling.setAnswer(jsonReader.nextString());
                                                                break;
                                                            default:
                                                                jsonReader.skipValue();
                                                                break;
                                                        }
                                                    }
                                                    lesson.getFillings().add(filling);
                                                    jsonReader.endObject();
                                                }
                                                jsonReader.endArray();
                                                break;
                                            case "speaking":
                                                jsonReader.beginArray();
                                                while (jsonReader.hasNext()) {
                                                    jsonReader.beginObject();
                                                    Speaking speaking = realm.createObject(Speaking.class, new ObjectId());
                                                    while (jsonReader.hasNext()) {
                                                        String contentName = jsonReader.nextName();
                                                        switch (contentName) {
                                                            case "word":
                                                                speaking.setWord(jsonReader.nextString());
                                                                break;
                                                            case "answer":
                                                                speaking.setAnswer(jsonReader.nextString());
                                                                break;
                                                            default:
                                                                jsonReader.skipValue();
                                                                break;
                                                        }
                                                    }
                                                    lesson.getSpeakings().add(speaking);
                                                    jsonReader.endObject();
                                                }
                                                jsonReader.endArray();
                                                break;
                                            default:
                                                jsonReader.skipValue();
                                                break;
                                        }
                                    }
                                    course.getLessons().add(lesson);
                                    jsonReader.endObject();
                                }
                                jsonReader.endArray();
                                break;
                            default:
                                jsonReader.skipValue();
                                break;
                        }
                    }
                    jsonReader.endObject();
                    account.getCourseModels().add(course);
                }
                jsonReader.endArray();

                realm.commitTransaction();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                realm.close();
            }
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(this, new IClickItemListener() {
            @Override
            public void onClickItem(CourseModel courseModel) {
                onItemClickCourse(courseModel);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter.setData(setData());
        recyclerView.setAdapter(recyclerViewAdapter);


    }

    private void onItemClickCourse(CourseModel courseModel) {
        Intent intent = new Intent(this, CourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_course", courseModel.getID());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<CourseModel> setData() {
        List<CourseModel> list = new ArrayList<CourseModel>();
        Realm realm = Database.getDBInstance();
        Account account = realm.where(Account.class)
                .equalTo("accountID", CurrentAccount.getInstance().getAccountID())
                .findFirst();
        list = account.getCourseModels();
        realm.close();
        return list;
    }
}