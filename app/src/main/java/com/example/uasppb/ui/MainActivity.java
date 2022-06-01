package com.example.uasppb.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.example.uasppb.R;
import com.example.uasppb.databinding.ActivityMainBinding;
import com.example.uasppb.ui.Bookmark.BookmarkFragment;
import com.example.uasppb.ui.Dashboard.DashboardFragment;
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
                    case (R.id.nav_bookmark):
                        makeCurrentFragment(bookmarkFragment);
                        item.setCheckable(true);
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Bookmarks");
                        return true;
                }
                return false;
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                Spinner spinner = mView.findViewById(R.id.region);
                mBuilder.setTitle("Set Region");
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences settings = getSharedPreferences("NEWS_SETTINGS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("region", spinner.getSelectedItem().toString());
                        editor.apply();
                        makeCurrentFragment(new DashboardFragment());
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                mBuilder.show();
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