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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNama;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNip;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
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
public class DataJadwalAdmin extends Fragment {

    Spinner Kelas,Hari,dari_jam,sampai_jam;
    AutoCompleteTextView NamaGuru,NIP,NamaMapel;
    Button insert;
    String id_kelas,idMapel,hari,dariJam,sampaiJam,tingkatKelas;
    private List<DataModel> mItems = new ArrayList<>();
    private AdapterKelasSpinner aSpinner;

    public DataJadwalAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_jadwal_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NamaGuru = (AutoCompleteTextView) view.findViewById(R.id.etNamaGuruJadwalAdmin);
        NIP = (AutoCompleteTextView)view.findViewById(R.id.etNipGuruJadwalAdmin);
        NamaMapel = (AutoCompleteTextView)view.findViewById(R.id.etNamaMapelJadwalAdmin);
        Kelas = (Spinner)view.findViewById(R.id.SpinnerKelasJadwalAdmin);
        Hari = (Spinner)view.findViewById(R.id.SpinnerHariJadwalAdmin);
        dari_jam = (Spinner)view.findViewById(R.id.SpinnerDariJamJadwalAdmin);
        sampai_jam = (Spinner)view.findViewById(R.id.SpinnerSampaiJamJadwalAdmin);
        insert = (Button)view.findViewById(R.id.btnInputDataJadwalAdmin);

        getNamaGuruAutoText();
        getNipGuruAutoText();
        getSpinnerKelas();
        getHariSpinner();
        getDariSpinner();
        getSampaiSpinner();
        ClickedListener();

        NamaGuru.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getNipGuru();
                }
            }
        });

        NIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getNamaGuru();
                }
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIDMapel();
                insertData();
            }
        });
    }
    private void getSpinnerKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetKelas = api.getKelas();
        GetKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterKelasSpinner adapter = new AdapterKelasSpinner(getActivity(),mItems);
                Kelas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getHariSpinner(){
        List<String> TK = new ArrayList<>();
        TK.add(0,"Pilih Hari Jadwal");
        TK.add("Senin");
        TK.add("Selasa");
        TK.add("Rabu");
        TK.add("Kamis");
        TK.add("Jum'at");
        TK.add("Sabtu");
        TK.add("Minggu");
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,TK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hari.setAdapter(dataAdapter);
    }
    private void getDariSpinner(){
        List<String> TK = new ArrayList<>();
        TK.add(0,"Pilih Dari Jam");
        TK.add("08:00");
        TK.add("10:00");
        TK.add("12:00");
        TK.add("14:00");
        TK.add("16:00");
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,TK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dari_jam.setAdapter(dataAdapter);
    }
    private void getSampaiSpinner(){
        List<String> TK = new ArrayList<>();
        TK.add(0,"Pilih Sampai Jam");
        TK.add("08:00");
        TK.add("10:00");
        TK.add("12:00");
        TK.add("14:00");
        TK.add("16:00");
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,TK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sampai_jam.setAdapter(dataAdapter);
    }
    private void getMapelAutoText(String tingkatKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getMapel(tingkatKelas);
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterNamaMapel adapter = new AdapterNamaMapel(getActivity(),mItems);
                NamaMapel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getGuru = api.getGuru("",NIP.getEditableText().toString());
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (NamaGuru.getEditableText().toString().isEmpty()){
                    NamaGuru.setText(response.body().getNama());
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNipGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getGuru = api.getGuru(NamaGuru.getEditableText().toString(),"");
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (NIP.getEditableText().toString().isEmpty()){
                    NIP.setText(response.body().getNip());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNipGuruAutoText(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllDataGuru();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterGuruAutoNip adapter = new AdapterGuruAutoNip(getActivity(),mItems);
                NamaGuru.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaGuruAutoText(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllDataGuru();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterGuruAutoNama adapter = new AdapterGuruAutoNama(getActivity(),mItems);
                NamaGuru.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetIDKelas(String NamaKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDKelas = api.GetIDKelas(NamaKelas);
        getIDKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                id_kelas = response.body().getId_kelas();
                tingkatKelas = response.body().getTingkat_kelas();
                getMapelAutoText(tingkatKelas);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam GetIDKelas",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ClickedListener(){
        Kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                String clickedItemNamaKelas = clickedItem.getNama_kelas();
                tingkatKelas=clickedItem.getTingkat_kelas();
                GetIDKelas(clickedItemNamaKelas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Hari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Hari Jadwal")){
                    hari = "Pilih Hari Jadwal";
                }else{
                    hari = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dari_jam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Dari Jam")){
                    dariJam = "Pilih Dari Jam";
                }else{
                    dariJam = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dari_jam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Sampai Jam")){
                    sampaiJam = "Pilih Sampai Jam";
                }else{
                    sampaiJam = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void insertData(){
        getIDMapel();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Insert = api.insertDataJadwal(NIP.getEditableText().toString(),
                id_kelas,
                idMapel,
                hari,
                dariJam,
                sampaiJam);
        Insert.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Insert")){
                    Toast.makeText(getActivity(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Terjadi Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada method InsertData",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getIDMapel(){
        String nama_mapel=NamaMapel.getEditableText().toString();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDMapel = api.getIDMapel(nama_mapel);
        getIDMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                idMapel = response.body().getId_mapel();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada getIDMapel",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
