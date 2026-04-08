package xyz.denprog.codefestredopractice.user.ui.room_reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.GlobalUserViewModel;
import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.session.SessionUser;
import xyz.denprog.codefestredopractice.databinding.FragmentRoomReservationDateBinding;

@AndroidEntryPoint
public class RoomReservationDateFragment extends Fragment {

    public static final String ARG_ROOM_ID = "room_id";

    private FragmentRoomReservationDateBinding binding;
    private RoomReservationViewModel roomReservationViewModel;
    private GlobalUserViewModel globalUserViewModel;
    private Room room;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomReservationDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomReservationViewModel = new ViewModelProvider(this).get(RoomReservationViewModel.class);
        globalUserViewModel = new ViewModelProvider(requireActivity()).get(GlobalUserViewModel.class);

        long roomId = requireArguments().getLong(ARG_ROOM_ID, -1L);
        room = roomReservationViewModel.getRoomById(roomId);
        if (room == null) {
            Toast.makeText(requireContext(), R.string.room_not_found, Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigateUp();
            return;
        }

        binding.roomNameValue.setText(room.roomName);
        binding.reservationDatePicker.setMinDate(System.currentTimeMillis());
        updateSelectedDateText();

        binding.reservationDatePicker.setOnDateChangedListener((view1, year, monthOfYear, dayOfMonth) ->
                updateSelectedDateText()
        );
        binding.confirmReservationButton.setOnClickListener(v -> submitReservation());
    }

    private void updateSelectedDateText() {
        Calendar calendar = selectedDateCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        binding.selectedDateValue.setText(
                getString(R.string.selected_reservation_date, dateFormat.format(calendar.getTime()))
        );
    }

    private void submitReservation() {
        SessionUser currentUser = globalUserViewModel.getCurrentUserValue();
        if (currentUser == null) {
            Toast.makeText(requireContext(), R.string.user_session_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = selectedDateCalendar();
        long reservationId = roomReservationViewModel.reserveRoom(
                currentUser.id,
                room.roomId,
                calendar.getTimeInMillis()
        );
        if (reservationId <= 0L) {
            Toast.makeText(requireContext(), R.string.room_reservation_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(
                requireContext(),
                getString(R.string.room_reserved_message, room.roomName),
                Toast.LENGTH_SHORT
        ).show();
        NavHostFragment.findNavController(this).popBackStack(R.id.nav_gallery, false);
    }

    private Calendar selectedDateCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, binding.reservationDatePicker.getYear());
        calendar.set(Calendar.MONTH, binding.reservationDatePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, binding.reservationDatePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
