package xyz.denprog.codefestredopractice.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;
import xyz.denprog.codefestredopractice.GlobalUserViewModel;
import xyz.denprog.codefestredopractice.MainActivity;
import xyz.denprog.codefestredopractice.R;
import xyz.denprog.codefestredopractice.databinding.ActivityHomeBinding;
@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private GlobalUserViewModel globalUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        globalUserViewModel = new ViewModelProvider(this).get(GlobalUserViewModel.class);
        globalUserViewModel.restoreCurrentUser(getIntent());
        setupNavigationHeader(navigationView);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_users, R.id.nav_rooms)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        setupDrawerNavigation(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupNavigationHeader(NavigationView navigationView) {
        TextView userNameView = navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_name);
        TextView userMetaView = navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_meta);

        globalUserViewModel.getCurrentUser().observe(this, sessionUser -> {
            if (sessionUser == null) {
                userNameView.setText(R.string.default_nav_user_name);
                userMetaView.setText(R.string.default_admin_nav_meta);
                return;
            }

            userNameView.setText(sessionUser.displayName);
            userMetaView.setText(sessionUser.email);
        });
    }

    private void setupDrawerNavigation(NavigationView navigationView, NavController navController) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_logout) {
                logout();
                return true;
            }

            boolean handled = NavigationUI.onNavDestinationSelected(menuItem, navController);
            if (handled) {
                menuItem.setChecked(true);
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return handled;
        });
    }

    private void logout() {
        globalUserViewModel.clearCurrentUser();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
