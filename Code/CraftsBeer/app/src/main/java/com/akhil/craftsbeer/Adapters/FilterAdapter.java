package com.akhil.craftsbeer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.akhil.craftsbeer.R;

import java.util.ArrayList;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class FilterAdapter extends ArrayAdapter<String> {

    private ArrayList<String> data;
    private Context context;

    public FilterAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.filter_item, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        CheckBox checkbox;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.settingCheckbox);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        if (data.contains(viewHolder.checkbox.getText())) {

        }
        return result;
    }
}
