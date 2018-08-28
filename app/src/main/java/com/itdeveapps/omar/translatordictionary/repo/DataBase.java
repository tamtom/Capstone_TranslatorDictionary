package com.itdeveapps.omar.translatordictionary.repo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {SearchHistoryEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class DataBase extends RoomDatabase {

    private static final String LOG_TAG = DataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "theDataBase";
    private static DataBase sInstance;

    public static DataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new DataBase instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DataBase.class, DataBase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the DataBase instance");
        return sInstance;
    }

    public abstract SearchHistoryDao searchHistoryDao();

}
