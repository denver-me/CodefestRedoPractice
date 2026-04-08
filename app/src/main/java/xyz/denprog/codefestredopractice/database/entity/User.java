package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public long id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;
    public String password;
    public boolean isAdmin;
}
