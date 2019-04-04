package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.destinyapp.aplikasisdn07.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NilaiFragmentGuru extends Fragment {


    public NilaiFragmentGuru() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nilai_fragment_guru, container, false);
    }


}
