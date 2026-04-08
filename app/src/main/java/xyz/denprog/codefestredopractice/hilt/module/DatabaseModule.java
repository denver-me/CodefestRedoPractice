package xyz.denprog.codefestredopractice.hilt.module;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import xyz.denprog.codefestredopractice.database.AppDatabase;
import xyz.denprog.codefestredopractice.database.LoggedInUserDao;
import xyz.denprog.codefestredopractice.database.RoomDao;
import xyz.denprog.codefestredopractice.database.RoomReservationDao;
import xyz.denprog.codefestredopractice.database.dao.UserDao;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    public AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "AppDatabase"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    @Provides
    public UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.provideUserDao();
    }

    @Provides
    public LoggedInUserDao provideLoggedInUserDao(AppDatabase appDatabase) {
        return appDatabase.provideLoggedInUserDao();
    }

    @Provides
    public RoomDao provideRoomDao(AppDatabase appDatabase) {
        return appDatabase.provideRoomDao();
    }

    @Provides
    public RoomReservationDao provideRoomReservationDao(AppDatabase appDatabase) {
        return appDatabase.provideRoomReservationDao();
    }

}
