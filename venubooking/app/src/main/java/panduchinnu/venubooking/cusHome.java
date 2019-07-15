package panduchinnu.venubooking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link cusHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link cusHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cusHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String user;
    Button addb,viewb,pendingb;
    TextView tv1;

    private OnFragmentInteractionListener mListener;

    public cusHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cusHome.
     */
    // TODO: Rename and change types and number of parameters
    public static cusHome newInstance(String param1, String param2) {
        cusHome fragment = new cusHome();
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
        View v= inflater.inflate(R.layout.fragment_cus_home, container, false);
        addb=(Button)v.findViewById(R.id.addhall);
        viewb=(Button)v.findViewById(R.id.button4);
        pendingb=(Button)v.findViewById(R.id.button5);
        tv1=(TextView)v.findViewById(R.id.textView28);
        tv1.setText(""+user);
        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new add_bookings();
                Bundle args=new Bundle();
                args.putString("customer",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2,frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });
        viewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new cusviewbooking();
                Bundle args=new Bundle();
                args.putString("customer",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2,frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });
        pendingb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new pendingreq();
                Bundle args=new Bundle();
                args.putString("customer",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2,frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });










        return v;
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