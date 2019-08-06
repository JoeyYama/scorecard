package com.jyamanouchi.golfscorebasic;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.TextView;

import com.jyamanouchi.golfscorebasic.database.AppDatabase;
import com.jyamanouchi.golfscorebasic.database.ParDao;
import com.jyamanouchi.golfscorebasic.database.ParEntity;
import com.jyamanouchi.golfscorebasic.database.ScoreDao;
import com.jyamanouchi.golfscorebasic.database.ScoreEntity;

import java.lang.ref.WeakReference;
import java.util.List;

public class ScoreRepository {

    private ScoreDao mScoreDao;
    private ParDao mParDao;

    private LiveData<List<ScoreEntity>> mScores;
    private LiveData<List<ParEntity>> mPar;
    private TextView textView;

    ScoreRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mScoreDao = db.scoreDao();
        mParDao = db.parDao();
        mScores = mScoreDao.getScores();
        mPar = mParDao.getPar();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<ScoreEntity>> getScores() {
        return mScores;
    }

    LiveData<List<ParEntity>> getPar() { return mPar; }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insertScore(ScoreEntity score) {
        new insertAsyncTask(mScoreDao).execute(score);
    }

    public void updateScore(ScoreEntity score) {
        new updateAsyncTask(mScoreDao).execute(score);
    }

    public void resetScores() {
        new resetScoresAsyncTask(mScoreDao).execute();
    }

    public void updatePar(ParEntity par) { new setParAsyncTask(mParDao).execute(par);}

    public void getTotal1(TextView textView) {
        new getTotal1AsyncTask(mScoreDao, textView).execute();
    }

    public void getTotal2(TextView textView) {
        new getTotal2AsyncTask(mScoreDao, textView).execute();
    }

    public void getTotal3(TextView textView) {
        new getTotal3AsyncTask(mScoreDao, textView).execute();
    }

    public void getTotal4(TextView textView) {
        new getTotal4AsyncTask(mScoreDao, textView).execute();
    }


    private static class insertAsyncTask extends AsyncTask<ScoreEntity, Void, Void> {

        private ScoreDao mAsyncTaskDao;

        insertAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ScoreEntity... params) {
            mAsyncTaskDao.insertScore(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<ScoreEntity, Void, Void> {
        private ScoreDao mAsyncTaskDao;

        updateAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ScoreEntity... params) {
            mAsyncTaskDao.updateScore(params[0]);
            return null;
        }
    }

    private static class getTotal1AsyncTask extends AsyncTask<Void, Void, String> {
        private ScoreDao mAsyncTaskDao;
        private WeakReference<TextView> mTextView;

        getTotal1AsyncTask(ScoreDao dao, TextView tv) {
            mAsyncTaskDao = dao;
            this.mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return String.valueOf(mAsyncTaskDao.getTotal1());

        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
        }

    }

    private static class getTotal2AsyncTask extends AsyncTask<Void, Void, String> {
        private ScoreDao mAsyncTaskDao;
        private WeakReference<TextView> mTextView;

        getTotal2AsyncTask(ScoreDao dao, TextView tv) {
            mAsyncTaskDao = dao;
            this.mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return String.valueOf(mAsyncTaskDao.getTotal2());

        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
        }

    }

    private static class getTotal3AsyncTask extends AsyncTask<Void, Void, String> {
        private ScoreDao mAsyncTaskDao;
        private WeakReference<TextView> mTextView;

        getTotal3AsyncTask(ScoreDao dao, TextView tv) {
            mAsyncTaskDao = dao;
            this.mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return String.valueOf(mAsyncTaskDao.getTotal3());

        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
        }

    }

    private static class getTotal4AsyncTask extends AsyncTask<Void, Void, String> {
        private ScoreDao mAsyncTaskDao;
        private WeakReference<TextView> mTextView;

        getTotal4AsyncTask(ScoreDao dao, TextView tv) {
            mAsyncTaskDao = dao;
            this.mTextView = new WeakReference<>(tv);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return String.valueOf(mAsyncTaskDao.getTotal4());

        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
        }

    }

    private static class resetScoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScoreDao mAsyncTaskDao;

        resetScoresAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.resetScores();
            return null;
        }
    }

    private static class setParAsyncTask extends AsyncTask<ParEntity, Void, Void> {

        private ParDao mAsyncTaskDao;

        setParAsyncTask(ParDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ParEntity... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

}
