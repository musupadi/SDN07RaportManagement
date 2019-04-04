package com.destinyapp.aplikasisdn07.Guru.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterKelasSpinner extends ArrayAdapter<DataModel> {

    public AdapterKelasSpinner(Context context, List<DataModel> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_kelas_row, parent, false
            );
        }

        TextView textViewKelas = convertView.findViewById(R.id.tvKelasSiswaGuru);

        DataModel currentItem = getItem(position);

        if (currentItem != null) {
            textViewKelas.setText(currentItem.getNama_kelas());
        }

        return convertView;
    }

}
