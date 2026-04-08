package xyz.denprog.codefestredopractice.admin.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.databinding.ItemAdminReservationBinding;

public class AdminReservationsAdapter extends RecyclerView.Adapter<AdminReservationsAdapter.ViewHolder> {

    private final List<AdminReservationItem> reservations;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

    public AdminReservationsAdapter(List<AdminReservationItem> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAdminReservationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdminReservationItem item = reservations.get(position);
        String fullName = ((item.firstName == null ? "" : item.firstName) + " " +
                (item.lastName == null ? "" : item.lastName)).trim();
        if (fullName.isEmpty()) {
            fullName = item.email;
        }

        holder.binding.userValue.setText(
                holder.binding.getRoot().getContext().getString(R.string.admin_reservation_user_label, fullName)
        );
        holder.binding.roomValue.setText(
                holder.binding.getRoot().getContext().getString(R.string.admin_reservation_room_label, item.roomName)
        );
        holder.binding.dateValue.setText(
                holder.binding.getRoot().getContext().getString(
                        R.string.admin_reservation_date_label,
                        dateFormat.format(new Date(item.reservationDate))
                )
        );
        holder.binding.emailValue.setText(item.email);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemAdminReservationBinding binding;

        ViewHolder(ItemAdminReservationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
