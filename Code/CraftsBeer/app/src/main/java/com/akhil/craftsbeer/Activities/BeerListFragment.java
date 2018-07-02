package com.akhil.craftsbeer.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.akhil.craftsbeer.Adapters.CustomListAdapter;
import com.akhil.craftsbeer.DataModel.Beer;
import com.akhil.craftsbeer.Properties.ApplicationProperties;
import com.akhil.craftsbeer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class BeerListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    FragmentManager manager;
    public SearchView searchText;
    CustomListAdapter customListAdapter = null;
    ListView listView;
    View m_view;
    MainActivity mainActivity;
    List<Beer> beerList;
    ImageButton sort;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beers_list, container, false);
        m_view = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.beers_list);
        searchText = view.findViewById(R.id.searchBeers);
        sort = view.findViewById(R.id.mainActivity_imageButton_sort);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Beer> orderedList = new ArrayList<>();
                orderedList.addAll(ApplicationProperties.getInstance().getBeerList());
                Collections.sort(orderedList, new AcloholAscending());
                customListAdapter = new CustomListAdapter(beerList);
                listView.setAdapter(customListAdapter);
                listView.deferNotifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        initViews();
    }


    private void initViews() {

        searchText.setOnQueryTextListener(this);
        //filterButton.setOnClickListener(this);
        if (getArguments().getString("category").equals("all")) {
            beerList = ApplicationProperties.getInstance().getBeerList();
        } else {
            beerList = ApplicationProperties.getInstance().getCartList();
        }
        customListAdapter = new CustomListAdapter(beerList);
        listView.setAdapter(customListAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (customListAdapter != null) {
            customListAdapter.getFilter().filter(newText);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BeerDetailsFragment beerDetailsFragment = new BeerDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("beer", beerList.get(i));
        beerDetailsFragment.setArguments(bundle);
        addFragment(beerDetailsFragment, true, "one");
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction ft = manager.beginTransaction();

        /*if (mainActivity.isLargeScreen) {


            ft.replace(R.id.container_frame_right, fragment);
            ft.commit();
        } else*/
        {
            //mainActivity.resolveUpButtonWithFragmentStack();
            ft.replace(R.id.container_frame, fragment);
            mainActivity.invalidateOptionsMenu();
            // mainActivity.isFromDetails = true;
            if (addToBackStack) {
                ft.addToBackStack(tag);
            }
            ft.commitAllowingStateLoss();
        }
    }

    class AcloholAscending implements Comparator<Beer> {

        @Override
        public int compare(Beer beer, Beer t1) {
            if (Double.parseDouble(beer.getAbv()) < Double.parseDouble(t1.getAbv())) return -1;
            if (Double.parseDouble(beer.getAbv()) > Double.parseDouble(t1.getAbv())) return 1;
            return 0;
        }
    }


}
