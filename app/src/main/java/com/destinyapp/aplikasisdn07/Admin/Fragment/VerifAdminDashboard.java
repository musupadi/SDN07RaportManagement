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
public class VerifAdminDashboard extends Fragment {

    Button SudahAbsen,BelumAbsen,SudahNilai,BelumNilai;
    public VerifAdminDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verif_admin_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SudahAbsen = (Button)view.findViewById(R.id.btnSudahAbsen);
        BelumAbsen = (Button)view.findViewById(R.id.btnBelumAbsen);
        SudahNilai = (Button)view.findViewById(R.id.btnSudahNilai);
        BelumNilai = (Button)view.findViewById(R.id.btnBelumNilai);

        SudahAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("ABSEN","sudah");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        BelumAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("ABSEN","belum");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
    }
}
