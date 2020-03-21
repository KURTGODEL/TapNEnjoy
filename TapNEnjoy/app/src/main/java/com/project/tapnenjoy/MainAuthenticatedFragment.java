package com.project.tapnenjoy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;
import java.io.FileInputStream;
import java.io.IOException;


public class MainAuthenticatedFragment extends AuthenticatedFragment {

    Button btnMyOrders, btnWatchList, btnMyOffers, btnStartShopping;
    MainActivity mainActivity;

    public MainAuthenticatedFragment() {
        // Required empty public constructor
    }

    public static MainAuthenticatedFragment newInstance() {
        return new MainAuthenticatedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_authenticated, container, false);

        mainActivity = ((MainActivity)getActivity());

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        MainActivity activity = ((MainActivity)getActivity());

        // set up action bar for internal screens
        activity.getSupportActionBar().show();
        activity.setToolbarTitle("Dashboard");
        activity.toogleOptionsMenu(true);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_darker_24dp);

        btnMyOrders = view.findViewById(R.id.btnMyOrders);
        btnMyOffers = view.findViewById(R.id.btnMyOffers);
        btnStartShopping = view.findViewById(R.id.btnStartShopping);

        btnMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.displayFragment(MyOrdersFragment.class);
            }
        });

        btnMyOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(view.getContext());

                try {
                    FileInputStream fis = new FileInputStream("/storage/self/primary/Pictures/dante.jpg");
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    Product product = new Product(null,
                            "Dante Button Closure Journal",
                            11.62,
                            "The perfect traveling journal. From personal writing to jotting notes during conference meetings, satisfy all your note-taking needs with this custom journal.",
                            bytes,
                            19,
                            9,
                            true);

                    Boolean isInserted =  db.insertProductData(product);

                    if (isInserted == true){
                        Toast.makeText(view.getContext(), "Data inserted", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(view.getContext(), "Data NOT inserted", Toast.LENGTH_LONG).show();
                    }
                }
                catch (IOException e){
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        btnStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DBHelper db = new DBHelper(view.getContext());

                //Toast.makeText(view.getContext(), db.deleteProduct(0), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
