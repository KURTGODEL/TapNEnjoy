package com.project.tapnenjoy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.Models.Product;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.project.tapnenjoy.DBHelper.DBHelper;

public class ProductAddFragment extends AuthenticatedFragment {

    private String Description, Pprice, Pname, Pstock, saveCurrentDate, saveCurrentTime;
    private Double Price;
    private Integer Id, Stock, sellerId = 0;
    private byte[] image;
    private Boolean status;
    private Button addNewProductButton, updateProductButton, queryProductButton, deleteProductButton;
    private ImageView inputProductImage;
    private TextInputLayout inputProductName, inputProductDescription, inputProductPrice;
    private EditText inputProductStock;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private ProgressDialog loadingBar;


    MainActivity mainActivity;

    private DBHelper db;

    public ProductAddFragment() {
        // Required empty public constructor
    }

    public static ProductAddFragment newInstance() {
        return new ProductAddFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_add, container, false);
        mainActivity = ((MainActivity)getActivity());

        db = new DBHelper(getContext());

        inputProductImage = view.findViewById(R.id.select_product_image);
        inputProductName = view.findViewById(R.id.product_name);
        inputProductDescription = view.findViewById(R.id.product_description);
        inputProductPrice = view.findViewById(R.id.product_price);
        inputProductStock = view.findViewById(R.id.product_stock);
        addNewProductButton = view.findViewById(R.id.add_new_product);
        updateProductButton = view.findViewById(R.id.update_product);
        queryProductButton = view.findViewById(R.id.query_product);
        deleteProductButton = view.findViewById(R.id.delete_product);
        loadingBar = new ProgressDialog(getContext());

/*
        Bundle bundle = new Bundle();
        bundle.putInt("Id", 2);
        mainActivity.displayFragment(ProductDetailsFragment.class, bundle);
*/


        Button addNumberButton = view.findViewById(R.id.btn_add_number);
        Button subNumberButton = view.findViewById(R.id.btn_sub_number);

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        addNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer value = Integer.parseInt(inputProductStock.getText().toString());
                inputProductStock.setText(String.valueOf(value + 1));
            }
        });

        subNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer value = Integer.parseInt(inputProductStock.getText().toString());

                if(value > 0){
                    inputProductStock.setText(String.valueOf(value - 1));
                }
            }
        });


        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Pname = inputProductName.getEditText().getText().toString();
                Description = inputProductDescription.getEditText().getText().toString();
                Pprice = inputProductPrice.getEditText().getText().toString();
                Price =  Double.valueOf(Pprice);
                Pstock = inputProductStock.getText().toString();
                Stock = Integer.valueOf(Pstock);

                InputStream iStream = null;

                try {
                    Context context = mainActivity.getApplicationContext();
                    iStream = context.getContentResolver().openInputStream(ImageUri);

                    image = getBytes(iStream);

                    //Bitmap bitmap = BitmapFactory.decodeStream(iStream);
                    //InputProductImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ValidateProductData();
            }
        });

        return view;
    }


    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == mainActivity.RESULT_OK && data != null)
        {
            ImageUri = data.getData();
            inputProductImage.setImageURI(ImageUri);
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void ValidateProductData()
    {
        if (ImageUri == null)
        {
            Toast.makeText(getContext(), "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(getContext(), "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pprice))
        {
            Toast.makeText(getContext(), "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(getContext(), "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pstock))
        {
            Toast.makeText(getContext(), "Please write product stock...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        if(Stock > 0){
            status = true;
        }else{
            status = false;
        }

        Product product = new Product(0, Pname, Price, Description,image,Stock, sellerId, status);

        if(db.insertProductData(product)){
            Toast.makeText(getContext(),"Product Added Successfully.", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putInt("Id", product.Id);

            mainActivity.displayFragment(ProductDetailsFragment.class, bundle);
        }else{
            Toast.makeText(getContext(), getString(R.string.createUserErrorMessage), Toast.LENGTH_LONG).show();
        }
    }
}
