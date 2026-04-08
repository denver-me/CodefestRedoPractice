package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomReservation {
    @PrimaryKey(autoGenerate = true)
    public long reservationId;
    public long roomId;
    public long reservationDate;
}
