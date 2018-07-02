package com.akhil.craftsbeer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akhil.craftsbeer.DataModel.Beer;
import com.akhil.craftsbeer.Properties.ApplicationProperties;
import com.akhil.craftsbeer.R;

import org.w3c.dom.Text;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class BeerDetailsFragment extends Fragment {

    TextView name;
    TextView style;
    TextView alcohol;
    TextView bitterness;
    TextView ounces;
    TextView id;
    Button addtocart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beer_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (TextView) view.findViewById(R.id.nameValue);
        style = (TextView) view.findViewById(R.id.styleValue);
        alcohol = (TextView) view.findViewById(R.id.alcoholValue);
        bitterness = (TextView) view.findViewById(R.id.bitternessValue);
        ounces = (TextView) view.findViewById(R.id.ouncesValue);
        id = (TextView) view.findViewById(R.id.idValue);
        addtocart = (Button) view.findViewById(R.id.addtocart);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setTitle("Beer Details");
        final Beer selectdBeer = getArguments().getParcelable("beer");

        name.setText(selectdBeer.getName());
        style.setText(selectdBeer.getStyle());
        alcohol.setText(selectdBeer.getAbv());
        if (!selectdBeer.getIbu().isEmpty()) {
            bitterness.setText(selectdBeer.getIbu());
        } else {
            bitterness.setText("Not Applicable");
        }
        ounces.setText(selectdBeer.getOunces());
        id.setText(selectdBeer.getId());
        if (selectdBeer.isAddedTocart() == 1) {
            addtocart.setText("Remove from cart");
        } else {
            addtocart.setText("Add to cart");
        }

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectdBeer.isAddedTocart() == 0) {
                    selectdBeer.setAddedTocart(1);
                    addtocart.setText("Remove from cart");
                    for (int i = 0; i < ApplicationProperties.getInstance().getBeerList().size(); i++) {
                        if (ApplicationProperties.getInstance().getBeerList().get(i).getId().equals(selectdBeer.getId())) {
                            ApplicationProperties.getInstance().getBeerList().get(i).setAddedTocart(1);
                            ApplicationProperties.getInstance().getCartList().add(ApplicationProperties.getInstance().getBeerList().get(i));
                        }
                    }
                    Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    selectdBeer.setAddedTocart(0);
                    addtocart.setText("Add to cart");
                    for (int i = 0; i < ApplicationProperties.getInstance().getBeerList().size(); i++) {
                        if (ApplicationProperties.getInstance().getBeerList().get(i).getId().equals(selectdBeer.getId())) {
                            ApplicationProperties.getInstance().getBeerList().get(i).setAddedTocart(0);
                        }
                    }
                    for (int i = 0; i < ApplicationProperties.getInstance().getCartList().size(); i++) {
                        if (ApplicationProperties.getInstance().getCartList().get(i).getId().equals(selectdBeer.getId())) {
                            ApplicationProperties.getInstance().getCartList().remove(i);
                        }
                    }
                    Toast.makeText(getActivity(), "Removed from cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().setTitle("Crafts Beer");
    }
}
