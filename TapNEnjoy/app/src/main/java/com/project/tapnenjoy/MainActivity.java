package com.project.tapnenjoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private NavigationView nv;
    private ActionBarDrawerToggle dt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.drawer_layout);
        dt = new ActionBarDrawerToggle(this,
                dl,
                R.string.Open,
                R.string.Close);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up header ActionBar
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_view_headline_black_24dp);
        actionBar.setTitle(R.string.app_name);

        // set up drawer elements
        nv = findViewById(R.id.navigation_view);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.signup_drawer_item:
                        displayFragment(SignupFragment.class);
                        Toast.makeText(MainActivity.this, "SignUp",Toast.LENGTH_SHORT).show();break;
                    case R.id.login_drawer_item:
                        displayFragment(LoginFragment.class);
                        Toast.makeText(MainActivity.this, "LogIn",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }


                return true;

            }
        });

        displayFragment(HomeFragment.class);
    }

    private void displayFragment(Class fragmentClass){
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment).commit();

            dl.closeDrawer(GravityCompat.START);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        try {
            if (item.getTitle().equals("LogIn")) {
                displayFragment(LoginFragment.class);
            } else if (item.getTitle().equals("SignUp")) {
                displayFragment(SignupFragment.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (dt.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
