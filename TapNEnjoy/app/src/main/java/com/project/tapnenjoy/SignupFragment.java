package com.project.tapnenjoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.tapnenjoy.DBHelper.DBHelper;


public class SignupFragment extends Fragment {

    DBHelper databaseHelper;
    TextView txtUsername;
    TextView txtPassword;
    TextView txtPasswordConfirm;

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

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Button back = view.findViewById(R.id.btnBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveHome();
            }
        });

        /*
        databaseHelper = new DBHelper(getContext());

        Button btn = view.findViewById(R.id.btnSignUp);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtPasswordConfirm = view.findViewById(R.id.txtPasswordConfirm);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtPassword.getText().toString();
                String passwordConfirm = txtPasswordConfirm.getText().toString();
                String username = txtUsername.getText().toString();

                if(password.isEmpty() || username.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(getContext(), "Missing Information", Toast.LENGTH_LONG).show();
                }else{
                    if(!password.equals(passwordConfirm)){
                        Toast.makeText(getContext(), "Passwords Must Match", Toast.LENGTH_LONG).show();
                    }else{
                        if(databaseHelper.checkUsername(username)){
                            Toast.makeText(getContext(), "Username Already Exists", Toast.LENGTH_LONG).show();
                        }else{
                            if(databaseHelper.insertUserData(null)){ // FIX
                                moveHome();
                                Toast.makeText(getContext(), "User Created", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(), "Error to Create User", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
        */

        return view;
    }

    private void moveHome(){
        HomeFragment homeFragment = HomeFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, homeFragment).commit();
    }

}
