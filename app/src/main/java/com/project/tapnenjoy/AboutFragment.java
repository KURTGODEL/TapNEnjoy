package com.project.tapnenjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    Button logoutBtn;
    MainActivity mainActivity;
    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_details, container, false);

        mainActivity = ((MainActivity) getActivity());
        mainActivity.setToolbarTitle("Product Detail");
        mainActivity.setUpDrawerButton();
        mainActivity.hideProgressDialog();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.displayFragment(LoginFragment.class);
                mainActivity.finish();
            }
        });

        return view;
    }
}
