package com.example.kiwienglish.LoginRegisterHandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.kiwienglish.R;

public class RestorePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        EditText editText = findViewById(R.id.restore_activity_username_edtText);

        EditText password = findViewById(R.id.restore_activity_password_edtText);

        EditText rpassword = findViewById(R.id.restore_activity_rpassword_edtText);

        password.setEnabled(false);
        rpassword.setEnabled(false);


    }
}