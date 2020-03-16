package com.project.tapnenjoy;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.DBHelper.DBHelper;
import com.project.tapnenjoy.Models.User;

import java.io.IOException;
import java.util.List;


public class SignupFragment extends Fragment {

    DBHelper databaseHelper;
    TextInputLayout txtUsername;
    TextInputLayout txtPassword;
    TextInputLayout txtAddress;
    SwitchMaterial swichIsSeller;
    TextInputLayout txtPasswordConfirm;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        MainActivity mainActivity = ((MainActivity)getActivity());

        mainActivity.toogleOptionsMenu(false);
        mainActivity.setToolbarTitle(getString(R.string.lblSignUp));

        // show action bar once again e override back button
        ActionBar actionBar = mainActivity.getSupportActionBar();

        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_darker_24dp);


        Button back = view.findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLogin();
            }
        });

        databaseHelper = new DBHelper(getContext());

        Button btn = view.findViewById(R.id.btnSignUp);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtPasswordConfirm = view.findViewById(R.id.txtPasswordConfirm);
        txtAddress = view.findViewById(R.id.txtAddress);
        swichIsSeller = view.findViewById(R.id.switchIsSeller);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtPassword.getEditText().getText().toString();
                String passwordConfirm = txtPasswordConfirm.getEditText().getText().toString();
                String username = txtUsername.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();
                Boolean isSeller = swichIsSeller.isSelected();

                if(username.isEmpty()){
                    txtUsername.setErrorEnabled(true);
                    txtUsername.setError(getString(R.string.lblRequired));
                }else{
                    txtUsername.setErrorEnabled(false);
                }

                if(password.isEmpty()) {
                    txtPassword.setErrorEnabled(true);
                    txtPassword.setError(getString(R.string.lblRequired));
                }else{
                    txtPassword.setErrorEnabled(false);
                }

                if(passwordConfirm.isEmpty()) {
                    txtPasswordConfirm.setErrorEnabled(true);
                    txtPasswordConfirm.setError(getString(R.string.lblRequired));
                }else{
                    txtPasswordConfirm.setErrorEnabled(false);
                }

                if(!password.isEmpty() && passwordConfirm != password){
                    txtPasswordConfirm.setErrorEnabled(true);
                    txtPasswordConfirm.setError(getString(R.string.passwordsMatchMessage));
                }else{
                    txtPasswordConfirm.setErrorEnabled(false);
                }

                if(address.isEmpty()) {
                    txtAddress.setErrorEnabled(true);
                    txtAddress.setError(getString(R.string.lblRequired));
                }else{
                    txtAddress.setErrorEnabled(false);
                }

                if(!password.isEmpty() && !username.isEmpty() && !passwordConfirm.isEmpty()&& !address.isEmpty()) {
                    if(databaseHelper.checkUsername(username)){
                        txtUsername.setErrorEnabled(true);
                        txtUsername.setError(getString(R.string.userExistsMessage));
                    }else{
                        Pair<Float, Float> latLng =
                                GeoLocationHelper.getLocationFromAddress(getContext(), address);

                        User user = new User(
                                username,
                                username,
                                password,
                                address,
                                latLng.first,
                                latLng.second,
                                isSeller,
                                true);
                        user.setUserIsSeller(true);

                        if(databaseHelper.insertUserData(user)){
                            new MaterialAlertDialogBuilder(getContext())
                                    .setTitle("System says:")
                                    .setMessage(getString(R.string.createUserSuccessMessasge))
                                    .setPositiveButton(getString(R.string.lblOk), (new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        moveLogin();
                                        }
                                    }))
                                    .show();
                        }else{
                            Toast.makeText(getContext(), getString(R.string.createUserErrorMessage), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        return view;
    }

    private void moveLogin(){
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, loginFragment).commit();
    }


    public void onBackPressed() {
        moveLogin();
    }
}
