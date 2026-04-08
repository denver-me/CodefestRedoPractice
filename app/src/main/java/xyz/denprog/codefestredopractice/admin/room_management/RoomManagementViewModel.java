package xyz.denprog.codefestredopractice.admin.room_management;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.RoomDao;
import xyz.denprog.codefestredopractice.database.entity.Room;

@HiltViewModel
public class RoomManagementViewModel extends ViewModel {

    private final RoomDao roomDao;

    @Inject
    public RoomManagementViewModel(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<Room> getAllRooms() {
        return roomDao.getAllRooms();
    }

    public long insertRoom(Room room) {
        return roomDao.insertRoom(room);
    }

    public void updateRoom(Room room) {
        roomDao.updateRoom(room);
    }

    public void deleteRoom(Room room) {
        roomDao.deleteRoom(room);
    }
}
