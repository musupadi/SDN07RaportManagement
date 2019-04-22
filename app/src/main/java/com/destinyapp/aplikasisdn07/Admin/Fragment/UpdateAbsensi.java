package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateAbsensi extends Fragment {

    EditText sakit,izin,alpa;
    Button input;
    public UpdateAbsensi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_absensi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sakit=(EditText)view.findViewById(R.id.etSakit);
        izin=(EditText)view.findViewById(R.id.etIzin);
        alpa=(EditText)view.findViewById(R.id.etAlpa);
        input=(Button)view.findViewById(R.id.btnInsert);
        final String ABSENSI = this.getArguments().getString("ABSENSI").toString();
        final String NIS = this.getArguments().getString("NIS").toString();

        if (ABSENSI.equals("update")){
            input.setText("Update Data");
            getDataAbsen(NIS);
        }
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ABSENSI.equals("update")){
                    UpdateDataAbsensi(NIS);
                }else{

                }
            }
        });
    }
    private void UpdateDataAbsensi(String NIS){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAbsen = api.updateDataAbsen(NIS,sakit.getText().toString(),
                izin.getText().toString(),alpa.getText().toString());
        GetAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(getActivity(),"Data Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("ABSEN","sudah");
                getActivity().startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDataAbsen(String NIS){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAbsen = api.GetAbsenSiswa(NIS);
        GetAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                sakit.setText(response.body().getSakit());
                izin.setText(response.body().getIzin());
                alpa.setText(response.body().getAlpa());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
}
