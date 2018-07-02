package com.akhil.craftsbeer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akhil.craftsbeer.DataModel.Beer;
import com.akhil.craftsbeer.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class CustomListAdapter extends BaseAdapter implements Filterable {

    private List<Beer> tempList;
    private List<Beer> mainList;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(List<Beer> workbenchList) {
        this.tempList = workbenchList;
        this.mainList = workbenchList;
    }

    @Override
    public int getCount() {
        return mainList.size();
    }

    @Override
    public Object getItem(int i) {
        return mainList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {

            layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.updateObject((Beer) getItem(i));
        return view;
    }

    private class ViewHolder {

        private TextView ounce;
        private TextView name;
        private TextView style;
        private TextView abv;
        private ImageButton add;
        private View convertView;

        private ViewHolder(View convertView) {
            this.convertView = convertView;
            ounce = (TextView) convertView.findViewById(R.id.list_item_ounce);
            name = (TextView) convertView.findViewById(R.id.list_item_name);
            style = (TextView) convertView.findViewById(R.id.list_item_style);
            abv = (TextView) convertView.findViewById(R.id.list_item_abv);
            add = (ImageButton) convertView.findViewById(R.id.list_item_add);
        }


        private void updateObject(Beer beer) {
            if (beer.getOunces().length() < 4) {
                ounce.setText("0" + beer.getOunces());
            } else {
                ounce.setText(beer.getOunces());
            }
            name.setText(beer.getName());
            style.setText(beer.getStyle());
            abv.setText("Alcohol content: " + beer.getAbv());
            if (beer.isAddedTocart() == 1) {
                add.setImageResource(R.drawable.remove);
            } else {
                add.setImageResource(R.drawable.add);
            }

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add.setImageResource(R.drawable.remove);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {

        return new CustomFilter();

    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            if (charSequence != null && charSequence.length() >= 2) {
                String filterString = charSequence.toString();
                List<Beer> filterList = new ArrayList<>();
                for (int i = 0; i < tempList.size(); i++) {
                    if (tempList.get(i).getName().toLowerCase().contains(filterString.toLowerCase())) {
                        Beer beer = new Beer(tempList.get(i).getAbv(), tempList.get(i).getIbu(), tempList.get(i).getId(), tempList.get(i).getName(), tempList.get(i).getStyle(),
                                tempList.get(i).getOunces(), tempList.get(i).isAddedTocart());
                        filterList.add(beer);
                    }
                    filterResults.count = filterList.size();
                    filterResults.values = filterList;
                }
            } else {
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mainList = (List<Beer>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
