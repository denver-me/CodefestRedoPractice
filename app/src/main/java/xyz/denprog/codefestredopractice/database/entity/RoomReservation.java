package xyz.denprog.codefestredopractice.database.entity;

import androidx.room.Entity;

@Entity
public class RoomReservation {
    public Long reservationId;
    public Long roomId;
    public Long reservationDate;
}
