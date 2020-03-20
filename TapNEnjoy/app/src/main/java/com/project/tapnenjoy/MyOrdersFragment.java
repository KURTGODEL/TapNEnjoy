package com.project.tapnenjoy;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tapnenjoy.DBHelper.Constants.Products;
import com.project.tapnenjoy.DBHelper.DBHelper;

public class MyOrdersFragment extends Fragment{

    private Products products;
    private SimpleCursorAdapter dataAdapter;

    String product[] = {"Product1", "Product2", "Product3"};
    String price[] = {"$10.00", "$20.00", "$30.00"};
    int images[] = {R.drawable.ic_arrow_back_darker_24dp, R.drawable.ic_launcher_background, R.drawable.ic_launcher_app_icon};

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        ListView myOrdersListView = view.findViewById(R.id.myOrdersListView);

        DBHelper dbHelper = new DBHelper(getContext());

        Cursor cursor = dbHelper.getProducts(true, "asc", 0);

        String[] columns = new String[] {
                products.PRODUCT_IMAGE,
                products.PRODUCT_TITLE,
                products.PRODUCT_PRICE
        };

        int[] to = new int[] {
                R.id.imageProduct,
                R.id.txtProduct,
                R.id.txtPrice
        };

        dataAdapter = new SimpleCursorAdapter(
                getContext(),
                R.layout.row_myorders_listview,
                cursor,
                columns,
                to,
                0);

        myOrdersListView.setAdapter(dataAdapter);

        myOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Item " + i + l +" clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rProduct[];
        String rPrice[];
        int rImage[];


        MyAdapter (Context c, String product[], String price[], int images[]){
            super(c, R.layout.row_myorders_listview, R.id.txtProduct, product);
            this.context = c;
            this.rProduct = product;
            this.rPrice = price;
            this.rImage = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)MyOrdersFragment.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.row_myorders_listview, parent, false);

            ImageView images = row.findViewById(R.id.imageProduct);
            TextView product = row.findViewById(R.id.txtProduct);
            TextView price = row.findViewById(R.id.txtPrice);

            images.setImageResource(rImage[position]);
            product.setText(rProduct[position]);
            price.setText(rPrice[position]);

            return row;
        }
    }

}
