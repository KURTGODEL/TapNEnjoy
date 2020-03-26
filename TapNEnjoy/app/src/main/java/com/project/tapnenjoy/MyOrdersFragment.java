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
import com.project.tapnenjoy.Models.Product;
import java.util.ArrayList;

public class MyOrdersFragment extends Fragment{

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        ListView myOrdersListView = view.findViewById(R.id.myOrdersListView);

        DBHelper dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.getProducts(true, "asc", 0);

        ArrayList<Product> products = new ArrayList<>();

        while(cursor.moveToNext()) {
            products.add(new Product(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("price")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getInt(cursor.getColumnIndex("stock")),
                    cursor.getInt(cursor.getColumnIndex("seller_id")),
                    cursor.getInt(cursor.getColumnIndex("status")) > 0));
        }
        cursor.close();

        ProductListAdapter adapter = new ProductListAdapter(view.getContext(), R.layout.row_products_listview, products);

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
