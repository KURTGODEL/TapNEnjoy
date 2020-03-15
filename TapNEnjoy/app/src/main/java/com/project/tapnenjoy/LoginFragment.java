package com.project.tapnenjoy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tapnenjoy.DBHelper.DBHelper;


public class LoginFragment extends Fragment {

    DBHelper databaseHelper;
    TextView txtUsername;
    TextView txtPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        databaseHelper = new DBHelper(getContext());

        Button btnLogin = view.findViewById(R.id.btnLogin);
        Button btnSignup = view.findViewById(R.id.btnSignUp);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String password = txtPassword.getText().toString();
            String username = txtUsername.getText().toString();

            if(password.isEmpty() || username.isEmpty()) {
                Toast.makeText(getContext(), "Missing Information", Toast.LENGTH_LONG).show();
            }else{
                if(databaseHelper.checkUsernameAndPassword(username, password)){
                    moveMainAuthenticated();
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "User not Registered", Toast.LENGTH_LONG).show();
                }
            }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSignup();
            }
        });


        return view;
    }

    private void moveMainAuthenticated(){
        MainAuthenticatedFragment mainAuthenticatedFragment = MainAuthenticatedFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, mainAuthenticatedFragment).commit();
    }

    private void moveSignup(){
        SignupFragment signupFragment = SignupFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, signupFragment).commit();
    }

}
