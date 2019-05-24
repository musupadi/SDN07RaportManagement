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
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
import com.destinyapp.aplikasisdn07.Models.DataModel;
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
public class InputDataSiswaAdmin extends Fragment {

    Spinner kelas,jk;
    EditText nis,nama,tahunajaran,namaibu,namaayah,pekerjanaayah,pekerjaanibu;
    Button insert;
    String idKelas,jenisKelamin;
    String defaultPPSiswa = "logo.jpg";
    private List<DataModel> mItems = new ArrayList<>();
    private AdapterKelasSpinner aSpinner;
    public InputDataSiswaAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data_siswa_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kelas = (Spinner)view.findViewById(R.id.SpinnerKelas);
        jk = (Spinner)view.findViewById(R.id.SpinnerKelamin);
        nis = (EditText)view.findViewById(R.id.etNIS);
        nama = (EditText)view.findViewById(R.id.etNamaSiswa);
        tahunajaran = (EditText)view.findViewById(R.id.etTahunAjaran);
        namaibu = (EditText)view.findViewById(R.id.etIbuSiswa);
        namaayah = (EditText)view.findViewById(R.id.etAyahSiswa);
        pekerjaanibu = (EditText)view.findViewById(R.id.etPekerjaanIbu);
        pekerjanaayah = (EditText)view.findViewById(R.id.etPekerjaanAyah);
        insert = (Button)view.findViewById(R.id.btnInput);
        String UPDATE = this.getArguments().getString("KEY_UPDATE").toString();
        final String NIS = this.getArguments().getString("KEY_NIS").toString();
        List<String> JK = new ArrayList<>();
        JK.add(0,"Pilih Jenis Kelamin");
        JK.add("Pria");
        JK.add("Wanita");

        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,JK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jk.setAdapter(dataAdapter);

        jk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Jenis Kelamin")){
                    jenisKelamin = "Pilih Jenis Kelamin";
                }else{
                    jenisKelamin = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getKelas();
        aSpinner = new AdapterKelasSpinner(getActivity(),mItems);
        kelas.setAdapter(aSpinner);

        kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                String clickedItemNamaKelas = clickedItem.getNama_kelas();
                GetIDKelas(clickedItemNamaKelas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (UPDATE.equals("Update")){
            insert.setText("Update");
            getDataSiswa(NIS);
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateData();
                }
            });
        }else{
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Insert();
                }
            });
        }
    }
    private void UpdateData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> updateSiswa = api.UpdateSiswa(nis.getText().toString(),
                nama.getText().toString(),
                jenisKelamin,
                tahunajaran.getText().toString(),
                namaibu.getText().toString(),
                namaayah.getText().toString(),
                pekerjanaayah.getText().toString(),
                pekerjaanibu.getText().toString(),
                idKelas);
        updateSiswa.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Update")){
                    Toast.makeText(getActivity(),"Data Berhasil Diubah",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                    goInput.putExtra("OUTPUT_DATA_GURU","input");
                    getActivity().startActivities(new Intent[]{goInput});
                }else if(Response.equals("Insert")){
                    Toast.makeText(getActivity(),"Data Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam Method InsertData",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDataSiswa(final String niss){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetData = api.getDataSiswaFromNIS(niss);
        GetData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                nis.setText(niss);
                nama.setText(response.body().getNama_siswa());
                if (response.body().getJk_siswa().equals("Pria")){
                    jk.setSelection(1);
                }else if(response.body().getJk_siswa().equals("Wanita")){
                    jk.setSelection(2);
                }
                tahunajaran.setText(response.body().getTahunajaran());
                namaayah.setText(response.body().getNamaayah());
                namaibu.setText(response.body().getNamaibu());
                pekerjanaayah.setText(response.body().getPekerjaanayah());
                pekerjaanibu.setText(response.body().getPekerjaanibu());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetKelas = api.getKelas();
        GetKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterKelasSpinner adapter = new AdapterKelasSpinner(getActivity(),mItems);
                kelas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Insert(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertDataSiswa = api.insertDataSiswa(nis.getText().toString(),
                nama.getText().toString(),
                jenisKelamin,
                tahunajaran.getText().toString(),
                namaibu.getText().toString(),
                namaayah.getText().toString(),
                pekerjanaayah.getText().toString(),
                pekerjaanibu.getText().toString(),
                idKelas,
                defaultPPSiswa);
        insertDataSiswa.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Insert")){
                    Toast.makeText(getActivity(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                    goInput.putExtra("OUTPUT_DATA_GURU","input");
                    getActivity().startActivities(new Intent[]{goInput});
                }else if(Response.equals("Update")){
                    Toast.makeText(getActivity(),"Data NIS sudah teriisi",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam Method InsertData",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetIDKelas(String NamaKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDKelas = api.GetIDKelas(NamaKelas);
        getIDKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                idKelas = response.body().getId_kelas();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam GetIDKelas",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
