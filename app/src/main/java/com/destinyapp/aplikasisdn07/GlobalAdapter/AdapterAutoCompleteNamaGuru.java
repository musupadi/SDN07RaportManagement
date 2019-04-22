package com.destinyapp.aplikasisdn07.GlobalAdapter;

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

public class AdapterAutoCompleteNamaGuru extends ArrayAdapter<DataModel> {
    private List<DataModel> dmListFull;

    public AdapterAutoCompleteNamaGuru(@NonNull Context context, @NonNull List<DataModel> countryList) {
        super(context, 0, countryList);
        dmListFull = new ArrayList<>(countryList);
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
        TextView textViewNIP = convertView.findViewById(R.id.tvNIPGuru);

        DataModel dm = getItem(position);

        if (dm != null) {
            textViewName.setText(dm.getNama());
            textViewNIP.setText(dm.getNip());
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
                    if (item.getNama().toLowerCase().contains(filterPattern)) {
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
            return ((DataModel) resultValue).getNama();
        }
    };
}

