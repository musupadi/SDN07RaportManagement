package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputDataKelasAdmin extends Fragment {

    Spinner tingkatkelas;
    EditText namaKelas;
    Button insert;
    String tingkatKelas;

    public InputDataKelasAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data_kelas_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tingkatkelas = (Spinner)view.findViewById(R.id.spinnerTingkatKelas);
        namaKelas = (EditText)view.findViewById(R.id.etNamaKelas);
        insert = (Button)view.findViewById(R.id.btnInput);
        String UPDATE = this.getArguments().getString("KEY_UPDATE").toString();
        final String IDKELAS = this.getArguments().getString("KEY_KELAS").toString();
        if (UPDATE.equals("Update")){
            getDataKelas(IDKELAS);
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateData(IDKELAS);
                }
            });
        }else{
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertDataKelas();
                }
            });
        }
    }
    private void UpdateData(String IDKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> updateData = api.UpdateKelas(IDKelas,
                namaKelas.getEditableText().toString(),
                tingkatkelas.getSelectedItem().toString());
        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getResponse().equals("Update")){
                    Toast.makeText(getActivity(),"Data Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                    goInput.putExtra("OUTPUT_KELAS","output_kelas");
                    getActivity().startActivities(new Intent[]{goInput});
                }else{
                    Toast.makeText(getActivity(),"Data Gagal Terupdate",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDataKelas(String IDKelas){

        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getDataKelas = api.Classed(IDKelas);
        getDataKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                namaKelas.setText(response.body().getNama_kelas());
                tingkatkelas.setSelection(Integer.parseInt(response.body().getTingkat_kelas()));
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void insertDataKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getInsertKelas = api.insertDataKelas(namaKelas.getText().toString(),
                tingkatkelas.getSelectedItem().toString());
        getInsertKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Insert")){
                    Toast.makeText(getActivity(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                }else if(Response.equals("Update")){
                    Toast.makeText(getActivity(),"Data Data Kelas sudah teriisi",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
