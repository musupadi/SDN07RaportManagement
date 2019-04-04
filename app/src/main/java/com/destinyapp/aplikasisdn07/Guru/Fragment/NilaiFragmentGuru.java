package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNIS;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
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
public class NilaiFragmentGuru extends Fragment {

    Spinner Kelas;
    AutoCompleteTextView NIS,NamaMapel;
    EditText NamaSiswa,idMapel,isiNilai,sakit,izin,alpa;
    private List<DataModel> aList = new ArrayList<>();
    private AdapterKelasSpinner aSpinner;

    public NilaiFragmentGuru() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nilai_fragment_guru, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Kelas = (Spinner)view.findViewById(R.id.SpinnerKelasPenilaianGuru);
        NIS = (AutoCompleteTextView)view.findViewById(R.id.ACTNISPenilaianGuru);
        NamaSiswa = (EditText) view.findViewById(R.id.tvNamaSiswaPenilaianGuru);
        NamaMapel = (AutoCompleteTextView) view.findViewById(R.id.ACTNamaPelajaranPenilaianGuru);
        idMapel = (EditText)view.findViewById(R.id.tvIdPelajaranPenilaianGuru);
        isiNilai = (EditText)view.findViewById(R.id.etIsiNilaiGuru);
        sakit = (EditText)view.findViewById(R.id.etBanyakSakitGuru);
        izin = (EditText)view.findViewById(R.id.etBanyakIzinGuru);
        alpa = (EditText)view.findViewById(R.id.etBanyakAlpaGuru);
        getKelas();
        aSpinner = new AdapterKelasSpinner(getActivity(),aList);
        Kelas.setAdapter(aSpinner);

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
                }
            }
        });
        NamaMapel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String mapel = NamaMapel.getEditableText().toString();
                    getIDMapel(mapel);
                }
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
                getMapel(tingkatKelas);
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
    private void getMapel(String tingkatKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getMapel(tingkatKelas);
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                aList=response.body().getResult();
                AdapterNamaMapel adapter = new AdapterNamaMapel(getActivity(),aList);
                NamaMapel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getMapel Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getIDMapel(final String ID){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDMapel = api.getIDMapel(ID);
        getIDMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                idMapel.setText(response.body().getId_mapel());
                String nis = NIS.getEditableText().toString();
                AutoNilai(nis,response.body().getId_mapel());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getIDMapel Method",Toast.LENGTH_SHORT).show();
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
    private void kosong(){
        NIS.setText("");
        NamaSiswa.setText("");
        NamaMapel.setText("");
        idMapel.setText("");
        isiNilai.setText("");
        sakit.setText("");
        izin.setText("");
        alpa.setText("");
    }
}
