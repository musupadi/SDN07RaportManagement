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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterAutoCompleteNIP;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterAutoCompleteNamaGuru;
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
public class InputDataJadwal extends Fragment {

    AutoCompleteTextView mapel,nama,nip;
    Spinner hari,kelas,dariJam,sampaiJam;
    Button Insert;
    private List<DataModel> mItems = new ArrayList<>();
    private AdapterKelasSpinner aSpinner;
    String idKelas;

    public InputDataJadwal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nip = (AutoCompleteTextView) view.findViewById(R.id.etNIP);
        nama = (AutoCompleteTextView)view.findViewById(R.id.etNamaGuru);
        mapel = (AutoCompleteTextView)view.findViewById(R.id.etNamaMapel);
        hari = (Spinner)view.findViewById(R.id.spinnerHari);
        kelas = (Spinner)view.findViewById(R.id.spinnerKelas);
        Insert = (Button)view.findViewById(R.id.btnInput);
        dariJam = (Spinner)view.findViewById(R.id.spinnerDariJam);
        sampaiJam = (Spinner)view.findViewById(R.id.spinnerSampaiJam);
        String UPDATE = this.getArguments().getString("KEY_UPDATE").toString();
        final String IDJADWAL = this.getArguments().getString("KEY_JADWAL").toString();
        getKelas();
        getMapel();
        getAutoNIP();
        getAutoNamaGuru();
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
        nip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getAuto();
                }
            }
        });
        nama.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getAuto();
                }
            }
        });
        if (UPDATE.equals("Update")){
            UpdateJadwal(IDJADWAL);
            Insert.setText("Update");
            Insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Mapel = mapel.getEditableText().toString();
                    getIDMapel(Mapel,"Update",IDJADWAL);
                }
            });
        }else{
            Insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Mapel = mapel.getEditableText().toString();
                    getIDMapel(Mapel,"Input","");
                }
            });
        }

    }
    private void UpdateData(String IDjadwal,String idMapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> updateDataJadwal = api.UpdateJadwal(IDjadwal,
                nip.getText().toString(),
                idKelas,
                idMapel,
                hari.getSelectedItem().toString(),
                dariJam.getSelectedItem().toString(),
                sampaiJam.getSelectedItem().toString()
        );
        updateDataJadwal.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getResponse().equals("Update")){
                    Toast.makeText(getActivity(),"Data Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                    goInput.putExtra("OUTPUT_JADWAL","output_jadwal");
                    getActivity().startActivities(new Intent[]{goInput});
                }else if(response.body().getResponse().equals("error")){
                    Toast.makeText(getActivity(),"Data Update Error",Toast.LENGTH_SHORT).show();
                }else if(response.body().getResponse().equals("Insert")){
                    Toast.makeText(getActivity(),"Data Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UpdateJadwal(String idJadwal){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getData = api.getDataJadwal(idJadwal);
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                nip.setText(response.body().getNip());
                nama.setText(response.body().getNama());
                mapel.setText(response.body().getNama_mapel());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAuto(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getData= api.getGuruComplete(nama.getEditableText().toString(),nip.getEditableText().toString());
        getData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                nama.setText(response.body().getNama());
                nip.setText(response.body().getNip());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void InputData(String idMapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertDataJadwal = api.insertDataJadwal(nip.getText().toString(), idKelas,idMapel,
                hari.getSelectedItem().toString(), dariJam.getSelectedItem().toString(),
                sampaiJam.getSelectedItem().toString()
                );
        insertDataJadwal.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getResponse().equals("Insert")){
                    Toast.makeText(getActivity(),"Data Berhasil Terinput",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                    goInput.putExtra("OUTPUT_JADWAL","output_jadwal");
                    getActivity().startActivities(new Intent[]{goInput});
                }else{
                    Toast.makeText(getActivity(),"Data Jadwal Sudah Teriisi",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Gagal silahkan coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getIDMapel(final String ID, final String InputUpdate,final String idJadwal){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDMapel = api.getIDMapel(ID);
        getIDMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String idMapel= response.body().getId_mapel();
                if(InputUpdate.equals("Input")){
                    InputData(idMapel);
                }else if(InputUpdate.equals("Update")){
                    UpdateData(idJadwal,idMapel);
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Gagal silahkan coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoNamaGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllDataGuru();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterAutoCompleteNamaGuru adapter = new AdapterAutoCompleteNamaGuru(getActivity(),mItems);
                nama.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getMapel Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoNIP(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllDataGuru();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterAutoCompleteNIP adapter = new AdapterAutoCompleteNIP(getActivity(),mItems);
                nip.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getMapel Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getMapel(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllMapel();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterNamaMapel adapter = new AdapterNamaMapel(getActivity(),mItems);
                mapel.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getMapel Method",Toast.LENGTH_SHORT).show();
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
}
