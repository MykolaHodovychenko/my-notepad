package ua.opu.pnit.mynotepad.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import ua.opu.pnit.mynotepad.model.Note;

@Database(entities = {Note.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app_db.db";
    private static volatile AppDatabase instance;

    public abstract NoteDAO noteDAO();

    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null)
                    instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
            }
        }

        return instance;
    }
}
