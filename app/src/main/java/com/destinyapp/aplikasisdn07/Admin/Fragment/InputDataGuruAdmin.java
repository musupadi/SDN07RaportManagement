package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
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
public class InputDataGuruAdmin extends Fragment {

    EditText nip,password,nama,TempatLahir,agama,notelp,jabatan,pendidikan,alamat;
    Spinner jk,tgl,bulan,tahun;
    Button insert;
    String jenisKelamin;
    String defaultPPGuru = "logo.png";
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;


    public InputDataGuruAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data_guru_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nip = (EditText)view.findViewById(R.id.etNIP);
        password = (EditText)view.findViewById(R.id.etPassword);
        nama = (EditText)view.findViewById(R.id.etNamaGuru);
        TempatLahir = (EditText)view.findViewById(R.id.etTempatLahir);
        tgl = (Spinner)view.findViewById(R.id.spinnerTglLahir);
        bulan = (Spinner)view.findViewById(R.id.spinnerBulanLahir);
        tahun = (Spinner) view.findViewById(R.id.spinnerTahunLahir);
        agama = (EditText)view.findViewById(R.id.etAgama);
        notelp = (EditText)view.findViewById(R.id.etTelepon);
        jabatan = (EditText)view.findViewById(R.id.etJabatan);
        pendidikan = (EditText)view.findViewById(R.id.etPendidikan);
        jk = (Spinner) view.findViewById(R.id.SpinnerKelamin);
        alamat = (EditText)view.findViewById(R.id.etAlamat);
        insert = (Button)view.findViewById(R.id.btnInput);

        //getAllGuru();

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
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGuru();
            }
        });
    }
    private String getTanggal(){
        String tanggal = tahun.getSelectedItem().toString()+"-"+bulan.getSelectedItem().toString()+"-"+tgl.getSelectedItem().toString();
        return tanggal;
    }
    private void insertGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertDataGuru = api.insrertDataGuru(nip.getText().toString(),
                password.getText().toString(),
                nama.getText().toString(),
                TempatLahir.getText().toString(),
                getTanggal(),
                agama.getText().toString(),
                notelp.getText().toString(),
                jabatan.getText().toString(),
                pendidikan.getText().toString(),
                jenisKelamin,
                defaultPPGuru,
                alamat.getText().toString());
        insertDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(getActivity(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("OUTPUT_GURU","input");
                getActivity().startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam Method InsertData",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
