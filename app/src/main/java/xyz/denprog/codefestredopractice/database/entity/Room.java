package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Room {
    @PrimaryKey(autoGenerate = true)
    public long roomId;
    public String roomName;
    public float roomPrice;
}
