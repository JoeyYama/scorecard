package com.jyamanouchi.golfscorebasic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jyamanouchi.golfscorebasic.database.ParEntity;
import com.jyamanouchi.golfscorebasic.database.ScoreEntity;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class ScoreActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener  {

    public static final int UPDATE_SCORE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_PAR_ACTIVITY_REQUEST_CODE = 3;

    //public static final String EXTRA_DATA_UPDATE_SCORE = "extra_score";
    public static final String EXTRA_DATA_SCORE1 = "score1";
    public static final String EXTRA_DATA_SCORE2 = "score2";
    public static final String EXTRA_DATA_SCORE3 = "score3";
    public static final String EXTRA_DATA_SCORE4 = "score4";
    public static final String EXTRA_PAR = "par";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private ScoreViewModel mScoreViewModel;
    private TextView mTotal1, mTotal2, mTotal3, mTotal4;
    private TextView mPlayer1, mPlayer2, mPlayer3, mPlayer4;
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Log.d("ScoreActivity", "onCreate");
        final RecyclerView mRecyclerView = findViewById(R.id.rv_scores);
        final ScoreAdapter mAdapter = new ScoreAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mScoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);

        mTotal1 = findViewById(R.id.tv_total_score1);
        mScoreViewModel.getTotal1(mTotal1);

        mTotal2 = findViewById(R.id.tv_total_score2);
        mScoreViewModel.getTotal2(mTotal2);

        mTotal3 = findViewById(R.id.tv_total_score3);
        mScoreViewModel.getTotal3(mTotal3);

        mTotal4 = findViewById(R.id.tv_total_score4);
        mScoreViewModel.getTotal4(mTotal4);

        mScoreViewModel.getScores().observe(this, new Observer<List<ScoreEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ScoreEntity> scores) {
                // Update the cached copy of the scores in the adapter.
                mAdapter.setScores(scores);
                mScoreViewModel.getTotal1(mTotal1);
                mScoreViewModel.getTotal2(mTotal2);
                mScoreViewModel.getTotal3(mTotal3);
                mScoreViewModel.getTotal4(mTotal4);
            }
        });

        mScoreViewModel.getPar().observe(this, new Observer<List<ParEntity>>() {
            @Override
            public void onChanged(@Nullable List<ParEntity> parEntities) {
                mAdapter.setPar(parEntities);
                mScoreViewModel.getPar();
            }
        });

        mAdapter.setOnItemClickListener(new ScoreAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                ScoreEntity score = mAdapter.getScoreAtPosition(position);
                launchUpdateScoreActivity(score);
            }
        });

        mPlayer1 = findViewById(R.id.tv_player1_label);
        mPlayer2 = findViewById(R.id.tv_player2_label);
        mPlayer3 = findViewById(R.id.tv_player3_label);
        mPlayer4 = findViewById(R.id.tv_player4_label);

        setupSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Reset scores
        if (id == R.id.reset_score) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.reset_score_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            mScoreViewModel.resetScores();
            return true;
        }

        //Player Initials
        if(id == R.id.players) {
            Intent playersIntent = new Intent(this, PlayersActivity.class);
            startActivity(playersIntent);
        }

        //Course par information
        if(id == R.id.par) {
            Intent parIntent = new Intent(this, ParActivity.class);
            startActivityForResult(parIntent, UPDATE_PAR_ACTIVITY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("ScoreActiivity", "onActivityResult");
        int id= -1;

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case UPDATE_SCORE_ACTIVITY_REQUEST_CODE:
                    int score_data1 = data.getIntExtra(UpdateActivity.EXTRA_SCORE1, 0);
                    int score_data2 = data.getIntExtra(UpdateActivity.EXTRA_SCORE2, 0);
                    int score_data3 = data.getIntExtra(UpdateActivity.EXTRA_SCORE3, 0);
                    int score_data4 = data.getIntExtra(UpdateActivity.EXTRA_SCORE4, 0);
                    id = data.getIntExtra(UpdateActivity.EXTRA_REPLY_ID, -1);

                    if (id != -1) {
                        mScoreViewModel.updateScore(new ScoreEntity(id, score_data1, score_data2, score_data3, score_data4));
                    } else {
                        Toast.makeText(this, R.string.unable_to_update,
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case UPDATE_PAR_ACTIVITY_REQUEST_CODE:
                    //Log.d("ScoreActivity", "Return");
                    ArrayList<Integer> arrayListHole = data.getIntegerArrayListExtra(ParActivity.EXTRA_HOLE);
                    ArrayList<Integer> arrayListPar = data.getIntegerArrayListExtra(ParActivity.EXTRA_PAR);
                    id = data.getIntExtra(ParActivity.EXTRA_REPLY_ID, -1);
                    if (id != -1) {
                        for(int i=0;i< arrayListPar.size();i++) {
                            mScoreViewModel.updatePar(new ParEntity(arrayListHole.get(i), arrayListPar.get(i)));
                        }
                    } else {
                        Toast.makeText(this, R.string.unable_to_update,
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                        Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(this, R.string.transfer_error, Toast.LENGTH_LONG).show();
        }

    }

    public void launchUpdateScoreActivity( ScoreEntity score) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra(EXTRA_DATA_SCORE1,score.getScore(1));
        intent.putExtra(EXTRA_DATA_SCORE2,score.getScore(2));
        intent.putExtra(EXTRA_DATA_SCORE3,score.getScore(3));
        intent.putExtra(EXTRA_DATA_SCORE4,score.getScore(4));
        intent.putExtra(EXTRA_DATA_ID, score.getHole());
        startActivityForResult(intent, UPDATE_SCORE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView mRecyclerView = findViewById(R.id.rv_scores);
        mRecyclerView.getLayoutManager().scrollToPosition(lastPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecyclerView mRecyclerView = findViewById(R.id.rv_scores);
        lastPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mPlayer1.setText(sharedPreferences.getString("player1", "P1").toUpperCase());
        mPlayer2.setText(sharedPreferences.getString("player2", "P2").toUpperCase());
        mPlayer3.setText(sharedPreferences.getString("player3", "P3").toUpperCase());
        mPlayer4.setText(sharedPreferences.getString("player4", "P4").toUpperCase());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("player1")) {
            mPlayer1.setText(sharedPreferences.getString("player1", "P1F").toUpperCase());
        }
        if(s.equals("player2")) {
            mPlayer2.setText(sharedPreferences.getString("player2", "P2F").toUpperCase());
        }
        if(s.equals("player3")) {
            mPlayer3.setText(sharedPreferences.getString("player3", "P3F").toUpperCase());
        }
        if(s.equals("player4")) {
            mPlayer4.setText(sharedPreferences.getString("player4", "P4F").toUpperCase());
        }

    }
}
