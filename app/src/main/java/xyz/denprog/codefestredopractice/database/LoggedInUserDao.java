package xyz.denprog.codefestredopractice.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import xyz.denprog.codefestredopractice.database.entity.LoggedInUser;

@Dao
public interface LoggedInUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLoggedInUser(LoggedInUser loggedInUser);

    @Query("SELECT * FROM LoggedInUser WHERE sessionId = 1 LIMIT 1")
    LoggedInUser getLoggedInUser();

    @Query("DELETE FROM LoggedInUser WHERE sessionId = 1")
    void clearLoggedInUser();
}
