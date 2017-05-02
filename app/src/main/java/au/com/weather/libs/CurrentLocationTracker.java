package au.com.weather.libs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by hiten.bahri on 2/05/2017.
 */

public class CurrentLocationTracker implements LocationListener {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    private final Context context;
    private LocationManager locationManager;
    private boolean isNetworkEnabled;
    private boolean isGPSEnabled;
    boolean isLocationAvailable = false;
    private Location currentLocation;
    private double latitude;
    private double longitude;
    protected static final int REQUEST_LOCATION = 113;

    public CurrentLocationTracker(Context context, Activity activity) {
        this.context = context;
        getCurrentLocation(activity);
    }

    public void requestLocationPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION);
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION);
                }
            } else {
                // Location Permission not granted
                // No error triggered if this is not granted. So as of now, there is no onPermissionResult code for Location
            }
        } else {
        }
    }

    private void getCurrentLocation(Activity activity) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(/*!isGPSEnabled ||*/ !isNetworkEnabled) {
            Toast.makeText(context, "No service provider available", Toast.LENGTH_SHORT).show();
        } else {
            isLocationAvailable = true;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission(activity);
            } else if (isNetworkEnabled) {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        currentLocation = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    if (currentLocation != null) {
                        latitude = currentLocation.getLatitude();
                        longitude = currentLocation.getLongitude();
                    }
                }
                catch(SecurityException e){
                    e.printStackTrace();
                }
            }
            if (isGPSEnabled) {
                if (currentLocation == null) {
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            currentLocation = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (currentLocation != null) {
                                latitude = currentLocation.getLatitude();
                                longitude = currentLocation.getLongitude();
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public boolean isLocationAvailable() {
        return isLocationAvailable;
    }

    public double getLatitude() {
        if(currentLocation != null){
            latitude = currentLocation.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if(currentLocation != null){
            longitude = currentLocation.getLongitude();
        }
        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void showSettingsAlert(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Location Services Disabled");
        alertDialog.setMessage("Please turn on the location services under Settings.");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        alertDialog.show();
    }

    public void stopLocationServices() {
        if (locationManager != null) {
            locationManager.removeUpdates(CurrentLocationTracker.this);
        }
    }

}
