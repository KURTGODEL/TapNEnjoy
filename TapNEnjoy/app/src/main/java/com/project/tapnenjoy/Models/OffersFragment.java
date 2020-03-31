package com.project.tapnenjoy.Models;


import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.R;
import com.project.tapnenjoy.WatchListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends Fragment {


    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        ListView myOrdersListView = view.findViewById(R.id.myOffersListView);

        DBHelper dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.getUserOffers(1, 0);

        ArrayList<UserOffersAdapterToList> userOffersAdapterToListArray = new ArrayList<>();

        while(cursor.moveToNext()) {
            userOffersAdapterToListArray.add(new UserOffersAdapterToList(
                    cursor.getInt(cursor.getColumnIndex("user_offers_id")),
                    cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getInt(cursor.getColumnIndex("seller_id")),
                    cursor.getInt(cursor.getColumnIndex("product_id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("price")),
                    cursor.getInt(cursor.getColumnIndex("stock")),
                    cursor.getBlob(cursor.getColumnIndex("image"))));
        }
        cursor.close();

        OfferListAdapter adapter = new OfferListAdapter(view.getContext(), R.layout.row_products_listview, userOffersAdapterToListArray);

        myOrdersListView.setAdapter(adapter);

        myOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
