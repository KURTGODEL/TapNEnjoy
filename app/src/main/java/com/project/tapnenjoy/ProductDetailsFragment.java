package com.project.tapnenjoy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;
import com.project.tapnenjoy.Models.UserOrder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsFragment extends AuthenticatedFragment {
    Button addToCartButton, buyButton, offerButton;
    ImageView productImage;
    EditText txtNumber;
    TextView productPrice, productDescription, productName;
    String state = "Normal";
    boolean status;
    Integer productID = 0;
    //new
    Double totalPrice;
    String priceName;
    //end of new
    MainActivity mainActivity;
    private DBHelper db;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance() {
        return new ProductDetailsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_details, container, false);

        mainActivity = ((MainActivity) getActivity());
        mainActivity.setToolbarTitle("Product Detail");
        mainActivity.setUpDrawerButton();
        mainActivity.hideProgressDialog();

        db = new DBHelper(getContext());


        final Bundle bundle = this.getArguments();

        /*if (bundle != null) {
            productID = bundle.getInt("Id", 0);
        }*/
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderProduct",
                Context.MODE_PRIVATE);
        productID =sharedPreferences.getInt("productId",0);

        addToCartButton = view.findViewById(R.id.pd_add_to_cart_button);
        buyButton  = view.findViewById(R.id.pd_buy_button);
        offerButton = view.findViewById(R.id.pd_offer_button);
        txtNumber = view.findViewById(R.id.txt_number);
        productImage = view.findViewById(R.id.product_image_details);
        productName = view.findViewById(R.id.product_name_details);
        productDescription = view.findViewById(R.id.product_description_details);
        productPrice = view.findViewById(R.id.product_price_details);

        Button addNumberButton = view.findViewById(R.id.btn_add_number);
        Button subNumberButton = view.findViewById(R.id.btn_sub_number);

        getProductDetails(productID);

        addNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer value = Integer.parseInt(txtNumber.getText().toString());
                txtNumber.setText(String.valueOf(value + 1));
            }
        });

        subNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer value = Integer.parseInt(txtNumber.getText().toString());

                if (value > 0) {
                    txtNumber.setText(String.valueOf(value - 1));
                }
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals("Order Placed") || state.equals("Order Shipped")) {
                    Toast.makeText(getContext(), "you can add purchase more products, once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();
                } else {
                    addingToCartList();
                }
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProductDetails(productID);
                totalPrice = Double.parseDouble(productPrice.getText().toString()) * Integer.parseInt(txtNumber.getText().toString());
                Toast.makeText(getActivity(),"Price: " + productPrice.getText().toString() + "\n"
                        +"Number: "+txtNumber.getText().toString()+ "\n"
                        + "Total Amount:  " + String.valueOf(totalPrice), Toast.LENGTH_LONG).show();


                int sellerId = db.getProductById(productID).sellerId;
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderProduct", Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("productId", productID).apply();
                sharedPreferences.edit().putInt("sellerId",sellerId).apply();
                sharedPreferences.edit().putString("totalAmount",String.valueOf(totalPrice)).apply();

                int userId = sharedPreferences.getInt("userId",0);

               if(state != "Normal"){
                   status = false;
               }else{
                   status = true;
               }

                int userOrderId  =
                        db.insertUserOrderData(
                                new UserOrder(
                                userId,
                                sellerId,
                                productID,
                                Integer.parseInt(txtNumber.getText().toString()),
                                status
                        ));

                if(userOrderId != -1 ) {

                    /*
                    Bundle bundle1 = new Bundle();

                    bundle.putString("totalAmount",String.valueOf(totalPrice));
                    bundle.putInt("userOrderID",userOrderId);
                    ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                    productDetailsFragment.setArguments(bundle);
                      */
                    sharedPreferences.edit().putInt("userOrderId",userOrderId).apply();
                    Toast.makeText(getActivity(),"UserId: " + userOrderId + "\n"
                            +"SellerId: "+sellerId + "\n"
                            + "ProductId:  " + productID +"\n", Toast.LENGTH_LONG).show();

                    mainActivity.displayFragment(ConfirmFinalFragment.class);

                }else{
                    Toast.makeText(getContext(), "Error on creating order", Toast.LENGTH_LONG).show();
                }


            }
        });

        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                priceName = productName.getText().toString();
                Bundle bundle1 = new Bundle();
                assert bundle != null;
                bundle.putString("priceName",priceName);
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                productDetailsFragment.setArguments(bundle);
                */
                mainActivity.displayFragment(MakeOfferFragment.class);
            }
        });

        return view;
    }

    private HashMap addingToCartList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", txtNumber.getText());
        cartMap.put("discount", "");

        return  cartMap;
    }


    private void getProductDetails(Integer productID) {
        Product product = db.getProductById(productID);

        if(product != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.image, 0, product.image.length);
            productImage.setImageBitmap(bitmap);

            productName.setText(product.title);
            productDescription.setText(product.description);
            productPrice.setText(product.price.toString());

            mainActivity.setToolbarTitle(product.title);
        }
    }
}