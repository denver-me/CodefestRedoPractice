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
}
