package xyz.denprog.codefestredopractice.user.ui.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.GlobalUserViewModel;
import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.session.SessionUser;
import xyz.denprog.codefestredopractice.databinding.FragmentUserReservationsBinding;

@AndroidEntryPoint
public class UserReservationsFragment extends Fragment {

    private FragmentUserReservationsBinding binding;
    private UserReservationsViewModel userReservationsViewModel;
    private GlobalUserViewModel globalUserViewModel;
    private final List<UserReservationItem> reservations = new ArrayList<>();
    private UserReservationsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserReservationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userReservationsViewModel = new ViewModelProvider(this).get(UserReservationsViewModel.class);
        globalUserViewModel = new ViewModelProvider(requireActivity()).get(GlobalUserViewModel.class);

        binding.reservationsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new UserReservationsAdapter(reservations);
        binding.reservationsList.setAdapter(adapter);

        loadReservations();
    }

    private void loadReservations() {
        SessionUser currentUser = globalUserViewModel.getCurrentUserValue();
        if (currentUser == null) {
            binding.emptyState.setText(R.string.user_session_missing);
            binding.emptyState.setVisibility(View.VISIBLE);
            binding.reservationsList.setVisibility(View.GONE);
            return;
        }

        reservations.clear();
        reservations.addAll(userReservationsViewModel.getReservationsForUser(currentUser.id));
        adapter.notifyDataSetChanged();

        boolean hasReservations = !reservations.isEmpty();
        binding.reservationsList.setVisibility(hasReservations ? View.VISIBLE : View.GONE);
        binding.emptyState.setVisibility(hasReservations ? View.GONE : View.VISIBLE);
        if (!hasReservations) {
            binding.emptyState.setText(R.string.no_reservations_found);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
