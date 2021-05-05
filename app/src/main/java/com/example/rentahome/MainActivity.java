package com.example.rentahome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.rentahome.fragments.ComposeFragment;
import com.example.rentahome.fragments.Homefragment;
import com.example.rentahome.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContainer,new Homefragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                Fragment fragment = new Fragment();
                switch (menuitem.getItemId()){
                    case R.id.action_home: fragment = new Homefragment(); break;
                    case R.id.action_compose:fragment= new ComposeFragment(); break;
                    case R.id.action_profile: fragment = new ProfileFragment(); break;
                    default: fragment = new Homefragment();break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragment ).commit();

                return true;
            }
        });
        //bottomNavigationView.setSelectedItemId(R.id.action_home); //default; loads in home_activity
    }
}