package xyz.denprog.codefestredopractice.database;

import androidx.room.Dao;
import androidx.room.Insert;

import xyz.denprog.codefestredopractice.database.entity.RoomReservation;

@Dao
public interface RoomReservationDao {

    @Insert
    long insertReservation(RoomReservation roomReservation);
}
