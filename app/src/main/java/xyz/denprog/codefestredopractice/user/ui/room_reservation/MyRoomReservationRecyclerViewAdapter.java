package xyz.denprog.codefestredopractice.user.ui.room_reservation;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.database.entity.Room;
import xyz.denprog.codefestredopractice.databinding.FragmentRoomReservationBinding;

public class MyRoomReservationRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomReservationRecyclerViewAdapter.ViewHolder> {

    public interface RoomSelectListener {
        void onSelect(Room room);
    }

    private final List<Room> rooms;
    private final RoomSelectListener roomSelectListener;

    public MyRoomReservationRecyclerViewAdapter(List<Room> rooms, RoomSelectListener roomSelectListener) {
        this.rooms = rooms;
        this.roomSelectListener = roomSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentRoomReservationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.room = room;
        holder.idView.setText(String.valueOf(room.roomId));
        holder.contentView.setText(
                room.roomName + "\nPrice: " + String.format(Locale.US, "%.2f", room.roomPrice)
        );
        holder.reserveButton.setText(holder.reserveButton.getContext().getString(R.string.view_room_details));
        holder.reserveButton.setOnClickListener(v -> roomSelectListener.onSelect(room));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final android.widget.TextView idView;
        public final android.widget.TextView contentView;
        public final android.widget.Button reserveButton;
        public Room room;

        public ViewHolder(FragmentRoomReservationBinding binding) {
            super(binding.getRoot());
            idView = binding.itemNumber;
            contentView = binding.content;
            reserveButton = binding.reserveButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
