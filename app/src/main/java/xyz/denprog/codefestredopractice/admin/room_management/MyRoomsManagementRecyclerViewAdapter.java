package xyz.denprog.codefestredopractice.admin.room_management;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.databinding.FragmentItemBinding;

public class MyRoomsManagementRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomsManagementRecyclerViewAdapter.ViewHolder> {

    public interface RoomActionListener {
        void onEdit(Room room);
        void onDelete(Room room, int position);
    }

    private final List<Room> rooms;
    private final RoomActionListener roomActionListener;

    public MyRoomsManagementRecyclerViewAdapter(List<Room> rooms, RoomActionListener roomActionListener) {
        this.rooms = rooms;
        this.roomActionListener = roomActionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.room = room;
        holder.idView.setText(String.valueOf(room.roomId));
        holder.contentView.setText(room.roomName + "\nPrice: " + String.format(Locale.US, "%.2f", room.roomPrice));
        holder.editButton.setOnClickListener(v -> roomActionListener.onEdit(room));
        holder.deleteButton.setOnClickListener(v -> roomActionListener.onDelete(room, holder.getBindingAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final android.widget.TextView idView;
        public final android.widget.TextView contentView;
        public final android.widget.Button editButton;
        public final android.widget.Button deleteButton;
        public Room room;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            idView = binding.itemNumber;
            contentView = binding.content;
            editButton = binding.editButton;
            deleteButton = binding.deleteButton;
        }
    }
}
