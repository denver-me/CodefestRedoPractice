package xyz.denprog.codefestredopractice.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import xyz.denprog.codefestredopractice.database.dao.UserDao;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.database.entity.UserRequests;

@Database(
        entities = {
                User.class,
                UserRequests.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao provideUserDao();
}
