package com.project.tapnenjoy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.project.tapnenjoy.Models.Product;

import java.util.ArrayList;

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

        //dataAdapter = new SimpleCursorAdapter( getContext(), R.layout.row_myorders_listview, cursor, columns, to, 0);

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

        MyAdapter adapter = new MyAdapter(view.getContext(), R.layout.row_myorders_listview, products);

        myOrdersListView.setAdapter(adapter);

        myOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class MyAdapter extends ArrayAdapter<Product>{

        Context context;
        ArrayList<Product> products;
        Integer resource;

        MyAdapter (Context c, Integer resource, ArrayList<Product> product){
            super(c, resource, product);
            this.products = product;
            this.context = c;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView==null){
                LayoutInflater inflater = (LayoutInflater)MyOrdersFragment.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_myorders_listview,null,true);
            }

            Product product = getItem(position);

            ImageView image = convertView.findViewById(R.id.imageProduct);
            TextView tvProduct = convertView.findViewById(R.id.txtProduct);
            TextView tvPrice = convertView.findViewById(R.id.txtPrice);

            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);

            image.setImageBitmap(bitmap);
            tvProduct.setText(product.getTitle());
            tvPrice.setText(product.getTitle());

            return convertView;
        }
    }

}
