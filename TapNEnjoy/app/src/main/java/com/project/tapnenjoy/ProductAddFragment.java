package com.project.tapnenjoy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.Models.Product;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventListener;
import java.util.Locale;

import com.project.tapnenjoy.DBHelper.DBHelper;

public class ProductAddFragment extends AuthenticatedFragment {

    private String prodDescription, prodName, strPrice, strStock;
    private Double prodPrice = 0.0, prodStock = 0.0;
    private Integer editId = 0, deleteId = 0;
    private byte[] prodImage;
    private Button addNewProductButton, updateProductButton, queryProductButton, deleteProductButton;
    private ImageView inputProductImage;
    private TextInputLayout inputProductName, inputProductDescription, inputProductPrice;
    private EditText inputProductStock;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    ProgressDialog progressDialog;

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
        mainActivity.setUpBackButton();
        mainActivity.setToolbarTitle("Add Product");

        db = new DBHelper(getContext());


        // initialize input elements
        inputProductImage = view.findViewById(R.id.select_product_image);
        inputProductName = view.findViewById(R.id.product_name);
        inputProductDescription = view.findViewById(R.id.product_description);
        inputProductPrice = view.findViewById(R.id.product_price);
        inputProductStock = view.findViewById(R.id.product_stock);
        addNewProductButton = view.findViewById(R.id.add_new_product);
        updateProductButton = view.findViewById(R.id.update_product);
        queryProductButton = view.findViewById(R.id.query_product);
        deleteProductButton = view.findViewById(R.id.delete_product);

        // check if came from another fragment carrying actions
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            editId = bundle.getInt("EditId", 0);
            deleteId = bundle.getInt("DeleteId", 0);

            if(editId != 0){
                getProductDetails(editId);
                addNewProductButton.setVisibility(View.GONE);
                deleteProductButton.setVisibility(View.VISIBLE);
                updateProductButton.setVisibility(View.VISIBLE);
            }

            if(deleteId != 0){
                progressDialog =
                    mainActivity.showProgressDialog(
                            "Deleting Product",
                            "Please wait while we are deleting the product.");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(db.deleteProduct(deleteId) == 0){
                            Toast.makeText(getContext(), "No products deleted", Toast.LENGTH_LONG);
                        }

                        progressDialog.dismiss();

                        mainActivity.displayFragment(ProductManagingListFragment.class);
                    }
                }, 1500);

                return null;
            }
        }


        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        // elements and listeners for Adding or Subtracting stock amount
        Button addNumberButton = view.findViewById(R.id.btn_add_number);
        Button subNumberButton = view.findViewById(R.id.btn_sub_number);

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

        // event listener for product inclusion or updation used by two buttons
        Button.OnClickListener listener =
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        prodName = inputProductName.getEditText().getText().toString();
                        prodDescription = inputProductDescription.getEditText().getText().toString();
                        strPrice = inputProductPrice.getEditText().getText().toString();
                        strStock = inputProductStock.getText().toString();

                        ValidateProductData();
                    }
                };


        addNewProductButton.setOnClickListener(listener);
        updateProductButton.setOnClickListener(listener);
        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editId != 0){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(db.deleteProduct(editId) == 0){
                                Toast.makeText(getContext(), "No products deleted", Toast.LENGTH_LONG);
                            }

                            if(progressDialog != null){
                                progressDialog.dismiss();
                            }

                            mainActivity.displayFragment(ProductManagingListFragment.class);
                        }
                    }, 1500);
                }
            }
        });

        return view;
    }

    private void getProductDetails(Integer productID) {
        Product product = db.getProductById(productID);

        if(product != null){
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(product.image, 0, product.image.length);

                if(bitmap != null) {
                    inputProductImage.setImageBitmap(bitmap);
                }
            }catch (Exception ex){
                Log.e("SYSERROR", ex.getMessage());
            }

            inputProductName.getEditText().setText(product.title);
            inputProductDescription.getEditText().setText(product.description);
            inputProductStock.setText(String.valueOf(product.stock));
            inputProductPrice.getEditText().setText(product.price.toString());

            mainActivity.setToolbarTitle(product.title);
        }
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
            imageUri = data.getData();
            inputProductImage.setImageURI(imageUri);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.setTitle(R.string.lblAlertTitle);

        if (imageUri == null && editId == 0)
        {
            alert.setMessage("Product image is mandatory...");
            alert.show();
        }else{
            if(editId != 0){
                Bitmap bitmap = ((BitmapDrawable) inputProductImage.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                prodImage = byteArrayOutputStream.toByteArray();
            }else{
                try {
                    Context context = mainActivity.getApplicationContext();
                    InputStream iStream = context.getContentResolver().openInputStream(imageUri);
                    prodImage = getBytes(iStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            alert.hide();
            alert.dismiss();
        }

        if (TextUtils.isEmpty(prodDescription))
        {
            inputProductDescription.setErrorEnabled(true);
            inputProductDescription.setError(getString(R.string.lblRequired));
        }else{
            inputProductDescription.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(strPrice))
        {
            inputProductPrice.setErrorEnabled(true);
            inputProductPrice.setError(getString(R.string.lblRequired));
        }else{
            prodPrice =  Double.valueOf(strPrice);
            inputProductPrice.setErrorEnabled(false);
        }

        if (!TextUtils.isEmpty(strStock)) {
            prodStock = Double.valueOf(strStock);
        }

        if (TextUtils.isEmpty(prodName))
        {
            inputProductName.setErrorEnabled(true);
            inputProductName.setError(getString(R.string.lblRequired));
        }
        else
        {
            inputProductDescription.setErrorEnabled(false);
            inputProductPrice.setErrorEnabled(false);
            inputProductName.setErrorEnabled(false);

            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        mainActivity.showProgressDialog("Adding New Product", "Please wait while we are adding the new product.");

        if(editId != 0){
            Boolean result =
                    db.updateProductData(
                        new Product(
                            editId,
                            prodName,
                            prodPrice,
                            prodDescription,
                            prodImage,
                            (int)Math.round(prodStock),
                            mainActivity.getAuthenticatedUserId(),
                            true));

            if(result){
                Bundle bundle = new Bundle();
                bundle.putInt("Id", editId);

                mainActivity.displayFragment(ProductDetailsFragment.class, bundle);
            }else{
                Toast.makeText(getContext(), "Error on updating product", Toast.LENGTH_LONG).show();
            }
        }else{
            Integer result =
                    db.insertProductData(
                        new Product(
                            0,
                            prodName,
                            prodPrice,
                            prodDescription,
                            prodImage,
                            (int)Math.round(prodStock),
                            mainActivity.getAuthenticatedUserId(),
                            true));

            if(result != 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("Id", result);

                mainActivity.displayFragment(ProductDetailsFragment.class, bundle);
            }else{
                Toast.makeText(getContext(), "Error on creating product", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onBackPressed() {
        mainActivity.displayFragment(ProductManagingListFragment.class);
    }
}
