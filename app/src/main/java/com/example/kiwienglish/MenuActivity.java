package com.example.kiwienglish;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kiwienglish.AccountSettings.AccountSettingsActivity;
import com.example.kiwienglish.CourseHandling.LessonActivity;
import com.example.kiwienglish.Information.InformationActivity;
import com.example.kiwienglish.LoginRegisterHandling.LoginActivity;
import com.example.kiwienglish.Statistics.StatisticActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView tv = findViewById(R.id.menu_tv2);
        tv.setText(CurrentAccount.getInstance().getUsername());
        Log.e("currentAccount", CurrentAccount.getInstance().getUsername());

        CardView cardView = findViewById(R.id.menu_cardview_OPTION_COURSE);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CourseListActivity.class);
                startActivity(intent);
            }
        });

        CardView cardView1 = findViewById(R.id.menu_cardview_OPTION_LOGOUT);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this, R.style.AlertDialogAppTheme);
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Tôi chắc chắn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences.Editor editor = getSharedPreferences("autoLogin", MODE_PRIVATE).edit();
                                editor.putString("username", "");
                                editor.putString("password", "");
                                editor.putBoolean("autologin", false);
                                editor.apply();
                                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        CardView cardView2 = findViewById(R.id.menu_cardview_OPTION_INFORMATION);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });

        CardView cardView3 = findViewById(R.id.menu_cardview_OPTION_SETTINGS);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });

        CardView cardView4 = findViewById(R.id.menu_cardview_OPTION_STATISTICS);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this, R.style.AlertDialogAppTheme);
        builder.setMessage("Bạn có chắc chắn muốn thoát ứng dụng?")
                .setPositiveButton("Tôi chắc chắn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}