package xyz.denprog.codefestredopractice.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    public Long insertRoom(Room room);

    @Query("SELECT * FROM Room")
    List<Room> getAllRooms();



}
