package com.gradien.rubiksolver.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.gradien.rubiksolver.data.local.dao.HistoryDao;
import com.gradien.rubiksolver.data.local.entity.HistoryEntity;

@Database(entities = {HistoryEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "rubik_solver_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
