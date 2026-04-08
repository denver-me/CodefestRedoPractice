package xyz.denprog.codefestredopractice.database.entity;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserRequests {
    @PrimaryKey(autoGenerate = true)
    public long requestId;
    public long id;

    @Nullable
    public Boolean isApproved;
}
