package com.project.tapnenjoy;

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
import com.project.tapnenjoy.Models.UserOrdersAdapterToList;

import java.util.ArrayList;

public class OrdersFragment extends Fragment{

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        ListView myOrdersListView = view.findViewById(R.id.myOrdersListView);

        DBHelper dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.getUserOrders(1, 0);

        ArrayList<UserOrdersAdapterToList> userOrdersAdapterToList = new ArrayList<>();

        while(cursor.moveToNext()) {
            userOrdersAdapterToList.add(new UserOrdersAdapterToList(
                    cursor.getInt(cursor.getColumnIndex("user_orders_id")),
                    cursor.getInt(cursor.getColumnIndex("user_id")),
                    cursor.getInt(cursor.getColumnIndex("product_id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("price")),
                    cursor.getBlob(cursor.getColumnIndex("image"))));
        }
        cursor.close();

        OrdersListAdapter adapter = new OrdersListAdapter(view.getContext(), R.layout.row_products_listview, userOrdersAdapterToList);

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
