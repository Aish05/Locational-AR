package fromassets.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1033826 on 4/26/2017.
 */

public class PointList{

   /* public PointList(LocationObj location) {
        this.location = location;
    }*/

    //region dataobjects
    @SerializedName("location")
    @Expose
    private LocationObj location;

    @SerializedName("name")
    @Expose
    private String name;

    public PointList(LocationObj location, String placeName, int i) {
        this.name = placeName;
    }

    public PointList(double lat, double lon, String placeName, int i) {
     /*   lat =  location.getLocation().getLatitude();
        lon =  location.getLocation().getLongitude();*/

    }


    //endregion


    //region gettersandsetters


    public Location getLocation() {
        return location.getLocation();
    }

    public LocationObj getLocationObject() {
        return location;
    }

    public void setLocation(double lat, double lng) {
         location.setLocation(lat,lng);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    //endregion



}
