package xyz.denprog.codefestredopractice.admin.room_management;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.databinding.FragmentItemListBinding;

@AndroidEntryPoint
public class RoomsManagementFragment extends Fragment {

    private FragmentItemListBinding binding;
    private RoomManagementViewModel roomManagementViewModel;
    private final List<Room> rooms = new ArrayList<>();
    private MyRoomsManagementRecyclerViewAdapter adapter;
    private Room editingRoom;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomManagementViewModel = new ViewModelProvider(this).get(RoomManagementViewModel.class);

        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyRoomsManagementRecyclerViewAdapter(rooms, new MyRoomsManagementRecyclerViewAdapter.RoomActionListener() {
            @Override
            public void onEdit(Room room) {
                editingRoom = room;
                binding.roomNameInput.setText(room.roomName);
                binding.roomPriceInput.setText(String.format(Locale.US, "%.2f", room.roomPrice));
                binding.saveRoomButton.setText("Update Room");
                binding.cancelEditButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDelete(Room room, int position) {
                if (position < 0) {
                    return;
                }
                roomManagementViewModel.deleteRoom(room);
                rooms.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(requireContext(), "Room deleted.", Toast.LENGTH_SHORT).show();
                if (editingRoom != null && editingRoom.roomId == room.roomId) {
                    resetForm();
                }
            }
        });
        binding.list.setAdapter(adapter);

        binding.saveRoomButton.setOnClickListener(v -> saveRoom());
        binding.cancelEditButton.setOnClickListener(v -> resetForm());

        loadRooms();
    }

    private void loadRooms() {
        rooms.clear();
        rooms.addAll(roomManagementViewModel.getAllRooms());
        adapter.notifyDataSetChanged();
    }

    private void saveRoom() {
        String roomName = binding.roomNameInput.getText().toString().trim();
        String roomPriceText = binding.roomPriceInput.getText().toString().trim();

        if (TextUtils.isEmpty(roomName) || TextUtils.isEmpty(roomPriceText)) {
            Toast.makeText(requireContext(), "Enter room name and price.", Toast.LENGTH_SHORT).show();
            return;
        }

        float roomPrice;
        try {
            roomPrice = Float.parseFloat(roomPriceText);
        } catch (NumberFormatException exception) {
            Toast.makeText(requireContext(), "Enter a valid room price.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editingRoom == null) {
            Room room = new Room();
            room.roomName = roomName;
            room.roomPrice = roomPrice;
            room.roomId = roomManagementViewModel.insertRoom(room);
            rooms.add(room);
            adapter.notifyItemInserted(rooms.size() - 1);
            Toast.makeText(requireContext(), "Room added.", Toast.LENGTH_SHORT).show();
        } else {
            editingRoom.roomName = roomName;
            editingRoom.roomPrice = roomPrice;
            roomManagementViewModel.updateRoom(editingRoom);
            int position = findRoomPosition(editingRoom.roomId);
            if (position >= 0) {
                adapter.notifyItemChanged(position);
            }
            Toast.makeText(requireContext(), "Room updated.", Toast.LENGTH_SHORT).show();
        }

        resetForm();
    }

    private int findRoomPosition(long roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).roomId == roomId) {
                return i;
            }
        }
        return -1;
    }

    private void resetForm() {
        editingRoom = null;
        binding.roomNameInput.setText("");
        binding.roomPriceInput.setText("");
        binding.saveRoomButton.setText("Add Room");
        binding.cancelEditButton.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
