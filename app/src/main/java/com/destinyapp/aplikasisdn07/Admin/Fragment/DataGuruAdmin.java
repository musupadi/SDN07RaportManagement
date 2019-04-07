package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DataGuruAdmin extends Fragment {

    EditText nip,password,nama,TempatLahir,tgl,bulan,tahun,agama,notelp,jabatan,pendidikan,alamat;
    Spinner jk;
    Button insert;
    RecyclerView DataGuru;
    String tglLahir,jenisKelamin;
    String defaultPPGuru = "zeref.jpg";

    public DataGuruAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_guru_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nip = (EditText)view.findViewById(R.id.etNIPGuruAdmin);
        password = (EditText)view.findViewById(R.id.etPasswordGuruAdmin);
        nama = (EditText)view.findViewById(R.id.etNamaGuruAdmin);
        TempatLahir = (EditText)view.findViewById(R.id.etTempatLahirGuruAdmin);
        tgl = (EditText) view.findViewById(R.id.etTglLahirGuruAdmin);
        bulan = (EditText)view.findViewById(R.id.etBulanLahirGuruAdmin);
        tahun = (EditText)view.findViewById(R.id.etTahunLahirGuruAdmin);
        agama = (EditText)view.findViewById(R.id.etAgamaGuruAdmin);
        notelp = (EditText)view.findViewById(R.id.etTeleponGuruAdmin);
        jabatan = (EditText)view.findViewById(R.id.etJabatanGuruAdmin);
        pendidikan = (EditText)view.findViewById(R.id.etPendidikanGuruAdmin);
        jk = (Spinner) view.findViewById(R.id.SpinnerKelamin);
        alamat = (EditText)view.findViewById(R.id.etAlamatGuruAdmin);
        insert = (Button)view.findViewById(R.id.btnInputDataGuruAdmin);
        DataGuru = (RecyclerView)view.findViewById(R.id.recyclerDataGuruAdmin);

        List<String> JK = new ArrayList<>();
        JK.add(0,"Pilih Jenis Kelamin");
        JK.add("Pria");
        JK.add("Wanita");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jk.setAdapter(dataAdapter);
        jk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Jenis Kelamin")){
                    jenisKelamin = "";
                }else{
                    jenisKelamin = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Jenis Kelamin = "+jenisKelamin+" Tanggal Lahir = "+getTanggal(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private String getTanggal(){
        String tanggal = tahun.getText().toString()+"-"+bulan.getText().toString()+"-"+tgl.getText().toString();
        return tanggal;
    }
    private void insertGuru(){
        String NIP = nip.getText().toString();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        //Call<ResponseModel> checkNilaiSiswa = api.checkNilaiSiswa();
    }
}
