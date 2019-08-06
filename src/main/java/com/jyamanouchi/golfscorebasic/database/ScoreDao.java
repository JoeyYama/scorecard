package com.jyamanouchi.golfscorebasic.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM golfScore ORDER BY hole")
    LiveData<List<ScoreEntity>> getScores();

    @Query("SELECT * FROM golfScore")
    ScoreEntity[] getScoreInfo();

    @Query("SELECT SUM(score1) AS total1 FROM golfScore")
    Integer getTotal1();

    @Query("SELECT SUM(score2) AS total2 FROM golfScore")
    Integer getTotal2();

    @Query("SELECT SUM(score3) AS total3 FROM golfScore")
    Integer getTotal3();

    @Query("SELECT SUM(score4) AS total4 FROM golfScore")
    Integer getTotal4();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(ScoreEntity score);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateScore(ScoreEntity score);

    @Query("UPDATE golfScore SET score1 = 0, score2 = 0, score3 = 0, score4 = 0")
    void resetScores();

}