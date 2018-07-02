package com.akhil.craftsbeer.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aghatiki on 7/1/2018.
 */

public class Beer implements Parcelable {

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];

        }
    };
    /***
     * "abv":"0.05", -> acohol content
     "ibu":"",   -> international bittering units
     "id":1436,   -> id
     "name":"Pub Beer",
     "style":"American Pale Lager",
     "ounces":12.0
     */
    private String abv;
    private String ibu;
    private String id;
    private String name;
    private String style;
    private String ounces;
    private int addedTocart;

    public Beer(String abv, String ibu, String id, String name, String style, String ounces, int addedTocart) {
        this.abv = abv;
        this.ibu = ibu;
        this.id = id;
        this.name = name;
        this.style = style;
        this.ounces = ounces;
        this.addedTocart = addedTocart;
    }

    protected Beer(Parcel in) {
        abv = in.readString();
        ibu = in.readString();
        id = in.readString();
        name = in.readString();
        style = in.readString();
        ounces = in.readString();
        addedTocart = in.readInt();
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getOunces() {
        return ounces;
    }

    public void setOunces(String ounces) {
        this.ounces = ounces;
    }

    public int isAddedTocart() {
        return addedTocart;
    }

    public void setAddedTocart(int addedTocart) {
        this.addedTocart = addedTocart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(abv);
        parcel.writeString(ibu);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(style);
        parcel.writeString(ounces);
        parcel.writeInt(addedTocart);
    }
}
