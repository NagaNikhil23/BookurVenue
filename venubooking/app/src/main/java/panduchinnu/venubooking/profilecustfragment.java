package panduchinnu.venubooking;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profilecustfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profilecustfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilecustfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText tvv1, tvv2, tvv4;
    TextView tvv3;
    Button save, cancel;
    String customer, proname, propwd, promail, promob;
    Dbcus dbcus;
    String namee, email, mobile, password;


    private OnFragmentInteractionListener mListener;

    public profilecustfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilecustfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profilecustfragment newInstance(String param1, String param2) {
        profilecustfragment fragment = new profilecustfragment();
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
            customer = getArguments().getString("customer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilecustfragment, container, false);
        tvv1 = (EditText) v.findViewById(R.id.proe1);
        tvv2 = (EditText) v.findViewById(R.id.proe2);
        tvv3 = (TextView) v.findViewById(R.id.proe3);
        tvv4 = (EditText) v.findViewById(R.id.proe4);
        save = (Button) v.findViewById(R.id.probutton1);
        cancel = (Button) v.findViewById(R.id.probutton2);
        dbcus=new Dbcus(getActivity());
        Cursor res2 = dbcus.getCustomerDetails(customer);
        if (res2.getCount() == 0) {

        }
        while (res2.moveToNext()) {
            namee = res2.getString(0);
            password = res2.getString(1);
            mobile = res2.getString(3);

        }
        tvv1.setText(namee);
        tvv2.setText(password);
        tvv3.setText(customer);
        tvv4.setText(mobile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proname = (String) tvv1.getText().toString();
                propwd = (String) tvv2.getText().toString();
                promail = (String) tvv3.getText().toString();
                promob = (String) tvv4.getText().toString();
        /*if(proname.equals("")||propwd.equals("")||promail.equals("")||promob.equals("")||proadd.equals("")||proaadh.equals("")||promob.matches(mobpat)||proaadh.matches(aadhpat)||promail.matches(emailpat))
                {
                    Toast.makeText(getActivity(),"Fill the details correctly",Toast.LENGTH_LONG).show();
                }*/


                Toast.makeText(getActivity(),promail+promob+proname+propwd,Toast.LENGTH_LONG).show();
                String res = dbcus.updatecusdetails(proname, propwd, promail, promob);
                Toast.makeText(getActivity(), "" + res, Toast.LENGTH_LONG).show();
                movetoother();



            }
        });
        return v;
    }
     private void movetoother() {
        Fragment frg = new profilesavecusfragment();
        Bundle args=new Bundle();
        args.putString("customer",customer);
        frg.setArguments(args);
        android.support.v4.app.FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame2,frg);
        fragmentTransaction.commitAllowingStateLoss();
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
