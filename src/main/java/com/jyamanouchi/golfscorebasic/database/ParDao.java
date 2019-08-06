package com.jyamanouchi.golfscorebasic.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ParDao {

    @Query("SELECT * FROM golfPar ORDER BY hole")
    LiveData<List<ParEntity>> getPar();

    @Query("SELECT * FROM golfPar")
    ParEntity[] getPar2();

     @Insert
    void insert(ParEntity par);

    @Update
    void update(ParEntity par);

    @Query("UPDATE golfPar SET par = :par WHERE hole = :hole")
    void updatePar(int hole, int par);
}
