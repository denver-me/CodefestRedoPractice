package xyz.denprog.codefestredopractice.admin.ui.home;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import xyz.denprog.codefestredopractice.database.RoomReservationDao;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final RoomReservationDao roomReservationDao;

    @Inject
    public HomeViewModel(RoomReservationDao roomReservationDao) {
        this.roomReservationDao = roomReservationDao;
    }

    public List<AdminReservationItem> getAllReservations() {
        return roomReservationDao.getAllReservations();
    }
}
