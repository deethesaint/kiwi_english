package com.example.kiwienglish.AccountSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiwienglish.CurrentAccount;
import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.LoginRegisterHandling.LoginActivity;
import com.example.kiwienglish.R;

import java.util.regex.Pattern;

import io.realm.Realm;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        EditText accountEdtText = findViewById(R.id.account_settings_atvt_edt_ac);
        EditText idEdtText = findViewById(R.id.account_settings_atvt_edt_id);
        EditText emailEdtText = findViewById(R.id.account_settings_atvt_edt_mail);
        accountEdtText.setText(CurrentAccount.getInstance().getUsername());
        idEdtText.setText(CurrentAccount.getInstance().getAccountID().toString());
        accountEdtText.setEnabled(false);
        idEdtText.setEnabled(false);
        emailEdtText.setSingleLine(true);
        Button btnChangePassword = findViewById(R.id.account_settings_atvt_btn_changepswrd);
        Button btnConfirm = findViewById(R.id.account_settings_atvt_btn_confirm);

        Realm realm = Database.getDBInstance();
        Account account = realm.where(Account.class)
                        .equalTo("accountID", CurrentAccount.getInstance().getAccountID())
                        .findFirst();
        if (account != null) {
            emailEdtText.setText(account.getEmail());
        }

        btnChangePassword.setOnClickListener(view -> {
            ConstraintLayout changePasswordLayout = findViewById(R.id.account_settings_atvt_changepswrd_layout);
            changePasswordLayout.setVisibility(View.VISIBLE);

            EditText currentPasswordEdtText = findViewById(R.id.account_settings_atvt_edt_current_password);
            EditText passwordEdtText = findViewById(R.id.account_settings_atvt_edt_password);
            EditText rpasswordEdtText = findViewById(R.id.account_settings_atvt_edt_repass);


            Button btnChangeConfirm = findViewById(R.id.account_settings_atvt_btn_confirmChangepassword);
            btnChangeConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentPasswordEdtText.getText().toString().equals(account.getPassword())) {
                        Pattern patternPassword = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,12})");
                        if (!patternPassword.matcher(passwordEdtText.getText().toString()).matches()) {
                            Toast.makeText(AccountSettingsActivity.this, "Mật khẩu không hợp lệ! 8 - 12 ký tự, không chứa dấu, khoảng trắng, có ít nhất 1 chữ số và chữ in hoa, in thường.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!passwordEdtText.getText().toString().equals(rpasswordEdtText.getText().toString())) {
                            Toast.makeText(AccountSettingsActivity.this, "Mật khẩu mới và Re-password không khớp", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AccountSettingsActivity.this, "Đổi mật khẩu thành công, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                            realm.beginTransaction();
                            account.setPassword(passwordEdtText.getText().toString());
                            realm.commitTransaction();
                            realm.close();
                            SharedPreferences.Editor editor = getSharedPreferences("autoLogin", MODE_PRIVATE).edit();
                            editor.putString("username", "");
                            editor.putString("password", "");
                            editor.putBoolean("autologin", false);
                            editor.apply();
                            Intent intent = new Intent(AccountSettingsActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(AccountSettingsActivity.this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        });
        btnConfirm.setOnClickListener(view -> {
            realm.beginTransaction();
            account.setEmail(emailEdtText.getText().toString());
            realm.commitTransaction();
            realm.close();
            finish();
        });
    }
}