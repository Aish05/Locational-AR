package fromassets.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 1033826 on 4/26/2017.
 */

public class MainBean {

    //region dataobjects
    @SerializedName("dataList")
    @Expose
    private ArrayList<DataList> dataList;
    //endregion



    //region getters_setters
    public ArrayList<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DataList> dataList) {
        this.dataList = dataList;
    }


    //endregion









}
