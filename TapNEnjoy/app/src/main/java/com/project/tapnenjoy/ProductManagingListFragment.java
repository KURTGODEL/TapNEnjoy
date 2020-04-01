package com.project.tapnenjoy;


import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.project.tapnenjoy.DBHelper.Constants.Products;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.ProductManagingAdapterToList;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductManagingListFragment extends Fragment {

    MainActivity mainActivity;
    Button addNewProductButton;

    public ProductManagingListFragment() {
        // Required empty public constructor
    }

    public static ProductManagingListFragment newInstance() {
        return new ProductManagingListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_managing_list, container, false);
        mainActivity = ((MainActivity)getActivity());

        mainActivity.closeDrawer();
        mainActivity.setToolbarTitle("Products");
        mainActivity.setUpDrawerButton();
        mainActivity.hideProgressDialog();

        addNewProductButton = view.findViewById(R.id.add_new_product);

        ListView productsListView = view.findViewById(R.id.productListView);

        DBHelper dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.getProducts(false, "", 0);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 4 * 1024 * 1024); //the 4MB is the new size
        } catch (Exception e) {
            Log.e("SYSERROR", e.getMessage());
        }

        ArrayList<ProductManagingAdapterToList> productsAdapterToListArrayList = new ArrayList<>();

        while(cursor.moveToNext()) {
            productsAdapterToListArrayList.add(new ProductManagingAdapterToList(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCT_TITLE)),
                    cursor.getDouble(cursor.getColumnIndex(Products.PRODUCT_PRICE)),
                    cursor.getBlob(cursor.getColumnIndex(Products.PRODUCT_IMAGE))));
        }
        cursor.close();

        ProductManagingListAdapter adapter =
                new ProductManagingListAdapter(view.getContext(), mainActivity, R.layout.row_product_managing_listview, productsAdapterToListArrayList);

        productsListView.setAdapter(adapter);

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.displayFragment(ProductAddFragment.class);
            }
        });

        return view;
    }
}
