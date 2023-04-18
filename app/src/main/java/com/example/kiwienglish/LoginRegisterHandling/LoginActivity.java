package com.example.kiwienglish.LoginRegisterHandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.CurrentAccount;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.MenuActivity;
import com.example.kiwienglish.R;
import com.example.kiwienglish.TutorialActivity;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUsername = (EditText)findViewById(R.id.login_activity_username_edtText);
        EditText edtPassword = (EditText)findViewById(R.id.login_activity_password_edtText);

        SharedPreferences preferences = getSharedPreferences("autoLogin", MODE_PRIVATE);
        boolean autoLogin = preferences.getBoolean("autologin", false);
        if (autoLogin) {
            String username = preferences.getString("username", "username");
            String password = preferences.getString("password", "password");
            Realm realm = Database.getDBInstance();
            Account account = realm.where(Account.class)
                    .equalTo("username", username)
                    .findFirst();
            if (account != null) {
                if (password.equals(account.getPassword())) {
                    CurrentAccount currentAccount = CurrentAccount.getInstance();
                    currentAccount.setAccountID(account.getAccountID());
                    currentAccount.setUsername(account.getUsername());
                    currentAccount.setPassword(account.getPassword());
                    if (account.isNew()) {
                        Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            }
        }

        Button btn;
        btn = (Button) findViewById(R.id.login_activity_login_Btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Vui lòng không để trống tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Realm realm = Database.getDBInstance();
                    Account account = realm.where(Account.class)
                            .equalTo("username", username)
                            .findFirst();
                    if (account != null) {
                        if (password.equals(account.getPassword())) {
                            CurrentAccount currentAccount = CurrentAccount.getInstance();
                            currentAccount.setAccountID(account.getAccountID());
                            currentAccount.setUsername(account.getUsername());
                            currentAccount.setPassword(account.getPassword());
                            SharedPreferences.Editor editor = getSharedPreferences("autoLogin", MODE_PRIVATE).edit();
                            CheckBox checkBox = findViewById(R.id.login_activity_nextTimeLogged_checkBox);
                            editor.putString("username", account.getUsername());
                            editor.putString("password", account.getPassword());
                            if (checkBox.isChecked()) {
                                editor.putBoolean("autologin", true);
                            }
                            else {
                                editor.putBoolean("autologin", false);
                            }
                            editor.apply();
                            if (account.isNew()) {
                                Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        Button registerBtn;
        registerBtn = (Button) findViewById(R.id.login_activity_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button restorePasswordBtn;
        restorePasswordBtn = (Button) findViewById(R.id.login_activity_forgot_Btn);
        restorePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RestorePasswordActivity.class);
                startActivity(intent);
            }
        });


    }
}