package plaacesapi.model;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by 1033826 on 4/11/2017.
 */

public interface RetrofitMaps {

        /*
         * Retrofit get annotation with our URL
         * And our method that will return us details of student.
         */
        @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyCrlNAhsiDMeJSND2ygO9Fs7fOFfZoJHJc")
        Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}


