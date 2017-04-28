package fromassets.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 1033826 on 4/26/2017.
 */

public class LocationObj implements Parcelable {

    double lat;
    double lng;
    double altitude;
    Location location;



    public void setLocation(double lat, double lng) {
        location = new Location("LocationObj");
        location.setLatitude(lat);
        location.setLongitude(lng);
        //location.setAltitude(lat);
    }

    public Location getLocation() {
        return location;
    }

     protected LocationObj(Parcel in) {
            lat = in.readDouble();
            lng = in.readDouble();
            altitude = in.readDouble();
        }
    public static final Creator<LocationObj> CREATOR = new Creator<LocationObj>() {
        @Override
        public LocationObj createFromParcel(Parcel in) {
            return new LocationObj(in);
        }

        @Override
        public LocationObj[] newArray(int size) {
            return new LocationObj[size];
        }
    };


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeDouble(altitude);
    }
}
