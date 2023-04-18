package com.example.kiwienglish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.Database.Database;

import io.realm.Realm;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Button button = findViewById(R.id.tutorial_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Database.getDBInstance();
                realm.beginTransaction();
                Account account = realm.where(Account.class)
                        .equalTo("accountID", CurrentAccount.getInstance().getAccountID())
                        .findFirst();
                account.setNew(false);
                realm.commitTransaction();
                Intent intent = new Intent(TutorialActivity.this, MenuActivity.class);
            }
        });

    }
}