package com.gradien.rubiksolver.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class HistoryEntity {
    @PrimaryKey(autoGenerate = true)
    public long id = 0;
    public long timestamp;
    public String cubeState;
    public String solution;

    public HistoryEntity(long timestamp, String cubeState, String solution) {
        this.timestamp = timestamp;
        this.cubeState = cubeState;
        this.solution = solution;
    }
}
