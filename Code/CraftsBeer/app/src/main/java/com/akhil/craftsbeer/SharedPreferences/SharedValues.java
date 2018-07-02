package com.akhil.craftsbeer.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.akhil.craftsbeer.DataModel.Beer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class SharedValues {
    private static SharedValues INSTANCE = null;

    private SharedPreferences m_sharedPreferences;
    private SharedPreferences.Editor m_sharedPreferencesEditor;

    private SharedValues() {

    }

    public static SharedValues getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new SharedValues();
        return INSTANCE;
    }

    public void setContext(Context a_context) {
        if (a_context != null) {
            m_sharedPreferences = a_context.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        }
    }

    public List<Beer> getBeerCartList() {
        String playJSONString = m_sharedPreferences.getString("CATEGORIES", null);
        Type type = new TypeToken<List<Beer>>() {
        }.getType();
        List<Beer> beers = new Gson().fromJson(playJSONString, type);
        return beers;
    }

    public void setBeerCartList(List<Beer> categoryList) {
        m_sharedPreferencesEditor = m_sharedPreferences.edit();
        String playJSOnString = new Gson().toJson(categoryList);
        m_sharedPreferencesEditor.putString("CATEGORIES", playJSOnString);
        m_sharedPreferencesEditor.commit();
    }
}
