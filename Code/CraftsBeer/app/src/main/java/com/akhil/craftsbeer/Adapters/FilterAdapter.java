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
import java.util.List;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class FilterAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public FilterAdapter(List<String> data) {
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
        private View convertView;

        private ViewHolder(View convertView){
            this.convertView = convertView;
            checkbox = (CheckBox) convertView.findViewById(R.id.settingCheckbox);
        }

        public void updateObject(String beerType){
            checkbox.setText(beerType);
        }
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {

            layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(R.layout.filter_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.updateObject(getItem(i));
        return view;
    }
}
