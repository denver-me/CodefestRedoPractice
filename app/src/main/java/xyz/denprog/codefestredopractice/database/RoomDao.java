package xyz.denprog.codefestredopractice.database;

import androidx.room.Delete;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import xyz.denprog.codefestredopractice.database.entity.Room;

@Dao
public interface RoomDao {

    @Insert
    Long insertRoom(Room room);

    @Update
    void updateRoom(Room room);

    @Delete
    void deleteRoom(Room room);

    @Query("SELECT * FROM Room ORDER BY roomName ASC")
    List<Room> getAllRooms();

    @Query("SELECT * FROM Room WHERE roomId NOT IN (SELECT roomId FROM RoomReservation) ORDER BY roomName ASC")
    List<Room> getAvailableRooms();

    @Query("SELECT * FROM Room WHERE roomId = :roomId LIMIT 1")
    Room getRoomById(long roomId);
}
