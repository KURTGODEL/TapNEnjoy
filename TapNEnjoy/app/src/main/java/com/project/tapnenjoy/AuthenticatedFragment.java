package com.project.tapnenjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public abstract class AuthenticatedFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState)
    {
        MainActivity mainActivity = ((MainActivity)getActivity());

        // redirect to login if not logged
        if (!mainActivity.getIsLogged()) {
            mainActivity.displayFragment(LoginFragment.class);
        }

        super.onCreate(savedInstanceState);
    }
}
