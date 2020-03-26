package com.project.tapnenjoy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.project.tapnenjoy.Models.UserOrdersAdapterToList;
import com.project.tapnenjoy.Models.UserWatchsAdapterToList;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrdersListAdapter extends ArrayAdapter<UserOrdersAdapterToList> {

    Context context;
    ArrayList<UserOrdersAdapterToList> userOrdersAdapterToList;
    Integer resource;

    OrdersListAdapter(Context c, Integer resource, ArrayList<UserOrdersAdapterToList> userOrdersAdapterToList){
        super(c, resource, userOrdersAdapterToList);
        this.userOrdersAdapterToList = userOrdersAdapterToList;
        this.context = c;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_products_listview,null,true);
        }

        UserOrdersAdapterToList userOrdersAdapterToList = getItem(position);

        ImageView image = convertView.findViewById(R.id.imageProduct);
        TextView tvProduct = convertView.findViewById(R.id.txtProduct);
        TextView tvPrice = convertView.findViewById(R.id.txtPrice);
        TextView tvInStock = convertView.findViewById(R.id.txtInStock);

        Bitmap bitmap = BitmapFactory.decodeByteArray(userOrdersAdapterToList.getImage(), 0, userOrdersAdapterToList.getImage().length);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String currency = format.format(userOrdersAdapterToList.getProductPrice());

        image.setImageBitmap(bitmap);
        tvProduct.setText(userOrdersAdapterToList.getProductTitle());
        tvPrice.setText(currency);
        tvInStock.setText("N/A");

        return convertView;
    }
}
