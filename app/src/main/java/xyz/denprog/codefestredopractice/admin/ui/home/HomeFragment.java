package xyz.denprog.codefestredopractice.admin.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.databinding.FragmentAdminReservationsBinding;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentAdminReservationsBinding binding;
    private HomeViewModel homeViewModel;
    private final List<AdminReservationItem> reservations = new ArrayList<>();
    private AdminReservationsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentAdminReservationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.reservationsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AdminReservationsAdapter(reservations);
        binding.reservationsList.setAdapter(adapter);
        loadReservations();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) {
            loadReservations();
        }
    }

    private void loadReservations() {
        reservations.clear();
        reservations.addAll(homeViewModel.getAllReservations());
        adapter.notifyDataSetChanged();

        boolean hasReservations = !reservations.isEmpty();
        binding.reservationsList.setVisibility(hasReservations ? View.VISIBLE : View.GONE);
        binding.emptyState.setVisibility(hasReservations ? View.GONE : View.VISIBLE);
        if (!hasReservations) {
            binding.emptyState.setText(R.string.admin_no_reservations);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
