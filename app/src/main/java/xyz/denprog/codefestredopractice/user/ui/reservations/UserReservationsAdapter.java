package xyz.denprog.codefestredopractice.user.ui.reservations;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.databinding.ItemUserReservationBinding;

public class UserReservationsAdapter extends RecyclerView.Adapter<UserReservationsAdapter.ViewHolder> {

    private final List<UserReservationItem> reservations;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    public UserReservationsAdapter(List<UserReservationItem> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUserReservationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserReservationItem item = reservations.get(position);
        holder.binding.roomNameValue.setText(
                holder.binding.getRoot().getContext().getString(R.string.reservation_room_label, item.roomName)
        );
        holder.binding.reservationDateValue.setText(
                holder.binding.getRoot().getContext().getString(
                        R.string.reservation_date_label,
                        dateFormat.format(new Date(item.reservationDate))
                )
        );
        holder.binding.roomPriceValue.setText(
                String.format(Locale.US, holder.binding.getRoot().getContext().getString(R.string.reservation_price_label), item.roomPrice)
        );
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemUserReservationBinding binding;

        ViewHolder(ItemUserReservationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
