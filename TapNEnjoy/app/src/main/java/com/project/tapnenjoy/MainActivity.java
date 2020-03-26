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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity{

    private DrawerLayout dl;
    private NavigationView nv;
    private ActionBarDrawerToggle dt;
    private Menu _menu = null;
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private ProgressDialog loadingBar;


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
                case R.id.home_drawer_item:
                    displayFragment(MainAuthenticatedFragment.class);
                    break;
                case R.id.product_drawer_item:
                    displayFragment(ProductAddFragment.class);
                    break;
                case R.id.signup_drawer_item:
                    displayFragment(SignupFragment.class);
                    break;
                case R.id.logout_drawer_item:
                    displayFragment(LoginFragment.class);
                    break;
                default:
                    return true;
            }

            closeDrawer();

            return true;
            }
        });

        if(getIsLogged()){
            displayFragment(MainAuthenticatedFragment.class);
        }else{
            displayFragment(LoginFragment.class);
        }
    }

    public void displayFragment(Class fragmentClass, Bundle... bundleArguments){
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            if(bundleArguments != null && bundleArguments.length > 0){
                for(Bundle bundle : bundleArguments){
                    fragment.setArguments(bundle);
                }
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.nav_host_fragment, fragment).commit();

            dl.closeDrawer(GravityCompat.START);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setAuthenticatedUserId(Integer userId){
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences.edit().putInt("userId", userId).apply();
    }

    public Integer getAuthenticatedUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0);
    }

    public void setIsLogged(Boolean isLogged){
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("logged", isLogged).apply();
    }

    public Boolean getIsLogged(){
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
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
            this._menu.findItem(R.id.home_option_item).setVisible(visible);
            this._menu.findItem(R.id.logout_option_item).setVisible(visible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // update toogleOptionsMenu when changing it
        switch (item.getItemId()) {
            case android.R.id.home: // handle back button
                //onBackPressed();
                closeDrawer();
                if(tellFragments()) {
                    displayFragment(LoginFragment.class); // prevent delay
                }
                break;
            case R.id.home_option_item:
                displayFragment(MainAuthenticatedFragment.class);
                break;
            case R.id.logout_option_item:
                displayFragment(LoginFragment.class);
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

    public void showProgressDialog(String title, String message){
        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle(title);
        loadingBar.setMessage(message);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    public void hideProgressDialog(){
        if(loadingBar != null && loadingBar.isShowing()){
            loadingBar.dismiss();
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
