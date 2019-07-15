package panduchinnu.venubooking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.UiSettings.*;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GooglePlayServicesUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.net.http.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    Button search;
    int REQUEST_SMS_GRANT = 99;
    private LocationRequest locationRequest;
    EditText place;
    Button get;


    public GoogleApiClient client;
    private android.location.Location lastlocation;
    String Location;
    List<Address> addressList = null;
    private Marker currentMarker;
    boolean mLocationPermissionGranted = false;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;
    public static final int REQUEST_LOCATION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.WRITE_GSERVICES}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE
                }, REQUEST_SMS_GRANT
        );
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_SMS_GRANT);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(currentMarker!=null){
                    currentMarker.remove();
                }
                double lati=latLng.latitude;
                double longi=latLng.longitude;
              //Toast.makeText(getApplicationContext(),""+lati+":::"+longi,Toast.LENGTH_LONG).show();
               currentMarker= mMap.addMarker(new MarkerOptions().position(latLng).title("Ur Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng markerrpos=marker.getPosition();
                Toast.makeText(getApplicationContext(),""+markerrpos.latitude+markerrpos.longitude,Toast.LENGTH_LONG).show();

                return false;
            }
        });
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                    mMap.clear();
                    mMap.addMarker( new MarkerOptions()
                           .position( position.target )
                             .title( position.toString() )
                    );            }
        });


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);

        place = (EditText) findViewById(R.id.searchplace);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location = (String) place.getText().toString();
                if(currentMarker!=null){
                    currentMarker.remove();
                }
                if (Location != null || !Location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(Location, 5);
                        //Toast.makeText(getApplicationContext(), "" + addressList.size(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
                    }
                    for (int k = 0; k < addressList.size(); k++) {
                        Address myaddress = addressList.get(k);
                        LatLng latLng = new LatLng(myaddress.getLatitude(), myaddress.getLongitude());
                       currentMarker= mMap.addMarker(new MarkerOptions().position(latLng).title("Ur Location" + k));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
                    }
                }
            }
        });
        get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMarker!=null){
                    currentMarker.remove();
                }
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location loc = LocationServices.FusedLocationApi.getLastLocation(client);
                double llatt = loc.getLatitude();
                double llong = loc.getLongitude();
                //Toast.makeText(getApplicationContext(), "" + llatt + llong, Toast.LENGTH_LONG).show();
                LatLng latLng = new LatLng(llatt, llong);
                currentMarker=mMap.addMarker(new MarkerOptions().position(latLng).title("Ur Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(10));


            }
        });


    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    // A step later in the tutorial adds the code to get the device location.
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
              @NonNull String permissions[],
               @NonNull int[] grantResults) {
              mLocationPermissionGranted = false;
           switch (requestCode) {
              case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
               // If request is cancelled, the result arrays are empty.
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {mLocationPermissionGranted = true;
        }
       }
     }
     updateLocationUI();
    }

    private void updateLocationUI() {
      if (mMap == null) {
        return;
    }

     /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
   if (ContextCompat.checkSelfPermission(this.getApplicationContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
       mLocationPermissionGranted = true;
   } else {
      ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
   }

    if (mLocationPermissionGranted) {
      mMap.setMyLocationEnabled(true);
      mMap.getUiSettings().setMyLocationButtonEnabled(true);
    } else {
     mMap.setMyLocationEnabled(false);
      mMap.getUiSettings().setMyLocationButtonEnabled(false);
      //mLastKnownLocation = null;
 }
    }


   /* public void onClick(View v) {
        if(v.getId() == R.id.search)
        Location = (String) place.getText().toString();
        if (Location != null || !Location.equals("")) {
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                addressList = geocoder.getFromLocationName(Location, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int k = 0; k < addressList.size(); k++) {
                Address myaddress = addressList.get(k);
                LatLng latLng = new LatLng(myaddress.getLatitude(), myaddress.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Ur Location"+k));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

            }
        }

    }*/


    public synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();

        client.connect();


    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        lastlocation=location;

        Toast.makeText(getApplicationContext(),"  ",Toast.LENGTH_LONG);
        if(currentMarker != null){
            currentMarker.remove();
        }
        LatLng latLng = new LatLng(lastlocation.getLatitude(), lastlocation.getLongitude());
        currentMarker=mMap.addMarker(new MarkerOptions().position(latLng).title("Ur Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
