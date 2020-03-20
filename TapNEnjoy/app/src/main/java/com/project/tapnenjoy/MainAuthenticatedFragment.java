package com.project.tapnenjoy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


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
                    FileInputStream fis = new FileInputStream("/storage/self/primary/Pictures/Jonas.jpg");
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    Product product = new Product(null, "Mouse", 1.00, "Brand new mouse", bytes, 1, 1, true);

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
                //---------------------------




            }
        });

        return view;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static byte[] bitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream output= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
        return output.toByteArray();
    }

}
