package com.Kelompok1.android.hayunanem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.Kelompok1.android.hayunanem.Fragment.HomeFragment;
import com.Kelompok1.android.hayunanem.Fragment.MarketListFragment;
import com.Kelompok1.android.hayunanem.Fragment.ReminderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;

//                    case R.id.nav_reminder:
//                        fragment = new ReminderFragment();
//                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
//                        break;

                    case R.id.nav_market:
                        fragment = new MarketListFragment();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
    }
}