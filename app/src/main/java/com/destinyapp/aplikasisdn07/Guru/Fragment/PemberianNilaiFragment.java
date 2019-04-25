package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
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
public class PemberianNilaiFragment extends Fragment {

    DB_Helper dbHelper;
    String User = "";
    private List<DataModel> mItems = new ArrayList<>();
    AutoCompleteTextView autoCompleteMapel;
    TextView NamaSiswa,NamaKelas,NomorIndukSiswa,idMapel;
    Button insert;
    EditText isiNilai;
    public PemberianNilaiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemberian_nilai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        autoCompleteMapel = (AutoCompleteTextView)view.findViewById(R.id.ACTPelajaranGuru);
        NamaSiswa = (TextView)view.findViewById(R.id.tvNamaSiswaPenilaianGuru);
        NamaKelas = (TextView)view.findViewById(R.id.tvNamaKelasPenilaianGuru);
        NomorIndukSiswa = (TextView)view.findViewById(R.id.tvNISPenilaianGuru);
        insert = (Button)view.findViewById(R.id.btnNilaiBeriGuru);
        isiNilai = (EditText)view.findViewById(R.id.etIsiNilai);
        String idNama = this.getArguments().getString("KEY_NAMA").toString();
        final String idKelas = this.getArguments().getString("KEY_ID_KELAS").toString();
        final String tingkatKelas = this.getArguments().getString("KEY_TINGKAT_KELAS").toString();
        final String nis = this.getArguments().getString("KEY_NIS").toString();
        NomorIndukSiswa.setText(tingkatKelas);
        NamaSiswa.setText(idNama);
        Toast.makeText(getActivity(),tingkatKelas,Toast.LENGTH_SHORT).show();

        //Logic
        getNamaKelas(idKelas);
        getMapelAutoText(idKelas);
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();

        while (cursor.moveToNext()){
            User=cursor.getString(0);
        }
        autoCompleteMapel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getIDMapel(idKelas,nis,"Auto");
                }
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIDMapel(idKelas,nis,"UI");
                //checkData(nis);
            }
        });
    }
    private void getMapelAutoText(String idKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getMapel(idKelas);
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterNamaMapel adapter = new AdapterNamaMapel(getActivity(),mItems);
                autoCompleteMapel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaKelas(String idKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNamaKelas = api.getNamaKelas(idKelas);
        getNamaKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String kelas = response.body().getNama_kelas();
                NamaKelas.setText(kelas);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada getNamaKelas",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getIDMapel(final String idKelas, final String nis, final String Checker){
        String nama_mapel=autoCompleteMapel.getEditableText().toString();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDMapel = api.getIDMapel(nama_mapel);
        getIDMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String IDMapel = response.body().getId_mapel();
                if(Checker.equals("UI")){
                    checkData(idKelas,nis,IDMapel);
                }else{
                    AutoNilai(nis,IDMapel);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada getIDMapel",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void checkData(final String idKelas,final String nis, final String id_mapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> checkNilaiSiswa = api.checkNilaiSiswa(nis,id_mapel);
        checkNilaiSiswa.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Insert")){
                    insertData(idKelas,nis,User,id_mapel);
                }else{
                    updateData(idKelas,nis,User,id_mapel);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di Check Data Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void insertData(final String idKelas, final String nis, final String nip, String id_mapel){
        String nilai = isiNilai.getText().toString();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertNilai = api.insertNilaiSiswa(nis,nip,nilai,id_mapel,"belum");
        insertNilai.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Insert")){
                    Toast.makeText(getActivity(),"Data Berhasil Terinput",Toast.LENGTH_SHORT).show();
                    Succes(idKelas);
                }else{
                    Toast.makeText(getActivity(),"Data Gagal Terinput",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateData(final String idKelas, final String nis, final String nip, String id_mapel){
        String nilai = isiNilai.getText().toString();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> updateNilai = api.updateNilaiSiswa(nis,nilai,id_mapel);
        updateNilai.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Update")){
                    Toast.makeText(getActivity(),"Data Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    Succes(idKelas);
                }else{
                    Toast.makeText(getActivity(),"Data Gagal Terupdate",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di Update Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AutoNilai(String nis,String IDMapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNilai = api.GetNilai(nis,IDMapel);
        getNilai.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Succes")){
                    isiNilai.setText(response.body().getNilai());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di AutoNilai",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Succes(String idKelas){
        Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
        goInput.putExtra("id_kelas",idKelas);
        goInput.putExtra("Kelas","kelas");
        getActivity().startActivities(new Intent[]{goInput});
    }
}
