package com.example.kiwienglish.CourseHandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kiwienglish.Database.CourseModel;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.R;

import org.bson.types.ObjectId;

import io.realm.Realm;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;

        ObjectId id = (ObjectId) bundle.get("object_course");

        Realm realm = Database.getDBInstance();
        CourseModel courseModel = realm.where(CourseModel.class)
                .equalTo("ID", id)
                .findFirst();
        TextView title = findViewById(R.id.activity_course_tv);
        TextView desc = findViewById(R.id.course_atvt_tv_desc);
        title.setText(courseModel.getName());
        desc.setText(courseModel.getDescription());
        realm.close();

        Button button = findViewById(R.id.course_atvt_btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseActivity.this, LessonActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}