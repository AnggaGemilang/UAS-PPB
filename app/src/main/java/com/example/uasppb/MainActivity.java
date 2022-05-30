package com.example.uasppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
                        item.setCheckable(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
                        return true;
                    case (R.id.nav_search):
                        makeCurrentFragment(searchFragment);
                        item.setCheckable(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Search");
                        return true;
                    case (R.id.nav_bookmark):
                        makeCurrentFragment(bookmarkFragment);
                        item.setCheckable(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Bookmark");
                        return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.act_filter):
                Toast.makeText(this, "Modal Muncul", Toast.LENGTH_SHORT).show();
                break;
            case  (R.id.act_about):
                new AlertDialog.Builder(this)
                        .setTitle("Versi Aplikasi")
                        .setMessage("Beta 1.0.0")
                        .setCancelable(true)
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}