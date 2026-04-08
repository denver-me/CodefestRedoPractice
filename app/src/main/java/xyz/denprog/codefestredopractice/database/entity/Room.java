package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Room {
    @PrimaryKey
    public Long roomId;
    public String roomName;
    public Float roomPrice;
}
