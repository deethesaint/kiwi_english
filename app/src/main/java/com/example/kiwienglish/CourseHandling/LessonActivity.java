package com.example.kiwienglish.CourseHandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kiwienglish.CurrentAccount;
import com.example.kiwienglish.Database.Account;
import com.example.kiwienglish.Database.Choice;
import com.example.kiwienglish.Database.ContentModel;
import com.example.kiwienglish.Database.CourseModel;
import com.example.kiwienglish.Database.Database;
import com.example.kiwienglish.Database.Filling;
import com.example.kiwienglish.Database.LessonModel;
import com.example.kiwienglish.Database.Speaking;
import com.example.kiwienglish.LoginRegisterHandling.LoginActivity;
import com.example.kiwienglish.MenuActivity;
import com.example.kiwienglish.R;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class LessonActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;
    String subscriptionKey = "e1e8e489bc7546acb23e4eff42df758f";
    String serviceRegion = "southeastasia";
    final int[] correctAnswer = {0};
    final int[] wrongAnswer = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        textToSpeech = new TextToSpeech(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        ObjectId id = (ObjectId) bundle.get("object_course");
        Realm realm = Database.getDBInstance();

        CourseModel courseModel = realm.where(CourseModel.class)
                .equalTo("ID", id)
                .findFirst();


        final int lessonIndex[] = {0};

        List<LessonModel> lessons = courseModel.getLessons();
        final LessonModel[] lesson = {lessons.get(lessonIndex[0])};

        LessonDisplay(lesson[0].getLessonID());

        Button nextLessonBtn = findViewById(R.id.lesson_atvt_btn_next_lesson);
        Button homeBtn = findViewById(R.id.lesson_atvt_last_stats_home_btn);
        nextLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lessonIndex[0] == lessons.size() - 1) {
                    ConstraintLayout lastLayout = findViewById(R.id.lesson_atvt_layout_congrats);
                    lastLayout.setVisibility(View.VISIBLE);
                    TextView tv1 = findViewById(R.id.lesson_atvt_last_stats_tv1);
                    TextView tv2 = findViewById(R.id.lesson_atvt_last_stats_tv2);
                    tv1.setText(tv1.getText().toString() + String.valueOf(correctAnswer[0]));
                    tv2.setText(tv2.getText().toString() + String.valueOf(wrongAnswer[0]));
                    realm.beginTransaction();
                    Account account = realm.where(Account.class)
                            .equalTo("accountID", CurrentAccount.getInstance().getAccountID())
                            .findFirst();
                    if (account != null) {
                        account.setCorrectAnswers(account.getCorrectAnswers() + correctAnswer[0]);
                        account.setWrongAnswers(account.getWrongAnswers() + wrongAnswer[0]);
                        account.setCoursesAttended(account.getCoursesAttended() + 1);
                    }
                    realm.commitTransaction();
                    realm.close();
                } else {
                    ++lessonIndex[0];
                    lesson[0] = lessons.get(lessonIndex[0]);
                    ConstraintLayout nextLesson_constraintLayout = findViewById(R.id.lesson_atvt_layout_nextlesson);
                    nextLesson_constraintLayout.setVisibility(View.GONE);
                    LessonDisplay(lesson[0].getLessonID());
                }
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.close();
                Intent intent = new Intent(LessonActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void LessonDisplay(ObjectId id) {
        ConstraintLayout constraintLayout = findViewById(R.id.lesson_atvt_layout_presentation);
        ConstraintLayout content_constraintLayout = findViewById(R.id.lesson_atvt_layout_content);
        ConstraintLayout choice_constraintLayout = findViewById(R.id.lesson_atvt_layout_choice);
        ConstraintLayout filling_constraintLayout = findViewById(R.id.lesson_atvt_layout_filling);
        ConstraintLayout speaking_constraintLayout = findViewById(R.id.lesson_atvt_layout_speaking);
        ConstraintLayout nextLesson_constraintLayout = findViewById(R.id.lesson_atvt_layout_nextlesson);
        ConstraintLayout lastLayout = findViewById(R.id.lesson_atvt_layout_congrats);
        MediaPlayer correctSound = MediaPlayer.create(this, R.raw.correct_answer);
        MediaPlayer wrongSound = MediaPlayer.create(this, R.raw.wrong_answer);
        constraintLayout.setVisibility(View.VISIBLE);
        Realm realm = Database.getDBInstance();
        LessonModel lesson = realm.where(LessonModel.class)
                .equalTo("LessonID", id)
                .findFirst();
        TextView title = findViewById(R.id.lesson_atvt_layout_presentation_tv_title);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                title.setText(lesson.getLessonName());
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        title.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                title.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        title.startAnimation(fadeIn);
        List<ContentModel> contentModels = lesson.getContentList();
        List<Choice> choices = lesson.getChoices();
        List<Filling> fillings = lesson.getFillings();
        List<Speaking> speakings = lesson.getSpeakings();

        final int[] contentIndex = {0};
        final int[] choiceIndex = {0};
        final int[] fillingIndex = {0};

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!contentModels.isEmpty()) {

                    content_constraintLayout.setVisibility(View.VISIBLE);
                    final String[] data = {contentModels.get(0).getData()};
                    final String[] speech = {contentModels.get(0).getSpeech()};

                    TextView tv = findViewById(R.id.lesson_content_data);
                    tv.setText(data[0]);
                    Button speakBtn = findViewById(R.id.lesson_content_btn_speak);
                    Button nextBtn = findViewById(R.id.lesson_content_btn_next);

                    speakBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            textToSpeech.speak(speech[0], TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });

                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (contentIndex[0] < contentModels.size() - 1) {
                                ++contentIndex[0];
                                data[0] = contentModels.get(contentIndex[0]).getData();
                                speech[0] = contentModels.get(contentIndex[0]).getSpeech();
                                tv.setText(data[0]);
                            }
                            else if (contentIndex[0] == contentModels.size() - 1) {
                                data[0] = contentModels.get(contentIndex[0]).getData();
                                speech[0] = contentModels.get(contentIndex[0]).getSpeech();
                                tv.setText(data[0]);
                                ++contentIndex[0];
                                if (contentIndex[0] == contentModels.size()) {
                                    content_constraintLayout.setVisibility(View.GONE);
                                    choice_constraintLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                }

                if (!choices.isEmpty()) {

                    final String[] question = {choices.get(0).getFirst()};
                    final String[] option1 = {choices.get(0).getOption1()};
                    final String[] option2 = {choices.get(0).getOption2()};
                    final String[] option3 = {choices.get(0).getOption3()};
                    final String[] option4 = {choices.get(0).getOption4()};
                    final String[] answer = {choices.get(0).getAnswer()};
                    TextView questionTv = findViewById(R.id.lesson_atvt_choice_question_tv);
                    Button btn1 = findViewById(R.id.lesson_atvt_choice_op1);
                    Button btn2 = findViewById(R.id.lesson_atvt_choice_op2);
                    Button btn3 = findViewById(R.id.lesson_atvt_choice_op3);
                    Button btn4 = findViewById(R.id.lesson_atvt_choice_op4);
                    Button nextBtn = findViewById(R.id.lesson_atvt_choice_next_btn);
                    questionTv.setText(question[0]);
                    btn1.setText(option1[0]);
                    btn2.setText(option2[0]);
                    btn3.setText(option3[0]);
                    btn4.setText(option4[0]);

                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (option1[0].equals(answer[0])) {
                                btn1.setTextColor(getResources().getColor(R.color.right_answer_color));
                                correctSound.start();
                                ++correctAnswer[0];
                            } else {
                                btn1.setTextColor(getResources().getColor(R.color.wrong_answer_color));
                                wrongSound.start();
                                ++wrongAnswer[0];
                            }
                            btn1.setClickable(false);
                            btn2.setClickable(false);
                            btn3.setClickable(false);
                            btn4.setClickable(false);
                            nextBtn.setVisibility(View.VISIBLE);
                        }
                    });

                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (option2[0].equals(answer[0])) {
                                btn2.setTextColor(getResources().getColor(R.color.right_answer_color));
                                correctSound.start();
                                ++correctAnswer[0];
                            } else {
                                btn2.setTextColor(getResources().getColor(R.color.wrong_answer_color));
                                wrongSound.start();
                                ++wrongAnswer[0];
                            }
                            btn1.setClickable(false);
                            btn2.setClickable(false);
                            btn3.setClickable(false);
                            btn4.setClickable(false);
                            nextBtn.setVisibility(View.VISIBLE);
                        }
                    });

                    btn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (option3[0].equals(answer[0])) {
                                btn3.setTextColor(getResources().getColor(R.color.right_answer_color));
                                correctSound.start();
                                ++correctAnswer[0];
                            } else {
                                btn3.setTextColor(getResources().getColor(R.color.wrong_answer_color));
                                wrongSound.start();
                                ++wrongAnswer[0];
                            }
                            btn1.setClickable(false);
                            btn2.setClickable(false);
                            btn3.setClickable(false);
                            btn4.setClickable(false);
                            nextBtn.setVisibility(View.VISIBLE);
                        }
                    });

                    btn4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (option4[0].equals(answer[0])) {
                                btn4.setTextColor(getResources().getColor(R.color.right_answer_color));
                                correctSound.start();
                                ++correctAnswer[0];
                            } else {
                                btn4.setTextColor(getResources().getColor(R.color.wrong_answer_color));
                                wrongSound.start();
                                ++wrongAnswer[0];
                            }
                            btn1.setClickable(false);
                            btn2.setClickable(false);
                            btn3.setClickable(false);
                            btn4.setClickable(false);
                            nextBtn.setVisibility(View.VISIBLE);
                        }
                    });

                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (choiceIndex[0] < choices.size() - 1) {
                                ++choiceIndex[0];
                                question[0] = choices.get(choiceIndex[0]).getFirst();
                                option1[0] = choices.get(choiceIndex[0]).getOption1();
                                option2[0] = choices.get(choiceIndex[0]).getOption2();
                                option3[0] = choices.get(choiceIndex[0]).getOption3();
                                option4[0] = choices.get(choiceIndex[0]).getOption4();
                                answer[0] = choices.get(choiceIndex[0]).getAnswer();
                                questionTv.setText(question[0]);
                                btn1.setText(option1[0]);
                                btn2.setText(option2[0]);
                                btn3.setText(option3[0]);
                                btn4.setText(option4[0]);
                                btn1.setClickable(true);
                                btn2.setClickable(true);
                                btn3.setClickable(true);
                                btn4.setClickable(true);
                                nextBtn.setVisibility(View.INVISIBLE);
                                btn1.setTextColor(getResources().getColor(R.color.black));
                                btn2.setTextColor(getResources().getColor(R.color.black));
                                btn3.setTextColor(getResources().getColor(R.color.black));
                                btn4.setTextColor(getResources().getColor(R.color.black));
                            }
                            else if (choiceIndex[0] == choices.size() - 1) {
                                question[0] = choices.get(choiceIndex[0]).getFirst();
                                option1[0] = choices.get(choiceIndex[0]).getOption1();
                                option2[0] = choices.get(choiceIndex[0]).getOption2();
                                option3[0] = choices.get(choiceIndex[0]).getOption3();
                                option4[0] = choices.get(choiceIndex[0]).getOption4();
                                answer[0] = choices.get(choiceIndex[0]).getAnswer();
                                questionTv.setText(question[0]);
                                btn1.setText(option1[0]);
                                btn2.setText(option2[0]);
                                btn3.setText(option3[0]);
                                btn4.setText(option4[0]);
                                btn1.setClickable(true);
                                btn2.setClickable(true);
                                btn3.setClickable(true);
                                btn4.setClickable(true);
                                nextBtn.setVisibility(View.INVISIBLE);
                                btn1.setTextColor(getResources().getColor(R.color.black));
                                btn2.setTextColor(getResources().getColor(R.color.black));
                                btn3.setTextColor(getResources().getColor(R.color.black));
                                btn4.setTextColor(getResources().getColor(R.color.black));
                                ++choiceIndex[0];
                                if (choiceIndex[0] == choices.size()) {
                                    choice_constraintLayout.setVisibility(View.GONE);
                                    filling_constraintLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                } else {
                    choice_constraintLayout.setVisibility(View.GONE);
                    filling_constraintLayout.setVisibility(View.VISIBLE);
                }

                if (!fillings.isEmpty()) {
                    final String[] question = {fillings.get(0).getQuestion()};
                    final String[] answer = {fillings.get(0).getAnswer()};
                    TextView questionTv = findViewById(R.id.lesson_atvt_filling_question_tv);
                    Button submitBtn = findViewById(R.id.lesson_atvt_filling_btn_submit);
                    Button nextBtn = findViewById(R.id.lesson_atvt_filling_btn_next);
                    EditText editText = findViewById(R.id.lesson_atvt_filling_edtText);
                    TextView statusTextView = findViewById(R.id.lesson_atvt_filling_status_tv);
                    questionTv.setText(question[0]);

                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (editText.getText().toString().equals(answer[0])) {
                                correctSound.start();
                                submitBtn.setClickable(false);
                                editText.setEnabled(false);
                                editText.setHintTextColor(getResources().getColor(R.color.right_answer_color));
                                Log.e("Correct Answer, EditText", editText.getText().toString() + ",  " + answer[0]);
                                nextBtn.setVisibility(View.VISIBLE);
                                statusTextView.setText("CORRECT! :D");
                                statusTextView.setTextColor(getResources().getColor(R.color.right_answer_color));
                                ++correctAnswer[0];
                            } else {
                                wrongSound.start();
                                submitBtn.setClickable(false);
                                editText.setEnabled(false);
                                editText.setHintTextColor(getResources().getColor(R.color.wrong_answer_color));
                                Log.e("Wrong Answer, EditText", editText.getText().toString() + ",  " + answer[0]);
                                nextBtn.setVisibility(View.VISIBLE);
                                statusTextView.setText("WRONG! :<");
                                statusTextView.setTextColor(getResources().getColor(R.color.wrong_answer_color));
                                ++wrongAnswer[0];
                            }
                        }
                    });

                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (fillingIndex[0] < fillings.size() - 1) {
                                ++fillingIndex[0];
                                questionTv.setText(fillings.get(fillingIndex[0]).getQuestion());
                                answer[0] = fillings.get(fillingIndex[0]).getAnswer();
                                editText.setText("");
                                editText.setEnabled(true);
                                editText.setHintTextColor(getResources().getColor(R.color.second_background_color));
                                submitBtn.setClickable(true);
                                statusTextView.setText("");
                                nextBtn.setVisibility(View.INVISIBLE);
                            } else if (fillingIndex[0] == fillings.size() - 1) {
                                questionTv.setText(fillings.get(fillingIndex[0]).getQuestion());
                                answer[0] = fillings.get(fillingIndex[0]).getAnswer();
                                editText.setText("");
                                editText.setEnabled(true);
                                editText.setHintTextColor(getResources().getColor(R.color.second_background_color));
                                submitBtn.setClickable(true);
                                statusTextView.setText("");
                                nextBtn.setVisibility(View.INVISIBLE);
                                ++fillingIndex[0];
                                if (fillingIndex[0] == fillings.size()) {
                                    filling_constraintLayout.setVisibility(View.GONE);
                                    nextLesson_constraintLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                } else {
                    filling_constraintLayout.setVisibility(View.GONE);
                    nextLesson_constraintLayout.setVisibility(View.VISIBLE);
                }

//                if (!speakings.isEmpty()) {
//                    speaking_constraintLayout.setVisibility(View.GONE);
//                    nextLesson_constraintLayout.setVisibility(View.VISIBLE);
//                } else {
//                    speaking_constraintLayout.setVisibility(View.GONE);
//                }
            }
        }, 3000);
        realm.close();
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("TTS", "Language isn't available");
            } else {
                Log.i("TTS", "TTS engine is ready");
            }
        } else {
            Log.e("TTS", "TTS init failed");
            Log.i("TTS", "Download TTS Data");
            try {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LessonActivity.this, R.style.AlertDialogAppTheme);
        builder.setMessage("Bạn có chắc chắn muốn hủy tiến trình học hiện tại?")
                .setPositiveButton("Tôi chắc chắn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(LessonActivity.this, MenuActivity.class);
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
}