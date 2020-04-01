package com.project.tapnenjoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.DBHelper.DBHelper;

public class ShoppingResultFragment extends Fragment {

    Button ShopButton, NewSearchButton;
    TextView txtShippingInformation;
    MainActivity mainActivity;
    private DBHelper db;

    public ShoppingResultFragment() {
        // Required empty public constructor
    }

    public static ShoppingResultFragment newInstance() {
        return new ShoppingResultFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_result, container, false);
        mainActivity = ((MainActivity) getActivity());
        mainActivity.setToolbarTitle("Product Detail");
        mainActivity.setUpDrawerButton();
        mainActivity.hideProgressDialog();

        db = new DBHelper(getContext());


        txtShippingInformation = view.findViewById(R.id.shipping_information);
        ShopButton = view.findViewById(R.id.continue_shopping_btn);
        NewSearchButton = view.findViewById(R.id.new_search_btn);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderProduct",
                Context.MODE_PRIVATE);
        int orderId =sharedPreferences.getInt("userOrderId",0);

        txtShippingInformation.setText("Your order ID: "+ orderId);

        ShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.displayFragment( WatchListFragment.class);
            }
        });

        NewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.displayFragment( MainAuthenticatedFragment.class);
            }
        });

        //txtShippingInformation
        return view;
    }
}
