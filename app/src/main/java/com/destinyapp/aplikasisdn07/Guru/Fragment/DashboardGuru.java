package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Siswa.MainSiswaActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardGuru extends Fragment {
    Button Jadwal,Absen,Nilai,Raport;

    public DashboardGuru() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_guru, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Jadwal = (Button)view.findViewById(R.id.btnJadwal);
        Absen = (Button)view.findViewById(R.id.btnAbsen);
        Nilai = (Button)view.findViewById(R.id.btnPenilaian);
        Raport = (Button)view.findViewById(R.id.btnRaport);

        Jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
                goInput.putExtra("JADWAL","jadwal");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        Absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
                goInput.putExtra("ABSEN","absen");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        Nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
                goInput.putExtra("NILAI","NILAI");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        Raport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
                goInput.putExtra("RAPORT","raport");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
    }
}
