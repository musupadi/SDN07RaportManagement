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

import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputAdminDashboard extends Fragment {

    Button guru,mapel,kelas,jadwal,siswa;

    public InputAdminDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_admin_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Variable Declaration
        guru = (Button)view.findViewById(R.id.btnGuru);
        siswa = (Button)view.findViewById(R.id.btnSiswa);
        mapel = (Button)view.findViewById(R.id.btnMapel);
        kelas = (Button)view.findViewById(R.id.btnKelas);
        jadwal = (Button)view.findViewById(R.id.btnJadwal);

        //ClickListener
        guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("INPUT_GURU","input_guru");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("INPUT_SISWA","input_siswa");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        mapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("INPUT_MAPEL","input_mapel");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("INPUT_KELAS","input_kelas");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("INPUT_JADWAL","input_jadwal");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
    }
}
