package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainGuruAdmin extends Fragment {

    LinearLayout InputData,GetData;

    public MainGuruAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_guru_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputData = (LinearLayout)view.findViewById(R.id.inputData);
        GetData = (LinearLayout)view.findViewById(R.id.checkData);

        InputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input();
            }
        });
        GetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Output();
            }
        });
    }
    private void Input(){
        Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
        goInput.putExtra("INPUT_GURU","input_guru");
        getActivity().startActivities(new Intent[]{goInput});
    }
    private void Output(){
        Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
        goInput.putExtra("OUTPUT_GURU","output_guru");
        getActivity().startActivities(new Intent[]{goInput});
    }
}
