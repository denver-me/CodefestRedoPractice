package xyz.denprog.codefestredopractice.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserRequests {
    @PrimaryKey
    long requestId;
    long id;
}
