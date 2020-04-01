package com.project.tapnenjoy;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalFragment extends Fragment {

    Button confirmButton;
    Button cancelBtn;
    //ImageView productImage;
    private TextInputLayout txtshipmentName, txtshipmentPhoneNumber, txtshipmentAddress, txtshipmentCity;
    MainActivity mainActivity;
    private DBHelper db;
    private String totalAmount = "";
    private String shipmentName,shipmentPhoneNumber,shipmentAddress,shipmentCity;


    public ConfirmFinalFragment() {
        // Required empty public constructor
    }

    public static ConfirmFinalFragment newInstance() {
        return new ConfirmFinalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirm_final, container, false);

        mainActivity = ((MainActivity) getActivity());
        mainActivity.setToolbarTitle("Final Confirmation");
        mainActivity.setUpDrawerButton();
        mainActivity.hideProgressDialog();

        db = new DBHelper(getContext());
        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            totalAmount = bundle.getString("totalAmount", "0");
        }
        //totalAmount = this.getArguments().getString("totalAmount");
        */
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderProduct",
                Context.MODE_PRIVATE);
        String totalAmount =sharedPreferences.getString("totalAmount", "");
        Toast.makeText(this.getActivity(), "Total Price =  $ " + totalAmount, Toast.LENGTH_SHORT).show();

        txtshipmentName = view.findViewById(R.id.shipment_name);
        txtshipmentPhoneNumber  = view.findViewById(R.id.shipment_phone_number);
        txtshipmentAddress = view.findViewById(R.id.shipment_address);
        txtshipmentCity = view.findViewById(R.id.shipment_city);
        confirmButton = view.findViewById(R.id.confirm_final_order_btn);
        cancelBtn = view.findViewById(R.id.cancel_button);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteUserOrderData(getArguments().getInt("userOrderID"));
                mainActivity.displayFragment( ProductDetailsFragment.class);
            }
        });
        return view;
    }

    private void Check() {
        shipmentName = txtshipmentName.getEditText().getText().toString();
        shipmentPhoneNumber = txtshipmentPhoneNumber.getEditText().getText().toString();
        shipmentAddress = txtshipmentAddress.getEditText().getText().toString();
        shipmentCity = txtshipmentCity.getEditText().getText().toString();

        Toast.makeText(this.getActivity(), "the product Name in shipment: " + shipmentName, Toast.LENGTH_SHORT).show();

        if (shipmentName.isEmpty()) {
            txtshipmentName.setErrorEnabled(true);
            txtshipmentName.setError(getString(R.string.lblRequired));
        } else {
            txtshipmentName.setErrorEnabled(false);
        }

        if (shipmentPhoneNumber.isEmpty()) {
            txtshipmentPhoneNumber.setErrorEnabled(true);
            txtshipmentPhoneNumber.setError(getString(R.string.lblRequired));
        } else {
            txtshipmentPhoneNumber.setErrorEnabled(false);
        }
        if (shipmentAddress.isEmpty()) {
            txtshipmentAddress.setErrorEnabled(true);
            txtshipmentAddress.setError(getString(R.string.lblRequired));
        } else {
            txtshipmentAddress.setErrorEnabled(false);
        }
        if (shipmentCity.isEmpty()) {
            txtshipmentCity.setErrorEnabled(true);
            txtshipmentCity.setError(getString(R.string.lblRequired));
        } else {
            txtshipmentCity.setErrorEnabled(false);
        }

        ConfirmOrder();



    }


    // store the user's information in tables users & userorder and get an order ID
    private void ConfirmOrder() {
        mainActivity.showProgressDialog("Confirm the Order", "Please wait while we are confirming the new order.");


        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final HashMap<String, Object> userorderMap = new HashMap<>();

        userorderMap.put("totalAmount", totalAmount);
        userorderMap.put("name", shipmentName);
        userorderMap.put("phone", shipmentPhoneNumber);
        userorderMap.put("address", shipmentAddress);
        userorderMap.put("city", shipmentCity);
        userorderMap.put("date", saveCurrentDate);
        userorderMap.put("time", saveCurrentTime);
        userorderMap.put("state", "not shipped");

        Toast.makeText(getActivity(), "The order information has been stored.", Toast.LENGTH_LONG).show();
 /*
        Bundle bundle = new Bundle();
        bundle.putInt("userOrderID",getArguments().getInt("userOrderID"));
        ConfirmFinalFragment confirmFinalFragment = new ConfirmFinalFragment();
        confirmFinalFragment.setArguments(bundle);
*/

        mainActivity.displayFragment(ShoppingResultFragment.class);


    }
}

