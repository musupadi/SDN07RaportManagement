package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterGuruAutoNip extends ArrayAdapter<DataModel> {
    private List<DataModel> dmListFull;

    public AdapterGuruAutoNip(@NonNull Context context, @NonNull List<DataModel> List) {
        super(context, 0, List);
        dmListFull = new ArrayList<>(List);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_guru_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tvNamaGuru);
        TextView textViewNip = convertView.findViewById(R.id.tvNIPGuru);

        DataModel dm = getItem(position);

        if (dm != null) {
            textViewNip.setText(dm.getNip());
            textViewName.setText(dm.getNama());
        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<DataModel> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(dmListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataModel item : dmListFull) {
                    if (item.getNip().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((DataModel) resultValue).getNip();
        }
    };
}
