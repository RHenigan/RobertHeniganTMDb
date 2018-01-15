package com.rhenigan.roberthenigantmdb.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//generated using http://www.jsonschema2pojo.org/

//Stores the Dates fields from Upcoming Movies and Now Playing Movies

public class Dates {

    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("minimum")
    @Expose
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

}
