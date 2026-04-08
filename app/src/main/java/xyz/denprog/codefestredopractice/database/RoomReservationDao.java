package xyz.denprog.codefestredopractice.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import xyz.denprog.codefestredopractice.database.entity.RoomReservation;
import xyz.denprog.codefestredopractice.admin.ui.home.AdminReservationItem;
import xyz.denprog.codefestredopractice.user.ui.reservations.UserReservationItem;

@Dao
public interface RoomReservationDao {

    @Insert
    long insertReservation(RoomReservation roomReservation);

    @Query("SELECT COUNT(*) FROM RoomReservation WHERE roomId = :roomId")
    int getReservationCountForRoom(long roomId);

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

    @Query("SELECT " +
            "RoomReservation.reservationId AS reservationId, " +
            "RoomReservation.userId AS userId, " +
            "RoomReservation.roomId AS roomId, " +
            "RoomReservation.reservationDate AS reservationDate, " +
            "Room.roomName AS roomName, " +
            "Room.roomPrice AS roomPrice, " +
            "User.firstName AS firstName, " +
            "User.lastName AS lastName, " +
            "User.email AS email " +
            "FROM RoomReservation " +
            "INNER JOIN Room ON Room.roomId = RoomReservation.roomId " +
            "INNER JOIN User ON User.id = RoomReservation.userId " +
            "ORDER BY RoomReservation.reservationDate DESC")
    List<AdminReservationItem> getAllReservations();
}
