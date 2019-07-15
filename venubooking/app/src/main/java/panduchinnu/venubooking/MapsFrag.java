package panduchinnu.venubooking;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFrag extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    ImageButton search;
    int REQUEST_SMS_GRANT = 99;
    private LocationRequest locationRequest;
    EditText place;
    Button get;
    Button getlocation;
    public GoogleApiClient client;
    private android.location.Location lastlocation;
    String Location,user;
    List<Address> addressList = null;
    private Marker currentMarker;
    View v;
    String area,city,state;
    String addressess;
    RadioGroup rg1;
    double latti,longi;
    RadioButton rb1,rb2;
    SupportMapFragment mapFragment;
    boolean mLocationPermissionGranted = false;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;
    public static final int REQUEST_LOCATION_CODE = 99;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsFrag newInstance(String param1, String param2) {
        MapsFrag fragment = new MapsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            user=getArguments().getString("user");
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_GSERVICES}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_NETWORK_STATE
                }, REQUEST_SMS_GRANT
        );
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET}, REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_SMS_GRANT);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_maps,container,false);

       /* MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.mapfrag,mapFragment).commit();
        mapFragment.getMapAsync(this);*/


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        mapFragment = new SupportMapFragment();
        fragmentTransaction.add(R.id.mapfrag, mapFragment).commit();
        mapFragment.getMapAsync(this);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
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
        if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
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




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    @Override
    public void onLocationChanged(android.location.Location location) {
        lastlocation = location;
        final Geocoder geocoder = new Geocoder(getActivity());
       // Toast.makeText(getActivity(), "  ", Toast.LENGTH_LONG);
        if (currentMarker != null) {
            currentMarker.remove();
        }
        latti=lastlocation.getLatitude();
        longi=lastlocation.getLongitude();
        try {
            List<Address>  address=  geocoder.getFromLocation(latti,longi,1);
            area=address.get(0).getLocality();
            city=address.get(0).getSubAdminArea();
            state=address.get(0).getAdminArea();

        } catch (IOException e) {
            Toast.makeText(getActivity(),""+e,Toast.LENGTH_LONG).show();
        }
        LatLng latLng = new LatLng(latti,longi);
        currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(""+area+";"+city+";"+state));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }


    public synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getActivity()).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();

        client.connect();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getActivity(),"Onmap",Toast.LENGTH_LONG).show();
        mMap = googleMap;
        final Geocoder geocoder = new Geocoder(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
            buildGoogleApiClient();
        }
        mMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        getlocation=(Button)v.findViewById(R.id.save);
        rg1=(RadioGroup)v.findViewById(R.id.viewmap);
        rb1=(RadioButton)v.findViewById(R.id.satellite);
        rb2=(RadioButton)v.findViewById(R.id.normal);
        rb2.setChecked(true);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(rb1.isChecked()){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }

                if(rb2.isChecked())
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

            }
        });
        //Toast.makeText(getActivity(),"Onmap  4444",Toast.LENGTH_LONG).show();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(currentMarker!=null){
                    currentMarker.remove();
                }
                latti=latLng.latitude;
                longi=latLng.longitude;
                try {
                   List<Address>  address=  geocoder.getFromLocation(latti,longi,1);
                    area=address.get(0).getLocality();
                    addressess=address.get(0).getAddressLine(0);
                    city=address.get(0).getSubAdminArea();
                    state=address.get(0).getAdminArea();

                } catch (IOException e) {
                    Toast.makeText(getActivity(),""+e,Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getActivity(),""+latti+":::"+longi,Toast.LENGTH_LONG).show();
                currentMarker= mMap.addMarker(new MarkerOptions().position(latLng).title(addressess+";"+area+";"+city+";"+state).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(5));

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng markerrpos=marker.getPosition();
                latti=markerrpos.latitude;
                longi=markerrpos.longitude;
                //Toast.makeText(getActivity(),""+latti+longi,Toast.LENGTH_LONG).show();


                return false;
            }
        });

        place = (EditText) v.findViewById(R.id.searchplace11);
        search = (ImageButton)v.findViewById(R.id.search11);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location = (String) place.getText().toString();
                if(currentMarker!=null){
                    currentMarker.remove();
                }
                if (Location != null || !Location.equals("")) {

                    try {
                        addressList = geocoder.getFromLocationName(Location, 5);
                       // Toast.makeText(getActivity(), "" + addressList.size(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
                    }
                    for (int k = 0; k < addressList.size(); k++) {
                        Address myaddress = addressList.get(k);
                        latti=myaddress.getLatitude();
                        longi=myaddress.getLongitude();
                        try {
                            List<Address>  address=  geocoder.getFromLocation(latti,longi,1);
                            addressess=address.get(k).getAddressLine(0);
                            area=address.get(k).getLocality();
                            city=address.get(k).getSubAdminArea();
                            state=address.get(k).getAdminArea();

                        } catch (IOException e) {
                            Toast.makeText(getActivity(),""+e,Toast.LENGTH_LONG).show();
                        }
                        LatLng latLng = new LatLng(latti,longi);
                        currentMarker= mMap.addMarker(new MarkerOptions().position(latLng).title(addressess+";"+area+";"+city+";"+state));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomBy(5));
                    }
                }
            }
        });
        get = (Button) v.findViewById(R.id.get11);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMarker!=null){
                    currentMarker.remove();
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                 latti = loc.getLatitude();
                 longi = loc.getLongitude();
                try {
                    List<Address>  address=  geocoder.getFromLocation(latti,longi,1);
                    area=address.get(0).getLocality();
                    addressess=address.get(0).getAddressLine(0);
                    city=address.get(0).getSubAdminArea();
                    state=address.get(0).getAdminArea();

                } catch (IOException e) {
                    Toast.makeText(getActivity(),""+e,Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getActivity(), "" + latti + longi, Toast.LENGTH_LONG).show();
                LatLng latLng = new LatLng(latti, longi);
                currentMarker=mMap.addMarker(new MarkerOptions().position(latLng).title(addressess+";"+area+";"+city+";"+state));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(5));


            }
        });
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),""+latti+":::::"+longi,Toast.LENGTH_LONG).show();
                Fragment frg = new addHalls();
                Bundle args=new Bundle();
                args.putString("user",user);
                args.putDouble("latti",latti);
                args.putDouble("longi",longi);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame,frg);
                fragmentTransaction.commitAllowingStateLoss();


            }
        });

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
