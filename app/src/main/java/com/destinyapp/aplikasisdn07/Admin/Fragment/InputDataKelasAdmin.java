package com.destinyapp.aplikasisdn07.Admin.Fragment;


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
        tingkatkelas = (Spinner)view.findViewById(R.id.SpinnerTingkatKelas);
        namaKelas = (EditText)view.findViewById(R.id.etNamaKelas);
        insert = (Button)view.findViewById(R.id.btnInput);

        List<String> TK = new ArrayList<>();
        TK.add(0,"Pilih Tingkat Kelas");
        TK.add("1");
        TK.add("2");
        TK.add("3");
        TK.add("4");
        TK.add("5");
        TK.add("6");
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,TK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tingkatkelas.setAdapter(dataAdapter);
        tingkatkelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Tingkat Kelas")){
                    tingkatKelas = "Pilih Tingkat Kelas";
                }else{
                    tingkatKelas = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataKelas();
            }
        });
    }
    private void insertDataKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getInsertKelas = api.insertDataKelas(namaKelas.getText().toString(),
                tingkatKelas);
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
                Toast.makeText(getActivity(),"Data Error pada method insertDataKelas",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
