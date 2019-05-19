package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterNIS;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AbsenSiswa extends Fragment {
    Button btnInsert;
    Spinner Kelas;
    AutoCompleteTextView NIS;
    EditText Izin,Alpa,Sakit,NamaSiswa;
    DB_Helper dbHelper;
    String User="";
    private List<DataModel> aList = new ArrayList<>();
    private AdapterKelasSpinner aSpinner;
    public AbsenSiswa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_absen_siswa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnInsert=(Button)view.findViewById(R.id.btnInsert);
        Kelas=(Spinner)view.findViewById(R.id.SpinnerKelas);
        NIS=(AutoCompleteTextView)view.findViewById(R.id.ACTNIS);
        NamaSiswa =(EditText)view.findViewById(R.id.etNamaSiswa);
        Izin=(EditText)view.findViewById(R.id.etIzin);
        Sakit=(EditText)view.findViewById(R.id.etSakit);
        Alpa=(EditText)view.findViewById(R.id.etAlpa);
        getKelas();
        aSpinner = new AdapterKelasSpinner(getActivity(),aList);
        Kelas.setAdapter(aSpinner);
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();

        while (cursor.moveToNext()){
            User=cursor.getString(0);
        }


        Kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                String clickedItemNamaKelas = clickedItem.getNama_kelas();
                GetIDKelas(clickedItemNamaKelas);
                kosong();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        NIS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String nis = NIS.getEditableText().toString();
                    getNamaFromNIS(nis);
                    getAbsen();
                }
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }
    private void insertData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertAbsenSiswa = api.insertAbsenSiswa(NIS.getEditableText().toString(),User,
                Sakit.getText().toString(),Izin.getText().toString(),Alpa.getText().toString());
        insertAbsenSiswa.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getResponse().equals("Insert")){
                    Toast.makeText(getActivity(),"Data Absen Berhasil Ditambahkan",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
                    getActivity().startActivities(new Intent[]{goInput});
                }else{
                    Toast.makeText(getActivity(),"Data Absen Sudah Terisi Harap hubungi Admin",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAbsen(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getAbsenSiswa = api.GetAbsenSiswa(NIS.getEditableText().toString());
        getAbsenSiswa.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Izin.setText(response.body().getIzin());
                Alpa.setText(response.body().getAlpa());
                Sakit.setText(response.body().getSakit());
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
                aList=response.body().getResult();
                AdapterKelasSpinner adapter = new AdapterKelasSpinner(getActivity(),aList);
                Kelas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetIDKelas(String NamaKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDKelas = api.GetIDKelas(NamaKelas);
        getIDKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String idKelas = response.body().getId_kelas();
                String tingkatKelas = response.body().getTingkat_kelas();
                getAutoText(idKelas);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getAUtoNISData",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoText(String idKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getAuto = api.getAllSiswaFromGuru(idKelas);
        getAuto.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                aList = response.body().getResult();
                AdapterNIS adapterNIS = new AdapterNIS(getActivity(),aList);
                NIS.setAdapter(adapterNIS);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada getAutoText Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaFromNIS(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNama = api.getDataSiswa(nis);
        getNama.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String nama = response.body().getNama_siswa();
                NamaSiswa.setText(nama);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getNamaFromNis Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void kosong(){
        NIS.setText("");
        NamaSiswa.setText("");
        Izin.setText("");
        Sakit.setText("");
        Alpa.setText("");
    }
}
