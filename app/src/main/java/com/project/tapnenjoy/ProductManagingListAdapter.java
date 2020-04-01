package com.project.tapnenjoy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.tapnenjoy.Models.Product;
import com.project.tapnenjoy.Models.ProductManagingAdapterToList;
import com.project.tapnenjoy.Models.UserOrdersAdapterToList;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductManagingListAdapter extends ArrayAdapter<ProductManagingAdapterToList> {

    MainActivity mainActivity;
    ArrayList<ProductManagingAdapterToList> productManagingAdapterToLists;
    ProductManagingAdapterToList productManagingAdapterToList;
    Integer resource;

    ProductManagingListAdapter(Context c, MainActivity mainActivity, Integer resource, ArrayList<ProductManagingAdapterToList> productManagingAdapterToLists){
        super(c, resource, productManagingAdapterToLists);
        this.productManagingAdapterToLists = productManagingAdapterToLists;
        this.mainActivity = mainActivity;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_product_managing_listview,null,true);
        }

        productManagingAdapterToList = getItem(position);

        ImageView image = convertView.findViewById(R.id.imageProduct);

        Bitmap bitmap = BitmapFactory.decodeByteArray(productManagingAdapterToList.getImage(), 0, productManagingAdapterToList.getImage().length);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String currency = format.format(productManagingAdapterToList.getPrice());

        image.setImageBitmap(bitmap);

        ((TextView)convertView.findViewById(R.id.txtProduct))
                .setText(productManagingAdapterToList.getTitle());
        ((TextView)convertView.findViewById(R.id.txtPrice))
                .setText(currency);


        ((Button)convertView.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("EditId", getItem(position).getId());
                mainActivity.displayFragment(ProductAddFragment.class, bundle);
            }
        });

        ((Button)convertView.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DeleteId", getItem(position).getId());
                mainActivity.displayFragment(ProductAddFragment.class, bundle);
            }
        });

        return convertView;
    }

}
