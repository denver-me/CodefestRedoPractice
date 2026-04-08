package xyz.denprog.codefestredopractice;

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
import xyz.denprog.codefestredopractice.databinding.FragmentFirstBinding;
import xyz.denprog.codefestredopractice.database.entity.User;
import xyz.denprog.codefestredopractice.viewmodel.RegisterViewModel;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.loginHere.setOnClickListener(v ->
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
        getActivity().setActionBar(null);
        binding.registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.firstName.getText().toString().trim();
                String middleName = binding.middleName.getText().toString().trim();
                String lastName = binding.lastName.getText().toString().trim();
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();

                if (TextUtils.isEmpty(firstName) ||
                        TextUtils.isEmpty(lastName) ||
                        TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password)) {
                    Toast.makeText(requireContext(), "Fill in all required fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(requireContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.id = System.currentTimeMillis();
                user.firstName = firstName;
                user.middleName = middleName;
                user.lastName = lastName;
                user.email = email;
                user.password = password;
                user.isAdmin = false;

                registerViewModel.registerUser(user);
                Toast.makeText(requireContext(), "Registration submitted.", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
