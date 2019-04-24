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
public class UpdateNilai extends Fragment {

    EditText mapel,nilai;
    Button input;
    public UpdateNilai() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_nilai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapel=(EditText)view.findViewById(R.id.etNamaMapel);
        nilai=(EditText)view.findViewById(R.id.etNilai);
        input=(Button)view.findViewById(R.id.btnInsert);
        final String NILAI = this.getArguments().getString("KEY_NILAI").toString();
        final String NIS = this.getArguments().getString("NIS").toString();
        final String Mapel = this.getArguments().getString("MAPEL").toString();

        if (NILAI.equals("update")){
            input.setText("Update Data");
            getDataNilai(NIS,Mapel);
        }
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NILAI.equals("update")){
                    UpdateDataNilai(NIS,Mapel);
                }else{

                }
            }
        });
    }
    private void UpdateDataNilai(String NIS,String Mapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAbsen = api.updateDataNilai(NIS,Mapel,nilai.getText().toString());
        GetAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(getActivity(),"Data Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("NILAI","sudah");
                getActivity().startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Koneksi Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDataNilai(String NIS, final String Mapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAbsen = api.GetAbsenSiswa(NIS);
        GetAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mapel.setText(Mapel);
                nilai.setText(response.body().getNilai());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
}
