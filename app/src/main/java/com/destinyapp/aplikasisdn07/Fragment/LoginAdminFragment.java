package com.destinyapp.aplikasisdn07.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;
import com.destinyapp.aplikasisdn07.Session.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginAdminFragment extends Fragment {

    EditText username,password;
    Button Login;
    private DB_Helper dbHelper;

    public LoginAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = (EditText)view.findViewById(R.id.etUsername);
        password = (EditText)view.findViewById(R.id.etPassword);
        Login = (Button)view.findViewById(R.id.btnLogin);
        dbHelper = new DB_Helper(getActivity());
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin();
            }
        });


    }
    private void SessionLoginSucces(String Username,String Password){
        String Person="Admin";
        dbHelper = new DB_Helper(getActivity());
        if(Username.isEmpty()){
            Toast.makeText(getActivity(),"Silahkan Masukan Username",Toast.LENGTH_SHORT).show();
        }
        if(Password.isEmpty()){
            Toast.makeText(getActivity(),"Silahkan Masukan Password",Toast.LENGTH_SHORT).show();
        }

        User user = new User(Username,Person);
        dbHelper.saveSession(user);
    }
    private void CheckLogin(){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Sedang Mengambil Data Ke Server");
        pd.setCancelable(false);
        pd.show();
        final String Username = username.getText().toString();
        final String Password = password.getText().toString();
        if(Username.isEmpty()){
            Toast.makeText(getActivity(),"Silahkan Masukan Username",Toast.LENGTH_SHORT).show();
        }
        if(Password.isEmpty()){
            Toast.makeText(getActivity(),"Silahkan Masukan Password",Toast.LENGTH_SHORT).show();
        }
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);

        Call<ResponseModel> login = api.getAdminLogin(Username,Password);
        login.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.hide();
                String ress = response.body().getResponse();
                if(ress.equals("succes")){
                    SessionLoginSucces(Username,Password);
                    Intent intent = new Intent(getActivity(), MainAdminActivity.class);
                    startActivity(intent);
                }else{
                    Snackbar.make(getView(),"Username atau Password Salah", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Snackbar.make(getView(),"Data Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
