package xyz.denprog.codefestredopractice.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.database.entity.UserRequests;

@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);
    @Insert
    long insertUserRequest(UserRequests userRequests);
}

