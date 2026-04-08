package xyz.denprog.codefestredopractice.user.ui.room_reservation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.database.entity.Room;

@AndroidEntryPoint
public class RoomReservationFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private final List<Room> rooms = new ArrayList<>();
    private MyRoomReservationRecyclerViewAdapter adapter;
    private RoomReservationViewModel roomReservationViewModel;

    public RoomReservationFragment() {
    }

    @SuppressWarnings("unused")
    public static RoomReservationFragment newInstance(int columnCount) {
        RoomReservationFragment fragment = new RoomReservationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        roomReservationViewModel = new ViewModelProvider(this).get(RoomReservationViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            loadRooms();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_reservation_list, container, false);

        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            adapter = new MyRoomReservationRecyclerViewAdapter(rooms, this::openRoomDetails);
            recyclerView.setAdapter(adapter);
            loadRooms();
        }
        return view;
    }

    private void loadRooms() {
        rooms.clear();
        rooms.addAll(roomReservationViewModel.getAllRooms());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (rooms.isEmpty()) {
            Toast.makeText(requireContext(), R.string.no_rooms_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void openRoomDetails(@NonNull Room room) {
        Bundle args = new Bundle();
        args.putLong(RoomDetailFragment.ARG_ROOM_ID, room.roomId);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_gallery_to_roomDetailFragment, args);
    }
}
