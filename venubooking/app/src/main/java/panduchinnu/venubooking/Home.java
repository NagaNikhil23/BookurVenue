package panduchinnu.venubooking;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements addHalls.OnFragmentInteractionListener,EditBookingsfragment.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView LV;
    Dbcus dbcus;
    Button addhallsbut,edithall,pendingRequests;
    String user,hallname;
    StringBuffer strn1;
    ArrayList<Bitmap> imageviews;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        addhallsbut = (Button) v.findViewById(R.id.addhall);
        edithall=(Button)v.findViewById(R.id.button4);
        pendingRequests=(Button)v.findViewById(R.id.button5);
        addhallsbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoother();
            }

            private void movetoother() {
                Fragment frg = new addHalls();
                Bundle args=new Bundle();
                args.putString("user",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, frg);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
        edithall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new viewbookings();
                Bundle args=new Bundle();
                args.putString("user",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });
        pendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new pendingrequestsfrag();
                Bundle args=new Bundle();
                args.putString("user",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });
        final ListView LV = (ListView) v.findViewById(R.id.listView);
        dbcus = new Dbcus(getActivity());
        ArrayList<String> arrlis = new ArrayList<>();
        ArrayList<byte[]> imageviews = new ArrayList<>();
        Cursor res = dbcus.getHallsName(user);
        if (res.getCount() == 0) {
            return v;
        }
        while (res.moveToNext()) {
            final String sname = res.getString(0);
            byte[] image = res.getBlob(1);
            arrlis.add(sname);
            imageviews.add(image);
            //Toast.makeText(getActivity(),""+image,Toast.LENGTH_LONG).show();;
            ImageAdapter im=new ImageAdapter(getActivity(),arrlis,imageviews);
            LV.setAdapter(im);
            LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String Value = (String) LV.getItemAtPosition(position);
                    displayalert(Value);

                }
            });
        }


        return v;
    }

        private void displayalert(final String value) {
          Toast.makeText(getActivity(),"in display",Toast.LENGTH_LONG).show();
            AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
            final Cursor res2=dbcus.gethallDetails(value);
            if(res2.getCount()==0){
                return;
            }
            strn1 = new StringBuffer();
            while(res2.moveToNext()){
                hallname=res2.getString(0);
                strn1.append("Name:"+res2.getString(0)+"\n");
                strn1.append("Area:"+res2.getString(1)+"\n");
                Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
            }
            ad.setCancelable(false);
            ad.setTitle("Edit the hall booking");
            //Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
            ad.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Fragment frg = new EditHallBooking();
                    Bundle args=new Bundle();
                    args.putString("hallname",hallname);
                    args.putString("owner",user);
                    frg.setArguments(args);
                    android.support.v4.app.FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, frg);
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
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
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


