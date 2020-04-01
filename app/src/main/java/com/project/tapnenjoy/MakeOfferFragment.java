package com.project.tapnenjoy;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;

public class MakeOfferFragment extends Fragment {
    Button sendOfferButton;
    ImageView productImage;
    EditText offerPrice, productNum, offerReason;
    TextView productName;
    String state = "Normal";
    Integer productID = 0;
    //new
    Double totalPrice;
    String priceName;
    //end of new
    MainActivity mainActivity;
    private DBHelper db;

    public  MakeOfferFragment() {
        // Required empty public constructor
    }

    public static  MakeOfferFragment newInstance() {
        return new  MakeOfferFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_offer, container, false);

        mainActivity = ((MainActivity) getActivity());
        mainActivity.setToolbarTitle("Make Your Offer");
        mainActivity.setUpDrawerButton();
        mainActivity.hideProgressDialog();

        db = new DBHelper(getContext());

        sendOfferButton = view.findViewById(R.id.send_offer_btn);
        offerReason = view.findViewById(R.id.txt_offerreason);
        productImage = view.findViewById(R.id.productimage);
        productName = view.findViewById(R.id.offer_product_name);
        productNum = view.findViewById(R.id.offer_number);
        offerPrice = view.findViewById(R.id.offer_price);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderProduct",
                Context.MODE_PRIVATE);
        int productID =sharedPreferences.getInt("productId",0);
        //Toast.makeText(getActivity(),"productID:"+productID ,Toast.LENGTH_LONG).show();
        getProductDetails(productID);

        sendOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mainActivity.showProgressDialog("Making an offer", "Please wait while we are sending the offer.");
                Toast.makeText(getActivity(),"YOUR OFFER HAS BEEN SENT: \n"
                        +  "Your offer the Price: " + offerPrice.getText() +"\n"
                        +  "the amount you need: " + productNum.getText() +"\n"
                        +  "Offer Reason: " + offerReason.getText()+"\n"
                        +   "Please check your MsgBox",Toast.LENGTH_LONG).show();

                mainActivity.displayFragment( ProductDetailsFragment.class);
            }
        });


        return view;
    }

    private void getProductDetails(Integer productID) {
        Product product = db.getProductById(productID);

        if(product != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.image, 0, product.image.length);
            productImage.setImageBitmap(bitmap);

            productName.setText(product.title);
            //mainActivity.setToolbarTitle(product.title);
        }


    }
}


