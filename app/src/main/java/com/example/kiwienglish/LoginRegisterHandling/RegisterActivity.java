package com.example.kiwienglish.LoginRegisterHandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.R;

import org.bson.types.ObjectId;

import java.util.regex.Pattern;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn;
        btn = (Button) findViewById(R.id.register_activity_register_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameedtText = (EditText) findViewById(R.id.register_activity_username_edtText);
                EditText passwordedtText = (EditText) findViewById(R.id.register_activity_password_edtText);
                EditText rpasswordedtText = (EditText) findViewById(R.id.register_activity_rpassword_edtText);

                String username = usernameedtText.getText().toString();
                String password = passwordedtText.getText().toString();
                String rpassword = rpasswordedtText.getText().toString();

                if (isValid(username, password, rpassword)) {
                    Realm realm = Database.getDBInstance();

                    Account find = realm.where(Account.class).equalTo("username", username).findFirst();
                    if (find != null) {
                        Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        realm.beginTransaction();
                        Account account = realm.createObject(Account.class, new ObjectId());
                        account.setUsername(username);
                        account.setPassword(password);
                        account.setEmail("");
                        account.setCoursesAttended(0);
                        account.setCorrectAnswers(0);
                        account.setWrongAnswers(0);
                        account.setNew(true);
                        realm.commitTransaction();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public boolean isValid(String username, String password, String rpassword) {

        Pattern patternUser = Pattern.compile("[a-z\\d_-]{6,12}$");
        Pattern patternPassword = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,12})");

        if (!patternUser.matcher(username).matches()) {
            Toast.makeText(RegisterActivity.this, "Tài khoản không hợp lệ! 6 - 12 ký tự, không chứa dấu, khoảng trắng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!patternPassword.matcher(password).matches()) {
            Toast.makeText(this, "Mật khẩu không hợp lệ! 8 - 12 ký tự, không chứa dấu, khoảng trắng, có ít nhất 1 chữ số và chữ in hoa, in thường.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(rpassword)) {
            Toast.makeText(RegisterActivity.this, "Re-password không khớp!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}