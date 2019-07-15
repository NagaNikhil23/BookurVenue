package panduchinnu.venubooking;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.R.attr.value;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GoogleMap mMap;
    StringBuffer strn1;
    GoogleApiClient client;
    final int REQUEST_SMS_GRANT=999;
    SupportMapFragment mapFragment;
    ArrayList<String> hallnames;
    String cust,hall,owner;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
            hallnames=getArguments().getStringArrayList("hallnames");
            cust=getArguments().getString("cust");
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
        View v = inflater.inflate(R.layout.fragment_blank, container, false);


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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        final Dbcus db=new Dbcus(getActivity());
        for(int k=0;k<hallnames.size();k++) {

            String hallname = hallnames.get(k).toString();
            Cursor res = db.getlatlang(hallname);
            if (res.getCount() == 0) {
                return;
            }
            while (res.moveToNext()) {
                Double latti = res.getDouble(0);
                Double longi = res.getDouble(1);
                LatLng hall = new LatLng(latti, longi);
                mMap.addMarker(new MarkerOptions().position(hall).title(hallname)).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(hall));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(hall));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(5));
            }

        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng markerrpos=marker.getPosition();
                double latti=markerrpos.latitude;
                double longi=markerrpos.longitude;
                displayalert(latti,longi);


                return true;
            }

            private void displayalert(double latti, double longi) {
                //Toast.makeText(getActivity(),""+cust,Toast.LENGTH_LONG).show();
                AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
                final Cursor res2=db.gethalldetailsatlatlang(latti,longi);
                if(res2.getCount()==0){

                }
                strn1=new StringBuffer();
                while(res2.moveToNext()){
                    hall=res2.getString(0);
                    strn1.append("Name:"+res2.getString(0)+"\n");
                    strn1.append("Area:"+res2.getString(1)+"\n");
                    strn1.append("Address:"+res2.getString(2)+"\n");
                    strn1.append("Capacity:"+res2.getString(3)+"\n");
                    strn1.append("Ac:"+res2.getString(4)+"\n");
                    strn1.append("Cost:"+res2.getString(5)+"\n");
                     owner=res2.getString(6);
                    strn1.append("Owner:"+res2.getString(6)+"\n");
                    Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
                }
                ad.setCancelable(false);
                ad.setTitle("Edit the hall booking");
                //Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
                ad.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment frg = new EditBookingsfragment();
                        Bundle args=new Bundle();
                        args.putString("hallname",hall);
                        args.putString("owner",owner);
                        args.putString("cust",cust);
                        frg.setArguments(args);
                        android.support.v4.app.FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        fragmentTransaction.replace(R.id.frame2, frg);
                        fragmentTransaction.commitAllowingStateLoss();

                    }
                });
                ad.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                ad.setMessage(strn1);
                ad.show();
            }
        });

    }


    public synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getActivity()).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();

        client.connect();
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
