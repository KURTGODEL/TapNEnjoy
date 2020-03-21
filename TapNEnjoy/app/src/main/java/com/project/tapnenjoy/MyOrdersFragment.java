package com.project.tapnenjoy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.project.tapnenjoy.DBHelper.Constants.Products;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyOrdersFragment extends Fragment{

    private Products products;

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

        ProductListAdapter adapter = new ProductListAdapter(view.getContext(), R.layout.row_myorders_listview, products);

        myOrdersListView.setAdapter(adapter);

        myOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class ProductListAdapter extends ArrayAdapter<Product>{

        Context context;
        ArrayList<Product> products;
        Integer resource;

        ProductListAdapter(Context c, Integer resource, ArrayList<Product> product){
            super(c, resource, product);
            this.products = product;
            this.context = c;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater)MyOrdersFragment.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_myorders_listview,null,true);
            }

            Product product = getItem(position);

            ImageView image = convertView.findViewById(R.id.imageProduct);
            TextView tvProduct = convertView.findViewById(R.id.txtProduct);
            TextView tvPrice = convertView.findViewById(R.id.txtPrice);
            TextView tvDescription = convertView.findViewById(R.id.txtDescription);

            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
            String currency = format.format(product.getPrice());


            image.setImageBitmap(bitmap);
            tvProduct.setText(product.getTitle());
            tvPrice.setText(currency);
            tvDescription.setText(product.getDescription());

            return convertView;
        }
    }

}
