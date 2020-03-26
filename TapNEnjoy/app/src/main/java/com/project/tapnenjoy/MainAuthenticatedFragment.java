package com.project.tapnenjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.DBHelper.Constants.UserWatchs;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;
import java.io.FileInputStream;
import java.io.IOException;


public class MainAuthenticatedFragment extends AuthenticatedFragment {

    Button btnMyOrders, btnWatchList, btnMyOffers, btnStartShopping, btnSearh;
    MainActivity mainActivity;
    TextInputLayout txtMainSearch;

    private DBHelper db = new DBHelper(getContext());

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
        btnSearh = view.findViewById(R.id.btnSearch);
        btnWatchList = view.findViewById(R.id.btnWatchList);
        txtMainSearch = view.findViewById(R.id.txtMainSearch);

        btnSearh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String keySearch = txtMainSearch.getEditText().getText().toString();

                ProductSearchResultFragment newFragment = new ProductSearchResultFragment();

                Bundle args = new Bundle();
                args.putString(ProductSearchResultFragment.key, keySearch);

                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        btnMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.displayFragment(MyOrdersFragment.class);
            }
        });

        btnWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.displayFragment(WatchListFragment.class);
            }
        });

        btnMyOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                //Toast.makeText(view.getContext(), db.deleteProduct(2), Toast.LENGTH_SHORT).show();
                //NewUsers();
            }
        });

        return view;
    }
}
