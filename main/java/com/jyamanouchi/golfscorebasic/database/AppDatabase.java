package com.jyamanouchi.golfscorebasic.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

//Version 2 - added par as a field to the database
@Database(entities = {ScoreEntity.class, ParEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "golfscorecard";

    public abstract ScoreDao scoreDao();
    public abstract ParDao parDao();

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return sInstance;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(sInstance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ScoreDao mScoreDao;
        private final ParDao mParDao;

        PopulateDbAsync(AppDatabase db) {

            mScoreDao = db.scoreDao();
            mParDao = db.parDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //Log.d("AppDatabase", "doInBackground");

            //sInstance.clearAllTables();

            //If the score entity is empty, create 18 entities for each hole
            if (mScoreDao.getScoreInfo().length < 1) {
                for (int i = 0; i < 18; i++) {
                    ScoreEntity score = new ScoreEntity(i + 1,  0, 0, 0, 0);
                    mScoreDao.insertScore(score);
                }
            }

            //If the par entity is empty, default all holes to a par of 4
            if(mParDao.getPar2().length < 1) {
                for(int i=0;i < 18; i++) {
                    ParEntity par  = new ParEntity(i+1, 4);
                    mParDao.insert(par);
                }
            }
            return null;
        }
    }
}