package panduchinnu.venubooking;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.value;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link custom_halls.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link custom_halls#newInstance} factory method to
 * create an instance of this fragment.
 */
public class custom_halls extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView list30;
    ImageButton b15;
    String hallname;
    Dbcus dbcus;
    Double latti,longi;
    Button showmap;
    StringBuffer strn1;
    ArrayList<String> arrlis;
    Double[] lattiarray;
    ArrayList<Double> longiarray;
    String area,capa,cost,ac,cust,owner;

    private OnFragmentInteractionListener mListener;

    public custom_halls() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment custom_halls.
     */
    // TODO: Rename and change types and number of parameters
    public static custom_halls newInstance(String param1, String param2) {
        custom_halls fragment = new custom_halls();
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
            area=getArguments().getString("area");
            capa=getArguments().getString("capa");
            cost=getArguments().getString("cost");
            ac=getArguments().getString("ac");
            cust=getArguments().getString("cust");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_custom_halls, container, false);
        final ListView LV = (ListView) v.findViewById(R.id.list30);
        dbcus=new Dbcus(getActivity());
        b15=(ImageButton)v.findViewById(R.id.button15);
        showmap=(Button)v.findViewById(R.id.showmap);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new cusHome();
                Bundle args=new Bundle();
                args.putString("hallname",hallname);
                args.putString("owner",owner);
                args.putString("customer",cust);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2, frg);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
        final ArrayList<String> arrlis = new ArrayList<>();
        ArrayList<byte[]> imageviews = new ArrayList<>();
        Cursor res = dbcus.getselectedhalls(area,capa,cost,ac);
        if (res.getCount() == 0) {
            return v;
        }
        while (res.moveToNext()) {
            final String sname = res.getString(0);
            byte[] image = res.getBlob(1);
            //latti=res.getDouble(2);
            //longi=res.getDouble(3);
            arrlis.add(sname);
            imageviews.add(image);
            //lattiarray.add(latti);
            //longiarray.add(longi);
            //Toast.makeText(getActivity(),""+latti+"::::"+longi,Toast.LENGTH_LONG).show();
            //Toast.makeText(getActivity(), "" + image, Toast.LENGTH_LONG).show();
            ;
            ImageAdapter im = new ImageAdapter(getActivity(), arrlis, imageviews);
            LV.setAdapter(im);
            LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String Value = (String) LV.getItemAtPosition(position);
                    //Toast.makeText(getActivity(), "" + Value, Toast.LENGTH_LONG).show();
                    displayalert(Value);

                }
            });
        }
        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frg = new BlankFragment();
                Bundle args=new Bundle();
                args.putStringArrayList("hallnames",arrlis);
                args.putString("cust",cust);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2, frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });


        return v;
    }
    private void displayalert(final String value) {
        //Toast.makeText(getActivity(),""+cust,Toast.LENGTH_LONG).show();
        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
        final Cursor res2=dbcus.gethallcompleteDetails(value);
        if(res2.getCount()==0){

        }
        strn1=new StringBuffer();
        while(res2.moveToNext()){
            hallname=res2.getString(0);
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
                args.putString("hallname",hallname);
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
