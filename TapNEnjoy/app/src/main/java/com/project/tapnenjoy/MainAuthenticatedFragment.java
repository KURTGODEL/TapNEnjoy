package com.project.tapnenjoy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MainAuthenticatedFragment extends AuthenticatedFragment {
    public MainAuthenticatedFragment() {
        // Required empty public constructor
    }

    public static MainAuthenticatedFragment newInstance() {
        return new MainAuthenticatedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_authenticated, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        MainActivity activity = ((MainActivity)getActivity());

        // set up action bar for internal screens
        activity.getSupportActionBar().show();
        activity.setToolbarTitle("Dashboard");
        activity.toogleOptionsMenu(true);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_darker_24dp);

        return view;
    }

}