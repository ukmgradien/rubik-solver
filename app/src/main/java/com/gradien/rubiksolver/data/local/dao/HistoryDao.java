package com.gradien.rubiksolver.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.gradien.rubiksolver.data.local.entity.HistoryEntity;
import java.util.List;
import kotlinx.coroutines.flow.Flow;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    Flow<List<HistoryEntity>> getAllHistory();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryEntity history);

    @Query("DELETE FROM history")
    void deleteAll();
}
