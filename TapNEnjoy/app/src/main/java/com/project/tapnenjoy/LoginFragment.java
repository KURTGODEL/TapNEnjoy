package com.project.tapnenjoy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.project.tapnenjoy.DBHelper.DBHelper;


public class LoginFragment extends Fragment {

    DBHelper databaseHelper;
    TextInputLayout txtUsername;
    TextInputLayout txtPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity mainActivity = ((MainActivity)getActivity());

        mainActivity.closeDrawer();

        // hide tool bar on Login
        mainActivity.getSupportActionBar().hide();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        databaseHelper = new DBHelper(getContext());

        Button btnLogin = view.findViewById(R.id.btnLogin);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtPassword = view.findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = txtPassword.getEditText().getText().toString();
                String username = txtUsername.getEditText().getText().toString();

                if(username.isEmpty()){
                    txtUsername.setErrorEnabled(true);
                    txtUsername.setError(getString(R.string.lblRequired));
                }else{
                    txtUsername.setErrorEnabled(false);
                }

                if(password.isEmpty()) {
                    txtUsername.setErrorEnabled(true);
                    txtPassword.setError(getString(R.string.lblRequired));
                }else{
                    txtUsername.setErrorEnabled(false);
                }

                if(!password.isEmpty() && !username.isEmpty()) {
                    if (!databaseHelper.checkUsername(username)) {
                        txtPassword.setErrorEnabled(false);
                        txtUsername.setErrorEnabled(true);

                        txtUsername.setError(getString(R.string.notExistsMessage));
                        return;
                    }

                    if (databaseHelper.checkUsernameAndPassword(username, password)) {
                        moveMainAuthenticated();
                    } else {
                        txtUsername.setErrorEnabled(false);
                        txtPassword.setErrorEnabled(true);

                        txtPassword.setError(getString(R.string.incorrectPasswordMessage));
                    }
                }
            }
        });

        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSignUp();
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

    private void moveSignUp(){
        SignupFragment signupFragment = SignupFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, signupFragment).commit();
    }
}
