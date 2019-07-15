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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pendingrequestsfrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pendingrequestsfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pendingrequestsfrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton home;
    String owner;
    Dbcus dbcus;

    private OnFragmentInteractionListener mListener;

    public pendingrequestsfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pendingrequestsfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static pendingrequestsfrag newInstance(String param1, String param2) {
        pendingrequestsfrag fragment = new pendingrequestsfrag();
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
            owner=getArguments().getString("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_pendingrequestsfrag, container, false);
        final ListView LV=(ListView)v.findViewById(R.id.List25);
        dbcus = new Dbcus(getActivity());
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> bookedfors = new ArrayList<>();
        Cursor res = dbcus.getPendingBookings(owner);
        if (res.getCount() == 0) {
            return v;
        }
        while (res.moveToNext()) {
            final String sname = res.getString(0);
            final String date = res.getString(1);
            final String bookedfor = res.getString(2);
            names.add(sname);
            dates.add(date);
            bookedfors.add(bookedfor);
            pendingbookingsadapter pd=new pendingbookingsadapter(getActivity(),names,dates,bookedfors);
            LV.setAdapter(pd);
            LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle args = (Bundle) LV.getItemAtPosition(position);
                    String hallname=args.getString("hallname").toString();
                    String customer=args.getString("customer").toString();
                    String date11=args.getString("date").toString();

                    displayalert(hallname,customer,date11);

                        }
            });
        }
        home=(ImageButton)v.findViewById(R.id.button17);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new Home();
                Bundle args=new Bundle();
                args.putString("user",owner);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, frg);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });



        return v;

    }
    private void displayalert(final String hallname, final String customername, final String dateee) {
        Toast.makeText(getActivity(),"in display",Toast.LENGTH_LONG).show();
        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
        final Cursor res2=dbcus.getCustomerDetails(customername);
        if(res2.getCount()==0){
            return;
        }
        StringBuffer strn1 = new StringBuffer();
        while(res2.moveToNext()){
            strn1.append("Name:"+res2.getString(0)+"\n");
            strn1.append("Email:"+res2.getString(2)+"\n");
            strn1.append("Mobileno:"+res2.getString(3)+"\n");
            Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
        }
        ad.setCancelable(false);
        ad.setTitle("Customer details");
        Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
        ad.setPositiveButton("Confirm Booking", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fragment frg = new EditHallBooking();
                Bundle args=new Bundle();
                args.putString("hallname",hallname);
                args.putString("owner",owner);
                args.putString("cust",customername);
                args.putString("date",dateee);
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
