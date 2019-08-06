package com.jyamanouchi.golfscorebasic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.widget.TextView;

import com.jyamanouchi.golfscorebasic.database.ParEntity;
import com.jyamanouchi.golfscorebasic.database.ScoreEntity;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = ScoreViewModel.class.getSimpleName();
    private ScoreRepository mRepository;
    private LiveData<List<ScoreEntity>> scores;
    private LiveData<List<ParEntity>> par;

    public ScoreViewModel(Application application) {
        super(application);

        mRepository = new ScoreRepository(application);
        //Log.d("ScoreViewModel", "constructor");
        scores = mRepository.getScores();
        par = mRepository.getPar();
    }

    LiveData<List<ScoreEntity>> getScores() {
        return scores;
    }

    LiveData<List<ParEntity>> getPar() { return par; }

    public void updatePar(ParEntity par) { mRepository.updatePar(par);}

    public void getTotal1(TextView textView) { mRepository.getTotal1(textView); }

    public void getTotal2(TextView textView) { mRepository.getTotal2(textView); }

    public void getTotal3(TextView textView) { mRepository.getTotal3(textView); }

    public void getTotal4(TextView textView) { mRepository.getTotal4(textView); }

    public void updateScore(ScoreEntity score) {
        mRepository.updateScore(score);
    }

    public void resetScores() {mRepository.resetScores();}

}