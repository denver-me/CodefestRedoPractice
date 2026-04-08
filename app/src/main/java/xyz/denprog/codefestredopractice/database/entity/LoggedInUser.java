package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LoggedInUser {
    @PrimaryKey
    public int sessionId;
    public long userId;
    public String displayName;
    public String email;
    public boolean isAdmin;
}
