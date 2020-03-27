package com.project.tapnenjoy;

import androidx.activity.OnBackPressedCallback;
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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
    protected void onCreate(final Bundle savedInstanceState) {
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
                    displayFragment(ProductManagingListFragment.class);
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

        // handle OS back button pressed
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder
                    .setCancelable(true)
                    .setTitle(R.string.lblAlertTitle)
                    .setMessage("Are you sure you want to close this application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null);

                AlertDialog alert = builder.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button yesButton =
                                ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(20,0,0,0);

                        yesButton.setLayoutParams(params);
                    }
                });

                alert.show();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        // start application
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
        // update toggleOptionsMenu when changing it
        switch (item.getItemId()) {
            case android.R.id.home: // handle back button
                closeDrawer();

                Fragment fragment = tellFragments();

                if(fragment != null) {
                    displayFragment(fragment.getClass()); // prevent delay
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

    private Fragment tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        // must add every Fragment which implements BackButton
        for(Fragment f : fragments){
            if(f != null && f instanceof SignupFragment) {
                ((SignupFragment) f).onBackPressed();
                return LoginFragment.newInstance();
            }

            if(f != null && f instanceof ProductAddFragment) {
                ((ProductAddFragment) f).onBackPressed();
                return ProductManagingListFragment.newInstance();
            }
        }

        return null;
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

    public ProgressDialog showProgressDialog(String title, String message){
        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle(title);
        loadingBar.setMessage(message);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        return loadingBar;
    }

    public void hideProgressDialog(){
        if(loadingBar != null && loadingBar.isShowing()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingBar.dismiss();
                }
            }, 1500);
        }
    }

    public void setUpBackButton(){
        toogleOptionsMenu(false);

        // show action bar once again e override back button
        ActionBar actionBar = getSupportActionBar();

        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_darker_24dp);
    }

    public void setUpDrawerButton(){
        toogleOptionsMenu(true);

        // show action bar once again e override back button
        ActionBar actionBar = getSupportActionBar();

        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_darker_24dp);
    }
}
