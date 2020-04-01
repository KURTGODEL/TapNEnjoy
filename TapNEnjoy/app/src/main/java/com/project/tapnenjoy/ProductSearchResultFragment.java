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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSearchResultFragment extends Fragment {

    final static String key = "keyWord";
    String keyWord = "";

    public ProductSearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_search_result, container, false);

        Bundle args = getArguments();

        if (args != null) {
            keyWord = args.getString(key);
        }

        ListView myOrdersListView = view.findViewById(R.id.productSearchListView);

        DBHelper dbHelper = new DBHelper(view.getContext());

        Cursor cursor = dbHelper.getProductsByTitle(true, "asc", 0, keyWord);

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
