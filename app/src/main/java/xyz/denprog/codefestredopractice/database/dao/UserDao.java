package xyz.denprog.codefestredopractice.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import xyz.denprog.codefestredopractice.admin.users_list.PendingUserItem;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.database.entity.UserRequests;

@Dao
public interface UserDao {
    @Insert
    Long insertUser(User user);
    @Insert
    Long insertUserRequest(UserRequests userRequests);

    @Query("SELECT * FROM User WHERE User.email = :email AND User.password = :password")
    User login(String email, String password);

    @Query("SELECT " +
            "UserRequests.requestId AS requestId, " +
            "User.password, " +
            "User.email, " +
            "User.firstName, " +
            "User.middleName, " +
            "User.lastName, " +
            "User.isAdmin, " +
            "User.id " +
            "FROM User " +
            "INNER JOIN UserRequests ON User.id = UserRequests.id " +
            "WHERE UserRequests.isApproved IS NULL")
    List<PendingUserItem> getAllPendingUserList();

    @Query("UPDATE UserRequests SET isApproved = :isApproved WHERE requestId = :requestId")
    void updateUserRequestApproval(long requestId, boolean isApproved);

    @Query("SELECT * FROM User WHERE User.isAdmin = 1")
    User isAdminUserExist();

}
