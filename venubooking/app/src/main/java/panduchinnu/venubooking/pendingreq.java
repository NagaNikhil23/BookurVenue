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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pendingreq.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pendingreq#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pendingreq extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String user;
    ImageButton home;
    Dbcus dbcus;
    TextView t27;

    private OnFragmentInteractionListener mListener;

    public pendingreq() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pendingreq.
     */
    // TODO: Rename and change types and number of parameters
    public static pendingreq newInstance(String param1, String param2) {
        pendingreq fragment = new pendingreq();
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
            user=getArguments().getString("customer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pendingreq, container, false);
        final ListView LV=(ListView)v.findViewById(R.id.list267);
        dbcus = new Dbcus(getActivity());
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> bookedfors = new ArrayList<>();
        Cursor res = dbcus.getCustPendingBookings(user);
        if (res.getCount() == 0) {
            t27=(TextView)v.findViewById(R.id.textView27);
            t27.setText("You dont have any pending bookings");
        }
        while (res.moveToNext()) {
            final String sname = res.getString(0);
            final String date = res.getString(1);
            final String bookedfor ="Owner:"+ res.getString(4);
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
                    String date11=args.getString("date").toString();
                    displayalert(hallname);
                }
            });
        }
        home=(ImageButton)v.findViewById(R.id.button18);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new cusHome();
                Bundle args=new Bundle();
                args.putString("customer",user);
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

    private void displayalert(String hallname) {
        Toast.makeText(getActivity(),"in display",Toast.LENGTH_LONG).show();
        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
        final Cursor res2=dbcus.gethallcompleteDetails(hallname);
        if(res2.getCount()==0){
            return;
        }
        StringBuffer strn1 = new StringBuffer();
        while(res2.moveToNext()){
            strn1.append("Name:"+res2.getString(0)+"\n");
            strn1.append("Area:"+res2.getString(1)+"\n");
            strn1.append("Address:"+res2.getString(2)+"\n");
            strn1.append("Capacity:"+res2.getString(3)+"\n");
            strn1.append("AC:"+res2.getString(4)+"\n");
            strn1.append("Cost:"+res2.getString(5)+"\n");
            strn1.append("Owner:"+res2.getString(6)+"\n");
            Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
        }
        ad.setCancelable(false);
        ad.setTitle("Customer details");
        Toast.makeText(getActivity(),""+strn1,Toast.LENGTH_LONG).show();
        ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Fragment frg = new cusHome();
                Bundle args=new Bundle();
                args.putString("customer",user);
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
