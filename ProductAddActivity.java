package com.project.tapnenjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.project.tapnenjoy.R;
import com.project.tapnenjoy.Models.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import com.project.tapnenjoy.DBHelper.DBHelper;

public class ProductAddActivity extends AppCompatActivity {
    private String Description, Pprice, Pname, Pstock, saveCurrentDate, saveCurrentTime;
    private Double Price;
    private Integer Id, Stock, sellerId;
    private byte[] image;
    private Boolean status;
    private Button AddNewProductButton, UpdateProductButton, QueryProductButton, DeleteProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice, InputProductStock;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    DBHelper myHelper;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        sellerId = 0;
        myHelper = new DBHelper(this);

        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        InputProductStock = (EditText) findViewById(R.id.product_stock);
        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        UpdateProductButton = (Button) findViewById(R.id.update_product);
        QueryProductButton = (Button) findViewById(R.id.query_product);
        DeleteProductButton = (Button) findViewById(R.id.delete_product);
        loadingBar = new ProgressDialog(this);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Pname = InputProductName.getText().toString();
                Description = InputProductDescription.getText().toString();
                Pprice = InputProductPrice.getText().toString();
                Price =  Double.valueOf(Pprice);
                Pstock = InputProductStock.getText().toString();
                Stock = Integer.valueOf(Pstock);
                InputStream iStream = null;
                try {
                    iStream = getContentResolver().openInputStream(ImageUri);
                    image = getBytes(iStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //sellerId = ;

                ValidateProductData();
            }
        });
    }



    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
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
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pprice))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pstock))
        {
            Toast.makeText(this, "Please write product stock...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }



    private void StoreProductInformation()
    {
        loadingBar.setTitle("Add New Product");
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
        Product product = new Product(Pname, Price, Description,image,Stock, sellerId, status);
        if(myHelper.insertProductData(product)){
            Toast.makeText(this,"Add new Product Successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductAddActivity.this, ProductDetailsActivity.class);
            intent.putExtra("Id", product.Id);
            startActivity(intent);;

        }else{
            Toast.makeText(this, getString(R.string.createUserErrorMessage), Toast.LENGTH_LONG).show();
        }

    }


}
