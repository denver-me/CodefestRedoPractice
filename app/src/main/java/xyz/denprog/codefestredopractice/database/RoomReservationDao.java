package xyz.denprog.codefestredopractice.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import xyz.denprog.codefestredopractice.database.entity.RoomReservation;
import xyz.denprog.codefestredopractice.user.ui.reservations.UserReservationItem;

@Dao
public interface RoomReservationDao {

    @Insert
    long insertReservation(RoomReservation roomReservation);

    @Query("SELECT " +
            "RoomReservation.reservationId AS reservationId, " +
            "RoomReservation.userId AS userId, " +
            "RoomReservation.roomId AS roomId, " +
            "RoomReservation.reservationDate AS reservationDate, " +
            "Room.roomName AS roomName, " +
            "Room.roomPrice AS roomPrice " +
            "FROM RoomReservation " +
            "INNER JOIN Room ON Room.roomId = RoomReservation.roomId " +
            "WHERE RoomReservation.userId = :userId " +
            "ORDER BY RoomReservation.reservationDate DESC")
    List<UserReservationItem> getReservationsForUser(long userId);
}
