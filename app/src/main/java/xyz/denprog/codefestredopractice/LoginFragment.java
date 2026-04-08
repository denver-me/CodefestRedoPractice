package xyz.denprog.codefestredopractice;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.admin.HomeActivity;
import xyz.denprog.codefestredopractice.databinding.FragmentSecondBinding;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.user.UserActivity;
import xyz.denprog.codefestredopractice.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private FragmentSecondBinding binding;
    private LoginViewModel loginViewModel;
    private GlobalUserViewModel globalUserViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        globalUserViewModel = new ViewModelProvider(requireActivity()).get(GlobalUserViewModel.class);
        getActivity().setActionBar(null);
        binding.loginAction.setOnClickListener(view1 -> {
            String email = binding.emailLogin.getText().toString().trim();
            String password = binding.passwordLogin.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Enter email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = loginViewModel.login(email, password);
            if (user == null) {
                Toast.makeText(requireContext(), "Invalid login credentials.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(
                    requireContext(),
                    user.isAdmin ? HomeActivity.class : UserActivity.class
            );
            globalUserViewModel.setCurrentUser(user);
            globalUserViewModel.appendCurrentUser(intent, user);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.registerHere.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
