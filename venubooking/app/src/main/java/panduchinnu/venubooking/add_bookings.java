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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link add_bookings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link add_bookings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_bookings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText eb1,eb2;
    Button bb;
    String reqarea,reqcapa,reqcost,reqac,user;
    RadioGroup rbd,rcost;
    RadioButton rbd1,rbd2,rcost1,rcost2,rcost3;



    private OnFragmentInteractionListener mListener;

    public add_bookings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_bookings.
     */
    // TODO: Rename and change types and number of parameters
    public static add_bookings newInstance(String param1, String param2) {
        add_bookings fragment = new add_bookings();
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
            user = getArguments().getString("customer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_bookings, container, false);
        eb1=(EditText)v.findViewById(R.id.spinner);
        eb2=(EditText)v.findViewById(R.id.booke1);
        rbd1=(RadioButton)v.findViewById(R.id.bookr1);
        rbd2=(RadioButton)v.findViewById(R.id.bookr2);
        rbd=(RadioGroup)v.findViewById(R.id.radioGroup2);
        rcost=(RadioGroup)v.findViewById(R.id.radioGroup3);
        rcost1=(RadioButton)v.findViewById(R.id.bookr3);
        rcost2=(RadioButton)v.findViewById(R.id.bookr4);
        rcost3=(RadioButton)v.findViewById(R.id.bookr5);
          bb=(Button)v.findViewById(R.id.bookbutton);
        //Toast.makeText(getActivity(),""+user,Toast.LENGTH_LONG).show();
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqarea=eb1.getText().toString();
                reqcapa=eb2.getText().toString();
                if(rbd1.isChecked()){
                    reqac="yes";
                }
                if(rbd2.isChecked()){
                    reqac="no";
                }
                if(rcost1.isChecked()){
                    reqcost="25000";
                }
                if(rcost2.isChecked()){
                    reqcost="50000";
                }
                if(rcost3.isChecked()){
                    reqcost="100000";
                }
                Fragment frg = new custom_halls();
                Bundle args=new Bundle();
                args.putString("area",reqarea);
                args.putString("capa",reqcapa);
                args.putString("ac",reqac);
                args.putString("cost",reqcost);
                args.putString("cust",user);
                //Toast.makeText(getActivity(),""+user,Toast.LENGTH_LONG).show();
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
