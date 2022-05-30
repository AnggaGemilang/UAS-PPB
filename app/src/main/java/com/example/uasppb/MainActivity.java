package com.example.uasppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.uasppb.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DashboardFragment dashboardFragment = new DashboardFragment();
        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        SearchFragment searchFragment = new SearchFragment();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Dashboard");

        makeCurrentFragment(dashboardFragment);
        binding.navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.nav_home):
                        makeCurrentFragment(dashboardFragment);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
                        break;
                    case (R.id.nav_search):
                        makeCurrentFragment(searchFragment);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Search");
                        break;
                    case (R.id.nav_bookmark):
                        makeCurrentFragment(bookmarkFragment);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Bookmark");
                        break;
                }
                return false;
            }
        });
    }

    private void makeCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.getRoot().requestFocus();
        makeCurrentFragment(new DashboardFragment());
    }

}