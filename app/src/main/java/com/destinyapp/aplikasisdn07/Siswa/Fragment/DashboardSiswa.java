package com.destinyapp.aplikasisdn07.Siswa.Fragment;


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
public class DashboardSiswa extends Fragment {

    Button CheckRaport,JadwalPelajaran;
    public DashboardSiswa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_siswa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckRaport=(Button)view.findViewById(R.id.btnNilai);
        JadwalPelajaran=(Button)view.findViewById(R.id.btnJadwal);

        CheckRaport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainSiswaActivity.class);
                goInput.putExtra("RAPORT","raport");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        JadwalPelajaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainSiswaActivity.class);
                goInput.putExtra("JADWAL","jadwal");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
    }
}
