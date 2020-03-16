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

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private NavigationView nv;
    private ActionBarDrawerToggle dt;
    private Menu _menu = null;
    private Toolbar toolbar;
    private TextView toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up drawer
        dl = findViewById(R.id.drawer_layout);
        dt = new ActionBarDrawerToggle(this,
                dl,
                R.string.Open,
                R.string.Close);
        dl.addDrawerListener(dt);
        dt.syncState();


        toolBarTitle = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // set up header ActionBar
        ActionBar actionBar = getSupportActionBar();

        // hide default ToolBar title
        actionBar.setDisplayShowTitleEnabled(false);

        // show back button
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_darker_24dp);


        // set up drawer elements and handler
        nv = findViewById(R.id.navigation_view);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            switch(id) {
                case R.id.signup_drawer_item:
                    displayFragment(SignupFragment.class);
                    break;
                case R.id.login_drawer_item:
                    displayFragment(LoginFragment.class);
                    break;
                default:
                    return true;
            }

            closeDrawer();

            return true;
            }
        });

        displayFragment(LoginFragment.class);
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
        this._menu = menu;
        return true;
    }

    public void toogleOptionsMenu(Boolean visible){
        if(this._menu != null) {
            this._menu.findItem(R.id.login_option_item).setVisible(visible);
            this._menu.findItem(R.id.signup_option_item).setVisible(visible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // handle back button
                //onBackPressed();
                closeDrawer();
                if(tellFragments()) {
                    displayFragment(LoginFragment.class); // prevent delay
                }
                break;
            case R.id.login_option_item:
                displayFragment(LoginFragment.class);
                break;
            case R.id.signup_option_item:
                displayFragment(SignupFragment.class);
                break;
        }

        if (dt.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public void onBackPressed() {
        if(tellFragments()){
            if (dl.isDrawerOpen(GravityCompat.START)) {
                closeDrawer();
            }
        }
    }
*/
    private boolean tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for(Fragment f : fragments){
            if(f != null && f instanceof SignupFragment) {
                ((SignupFragment) f).onBackPressed();
                return true;
            }
        }

        return false;
    }

    // call it from any fragment
    public void closeDrawer()
    {
        dl.closeDrawer(GravityCompat.START);
    }

    public void setToolbarTitle(String title){
        if(toolBarTitle != null){
            toolBarTitle.setText(title);
        }
    }

    public void closeSoftKeyBoard(){
        View view = this.getCurrentFocus();

        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
