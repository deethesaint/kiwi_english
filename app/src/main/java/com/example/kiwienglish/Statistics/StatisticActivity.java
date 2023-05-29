package com.example.kiwienglish.Statistics;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Shader;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kiwienglish.CurrentAccount;
import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.R;

import io.realm.Realm;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        TextView coursesRegisteredTextView = findViewById(R.id.stats_atvt_courses_registered_tv);
        TextView coursesAttendedTextView = findViewById(R.id.stats_atvt_courses_attended_tv);
        TextView correctAnswersTextView = findViewById(R.id.stats_atvt_correct_answer_tv);
        TextView wrongAnswersTextView = findViewById(R.id.stats_atvt_wrong_answer_tv);
        TextView nbrTextView = findViewById(R.id.stats_atvt_correct_answer_nbr_tv);

        Realm realm = Database.getDBInstance();
        Account account = realm.where(Account.class)
                .equalTo("accountID", CurrentAccount.getInstance().getAccountID())
                .findFirst();
        if (account != null) {
            coursesRegisteredTextView.setText(String.valueOf(account.getCourseModels().size()));
            coursesAttendedTextView.setText(coursesAttendedTextView.getText().toString() + String.valueOf(account.getCoursesAttended()));
            correctAnswersTextView.setText(correctAnswersTextView.getText().toString() + String.valueOf(account.getCorrectAnswers()));
            wrongAnswersTextView.setText(wrongAnswersTextView.getText().toString() + String.valueOf(account.getWrongAnswers()));
            int nbr = 0;
            try {
                nbr = (int)(account.getCorrectAnswers() / (double)((account.getCorrectAnswers() + account.getWrongAnswers())) * 100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            nbrTextView.setText(nbrTextView.getText().toString() + String.valueOf(nbr) + "%");
        }
        realm.close();
    }
}