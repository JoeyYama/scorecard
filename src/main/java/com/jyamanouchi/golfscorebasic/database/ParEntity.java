package com.jyamanouchi.golfscorebasic.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "golfPar")
public class ParEntity {

    @PrimaryKey(autoGenerate = false)
    private int hole;
    private int par;

    public ParEntity(int hole, int par) {
        this.hole = hole;
        this.par = par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public void setHole(int hole) {
        this.hole = hole;
    }

    public int getPar() {
        return this.par;
    }

    public int getHole() { return this.hole;}
}
