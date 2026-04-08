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

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.databinding.FragmentRoomDetailBinding;

@AndroidEntryPoint
public class RoomDetailFragment extends Fragment {

    public static final String ARG_ROOM_ID = "room_id";

    private FragmentRoomDetailBinding binding;
    private RoomReservationViewModel roomReservationViewModel;
    private Room room;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomReservationViewModel = new ViewModelProvider(this).get(RoomReservationViewModel.class);

        long roomId = requireArguments().getLong(ARG_ROOM_ID, -1L);
        room = roomReservationViewModel.getRoomById(roomId);
        if (room == null) {
            Toast.makeText(requireContext(), R.string.room_not_found, Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigateUp();
            return;
        }

        bindRoom(room);
        binding.reserveRoomButton.setOnClickListener(v -> openReservationDate(room.roomId));
    }

    private void bindRoom(@NonNull Room room) {
        binding.roomIdValue.setText(getString(R.string.room_id_label, room.roomId));
        binding.roomNameValue.setText(room.roomName);
        binding.roomPriceValue.setText(String.format(Locale.US, getString(R.string.room_price_label), room.roomPrice));
    }

    private void openReservationDate(long roomId) {
        Bundle args = new Bundle();
        args.putLong(RoomReservationDateFragment.ARG_ROOM_ID, roomId);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_roomDetailFragment_to_roomReservationDateFragment, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
