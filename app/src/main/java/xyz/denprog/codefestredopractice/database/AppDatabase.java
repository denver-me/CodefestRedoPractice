package xyz.denprog.codefestredopractice.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.database.entity.LoggedInUser;
import xyz.denprog.codefestredopractice.database.dao.UserDao;
import xyz.denprog.codefestredopractice.database.entity.RoomReservation;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.database.entity.UserRequests;

@Database(
        entities = {
                LoggedInUser.class,
                Room.class,
                User.class,
                UserRequests.class,
                RoomReservation.class
        },
        version = 4,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoggedInUserDao provideLoggedInUserDao();
    public abstract UserDao provideUserDao();
    public abstract RoomDao provideRoomDao();
    public abstract RoomReservationDao provideRoomReservationDao();
}
