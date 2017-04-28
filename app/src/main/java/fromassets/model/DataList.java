package fromassets.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1033826 on 4/26/2017.
 */

public class DataList {


    //region dataobjects
    @SerializedName("pointList")
    @Expose
    private ArrayList<PointList> pointList;

    @SerializedName("option")
    @Expose
    private String option;
    //endregion




    //region getters_setters
    public ArrayList<PointList> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<PointList> pointList) {
        this.pointList = pointList;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


    //endregion










}
