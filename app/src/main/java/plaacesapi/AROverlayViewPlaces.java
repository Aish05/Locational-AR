package plaacesapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.text.style.LocaleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.helper.LocationHelper;
import ng.dat.ar.model.ARPoint;
import plaacesapi.model.Example;
import retrofit.Callback;

/**
 * Created by 1033826 on 04/13/17.
 */

 /* Convert GPS coordinate (Latitude, Longitude, Altitude) to Navigation coordinate (East, North, Up), then transfer Navigation coordinate to Camera coordinate and display it on camera view. */
// ECEF -  (Earth-centered Earth-fixed coordinate):

public class AROverlayViewPlaces extends View implements View.OnTouchListener {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;


    public List<ARPoint> getArPoints() {
        return arPoints;
    }

    public void setArPoints(List<ARPoint> arPoints) {
        this.arPoints = arPoints;
    }

    private List<ARPoint> arPoints;
    float distanceInMeters;
    double dis;
    float x, y;


    public AROverlayViewPlaces(Context context) {
        super(context);

        this.context = context;


        //Demo points
       /* arPoints = new ArrayList<ARPoint>() {{
           *//* add(new ARPoint("Andheri Station", 19.119657, 72.846897, 0));
            add(new ARPoint("Marol Naka", 19.117215, 72.886691, 0));*//*

            add(new ARPoint("Block A", 19.120992, 72.857189, 0));
            add(new ARPoint("Block M", 19.120845, 72.856723, 0));
            add(new ARPoint("Block D", 19.120191, 72.857765, 0));
            add(new ARPoint("Block E", 19.119777, 72.857729, 0));
            add(new ARPoint("Block C", 19.121062, 72.857798, 0));   //19.121112, 72.857840
            //add(new ARPoint("Over Bridge", 19.1206077,72.8571426 , 0));
            add(new ARPoint("Executive Block Center", 19.120489, 72.857044, 0));
            add(new ARPoint("Suren Road", 19.118692, 72.856868, 0));
            add(new ARPoint("Tennis Court",19.118939, 72.857960, 0));
            //add(new ARPoint("GYM",19.121077, 72.858603,0));

            //19.120992, 72.857189 - block A
            // 19.120845, 72.856723 - block M
            //19.120191, 72.857765  - block D
            //19.119777, 72.857729  - block E
            //19.121062, 72.857798  - block C
            // 19.1206077,72.8571426 - Bridge
            // 19.121033, 72.858558 - Block C Gym
            //19.118692, 72.856868 - suren road
            //19.120437, 72.857020 - EBC Executive block
            // 19.120226, 72.856128 -  Gundavili Road
        }};*/
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("On draw currentLocation", "" + currentLocation);

        if (currentLocation == null) {
            return;
        }

        final int radius = 30;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        if (arPoints != null) {
            for (int i = 0; i < arPoints.size(); i++) {
//            dis = calculateDistanceInMeters(currentLocation, arPoints.get(i).getLocation());

                dis = distInMetres(currentLocation, arPoints.get(i).getLocation());
                float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
                float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
                float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

                float[] cameraCoordinateVector = new float[4];
                Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

                // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
                // if z > 0, the point will display on the opposite
                if (cameraCoordinateVector[2] < 0) {
                    x = (0.5f + cameraCoordinateVector[0] / cameraCoordinateVector[3]) * canvas.getWidth();
                    y = (0.5f - cameraCoordinateVector[1] / cameraCoordinateVector[3]) * canvas.getHeight();

                    canvas.drawCircle(x, y, radius, paint);
                    //canvas.drawBitmap(,x,y,paint);
                    canvas.drawText(arPoints.get(i).getName() + "\n", x - (30 * arPoints.get(i).getName().length() / 2), y - 100, paint);
                    canvas.drawText(String.format("%.2f", dis) + " m", x - (30 * arPoints.get(i).getName().length() / 2), y - 40, paint);


               /* for (String line: arPoints.get(i).getName().split("\n")) {
                    canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2), y - 80, paint);
                    y += paint.descent() - paint.ascent();
                }*/
                }
            }

        }

    }


    public float calculateDistanceInMeters(Location currentLocation, Location location) {
        distanceInMeters = this.currentLocation.distanceTo(location);
        Log.d("Distance in Meters", "" + distanceInMeters + "  " + location);
        return distanceInMeters;
    }

    protected double distInMetres(Location currentLocation, Location location) {

        double lat2, lng2;

        double lat1 = currentLocation.getLatitude();
        double lng1 = currentLocation.getLongitude();

        lat2 = location.getLatitude();
        lng2 = location.getLongitude();

        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        Log.d("Distance", "" + dist * 1000);
        return dist * 1000;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      /*  float X = event.getX();
        float Y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(X  == x && Y == y)
                {
                    Toast.makeText(context,"Hiiiiii",Toast.LENGTH_SHORT).show();

                }
                Log.d("motionEvent", "action_up"+ X +"" + Y);
                break;
        }*/
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = event.getX();
        float Y = event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (X == x && Y == y) {
                    Toast.makeText(context, "Hiiiiii", Toast.LENGTH_SHORT).show();

                }
                Log.d("motionEvent", "action_down" + x + " " + y);
                break;
        }
        return true;
    }
}
