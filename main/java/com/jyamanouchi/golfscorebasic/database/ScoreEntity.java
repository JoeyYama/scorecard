package com.jyamanouchi.golfscorebasic.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "golfScore")
public class ScoreEntity {

    @PrimaryKey(autoGenerate = false)
    private int hole;
    private int score1, score2, score3, score4;

    public ScoreEntity(int hole, int score1, int score2, int score3, int score4) {
        this.hole = hole;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
    }


    public int getHole() {
        return this.hole;
    }

    public int getScore(int player) {

        switch (player) {
            case 1:
                return this.score1;
            case 2:
                return this.score2;
            case 3:
                return this.score3;
            case 4:
                return this.score4;
        }

        return 0;
    }

    public int getScore1() {
        return this.score1;
    }

    public int getScore2() {
        return this.score2;
    }

    public int getScore3() {
        return this.score3;
    }

    public int getScore4() {
        return this.score4;
    }
}