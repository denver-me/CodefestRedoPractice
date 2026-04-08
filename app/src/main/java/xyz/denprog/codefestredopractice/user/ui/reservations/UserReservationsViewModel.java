package xyz.denprog.codefestredopractice.user.ui.reservations;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.RoomReservationDao;

@HiltViewModel
public class UserReservationsViewModel extends ViewModel {

    private final RoomReservationDao roomReservationDao;

    @Inject
    public UserReservationsViewModel(RoomReservationDao roomReservationDao) {
        this.roomReservationDao = roomReservationDao;
    }

    public List<UserReservationItem> getReservationsForUser(long userId) {
        return roomReservationDao.getReservationsForUser(userId);
    }
}
