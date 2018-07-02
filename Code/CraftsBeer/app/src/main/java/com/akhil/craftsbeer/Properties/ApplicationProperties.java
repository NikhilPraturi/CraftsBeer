package com.akhil.craftsbeer.Properties;

import com.akhil.craftsbeer.DataModel.Beer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class ApplicationProperties {

    private static ApplicationProperties Instance = null;

    private List<Beer> beerList = new ArrayList<>();

    private List<Beer> cartList = new ArrayList<>();

    public static ApplicationProperties getInstance() {
        if (Instance == null) {
            Instance = new ApplicationProperties();
        }
        return Instance;
    }

    public List<Beer> getBeerList() {
        return beerList;
    }

    public List<Beer> getCartList() {
        return cartList;
    }
}
