package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomReservation {
    @PrimaryKey
    public Long reservationId;
    public Long roomId;
    public Long reservationDate;
}
