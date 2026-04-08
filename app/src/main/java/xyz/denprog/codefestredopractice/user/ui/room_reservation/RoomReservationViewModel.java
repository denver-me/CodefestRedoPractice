package xyz.denprog.codefestredopractice.user.ui.room_reservation;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.RoomDao;
import xyz.denprog.codefestredopractice.database.RoomReservationDao;
import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.database.entity.RoomReservation;

@HiltViewModel
public class RoomReservationViewModel extends ViewModel {

    private final RoomDao roomDao;
    private final RoomReservationDao roomReservationDao;

    @Inject
    public RoomReservationViewModel(RoomDao roomDao, RoomReservationDao roomReservationDao) {
        this.roomDao = roomDao;
        this.roomReservationDao = roomReservationDao;
    }

    public List<Room> getAllRooms() {
        return roomDao.getAllRooms();
    }

    public Room getRoomById(long roomId) {
        return roomDao.getRoomById(roomId);
    }

    public long reserveRoom(long roomId, long reservationDate) {
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.roomId = roomId;
        roomReservation.reservationDate = reservationDate;
        return roomReservationDao.insertReservation(roomReservation);
    }
}
