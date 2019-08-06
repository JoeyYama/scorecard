package com.jyamanouchi.golfscorebasic;

import com.jyamanouchi.golfscorebasic.database.ParEntity;

import java.util.List;

public interface DataTransfer {
    void onChange(List<ParEntity> data);
}
