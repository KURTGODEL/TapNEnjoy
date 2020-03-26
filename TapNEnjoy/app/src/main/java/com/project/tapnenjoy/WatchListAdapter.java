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
import com.project.tapnenjoy.Models.UserWatchsAdapterToList;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WatchListAdapter extends ArrayAdapter<UserWatchsAdapterToList> {

    Context context;
    ArrayList<UserWatchsAdapterToList> userWatchsAdapterToList;
    Integer resource;

    WatchListAdapter(Context c, Integer resource, ArrayList<UserWatchsAdapterToList> userWatchsAdapterToList){
        super(c, resource, userWatchsAdapterToList);
        this.userWatchsAdapterToList = userWatchsAdapterToList;
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

        UserWatchsAdapterToList userWatchsAdapterToList = getItem(position);

        ImageView image = convertView.findViewById(R.id.imageProduct);
        TextView tvProduct = convertView.findViewById(R.id.txtProduct);
        TextView tvPrice = convertView.findViewById(R.id.txtPrice);
        TextView tvInStock = convertView.findViewById(R.id.txtInStock);

        Bitmap bitmap = BitmapFactory.decodeByteArray(userWatchsAdapterToList.getImage(), 0, userWatchsAdapterToList.getImage().length);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String currency = format.format(userWatchsAdapterToList.getPrice());
        Integer inStock = userWatchsAdapterToList.getStock();

        image.setImageBitmap(bitmap);
        tvProduct.setText(userWatchsAdapterToList.getProductTitle());
        tvPrice.setText(currency);
        tvInStock.setText(inStock.toString());

        return convertView;
    }

}
