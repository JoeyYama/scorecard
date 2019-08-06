package com.jyamanouchi.golfscorebasic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import static com.jyamanouchi.golfscorebasic.ScoreActivity.EXTRA_DATA_ID;
import static com.jyamanouchi.golfscorebasic.ScoreActivity.EXTRA_DATA_SCORE1;
import static com.jyamanouchi.golfscorebasic.ScoreActivity.EXTRA_DATA_SCORE2;
import static com.jyamanouchi.golfscorebasic.ScoreActivity.EXTRA_DATA_SCORE3;
import static com.jyamanouchi.golfscorebasic.ScoreActivity.EXTRA_DATA_SCORE4;

public class UpdateActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE1 = "EXTRA_SCORE1";
    public static final String EXTRA_SCORE2 = "EXTRA_SCORE2";
    public static final String EXTRA_SCORE3 = "EXTRA_SCORE3";
    public static final String EXTRA_SCORE4 = "EXTRA_SCORE4";

    public static final String EXTRA_REPLY_ID = "REPLY_ID";

    private EditText mEditScore1, mEditScore2, mEditScore3, mEditScore4;
    private Handler handler = new Handler();
    private int score1, score2, score3, score4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mEditScore1 = findViewById(R.id.edit_score1);
        mEditScore2 = findViewById(R.id.edit_score2);
        mEditScore3 = findViewById(R.id.edit_score3);
        mEditScore4 = findViewById(R.id.edit_score4);

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            score1 = extras.getInt(EXTRA_DATA_SCORE1, 0);
            if (score1 > 0) {
                mEditScore1.setText(String.valueOf(score1));
                mEditScore1.setSelection(String.valueOf(score1).length());
            }
            score2 = extras.getInt(EXTRA_DATA_SCORE2, 0);
            if (score2 > 0) {
                mEditScore2.setText(String.valueOf(score2));
                mEditScore2.setSelection(String.valueOf(score2).length());
            }
            score3 = extras.getInt(EXTRA_DATA_SCORE3, 0);
            if (score3 > 0) {
                mEditScore3.setText(String.valueOf(score3));
                mEditScore3.setSelection(String.valueOf(score3).length());
            }
            score4 = extras.getInt(EXTRA_DATA_SCORE4, 0);
            if (score4 > 0) {
                mEditScore4.setText(String.valueOf(score4));
                mEditScore4.setSelection(String.valueOf(score4).length());
            }
        } // Otherwise, start with empty fields.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, ScoreActivity).
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditScore1.getText())) {
                    // No data was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new score that the user entered.
                    if(mEditScore1.length()> 0) {
                        score1 = Integer.parseInt(mEditScore1.getText().toString());
                    } else {
                        score1 = 0;
                    }
                    if(mEditScore2.length()> 0) {
                        score2 = Integer.parseInt(mEditScore2.getText().toString());
                    } else {
                        score2 = 0;
                    }
                    if(mEditScore3.length()> 0) {
                        score3 = Integer.parseInt(mEditScore3.getText().toString());
                    } else {
                        score3 = 0;
                    }
                    if(mEditScore4.length()> 0) {
                        score4 = Integer.parseInt(mEditScore4.getText().toString());
                    } else {
                        score4 = 0;
                    }

                    // Put the new score in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_SCORE1, score1);
                    replyIntent.putExtra(EXTRA_SCORE2, score2);
                    replyIntent.putExtra(EXTRA_SCORE3, score3);
                    replyIntent.putExtra(EXTRA_SCORE4, score4);

                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
