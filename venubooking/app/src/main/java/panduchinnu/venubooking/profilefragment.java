package panduchinnu.venubooking;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profilefragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilefragment extends Fragment implements profilesavefrag.OnFragmentInteractionListener {
    Button btt3,btt4;
    TextView ep3;
    EditText ep1,ep2,ep4,ep5,ep6;
    String proname,propwd,promail,promob,proadd,proaadh;
    String mobpat="[0-9]{10}";
    String aadhpat="[0-9]{12}";
    String emailpat="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String username;
    String name,password,mobile,address,adhar;

    private OnFragmentInteractionListener mListener;

    public profilefragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profilefragment newInstance(String param1, String param2) {
        profilefragment fragment = new profilefragment();
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
            username=getArguments().getString("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View  v=inflater.inflate(R.layout.fragment_profilefragment, container, false);
        final Dbcus dbcus=new Dbcus(getActivity());
        ep1=(EditText)v.findViewById(R.id.proe1);
        ep2=(EditText)v.findViewById(R.id.proe2);
        ep3=(TextView)v.findViewById(R.id.proe3);
        ep3.setText(username);
        ep4=(EditText)v.findViewById(R.id.proe4);
        ep5=(EditText)v.findViewById(R.id.proe5);
        ep6=(EditText)v.findViewById(R.id.proe6);
        btt3=(Button)v.findViewById(R.id.probutton1);
        btt4=(Button)v.findViewById(R.id.probutton2);
        Cursor res2=dbcus.getowneralldetails(username);
        if(res2.getCount()==0){

        }
        while(res2.moveToNext()) {
            name = res2.getString(0);
            password = res2.getString(1);
            mobile = res2.getString(3);
            address = res2.getString(4);
            adhar = res2.getString(5);
        }
        ep1.setText(name);ep2.setText(password);ep4.setText(mobile);ep5.setText(address);ep6.setText(adhar);

            btt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proname=(String)ep1.getText().toString();
                propwd=(String)ep2.getText().toString();
                promail=(String)ep3.getText().toString();
                promob=(String)ep4.getText().toString();
                proadd=(String)ep5.getText().toString();
                proaadh=(String)ep6.getText().toString();

                /*if(proname.equals("")||propwd.equals("")||promail.equals("")||promob.equals("")||proadd.equals("")||proaadh.equals("")||promob.matches(mobpat)||proaadh.matches(aadhpat)||promail.matches(emailpat))
                {
                    Toast.makeText(getActivity(),"Fill the details correctly",Toast.LENGTH_LONG).show();
                }*/

                Toast.makeText(getActivity(),proname+" "+propwd+" "+promail+" "+promob+" "+proadd+" "+proaadh+ "", Toast.LENGTH_LONG).show();
                String res=dbcus.updateOwnerdetails(proname,propwd,promail,promob,proadd,proaadh);
                Toast.makeText(getActivity(),""+res,Toast.LENGTH_LONG).show();
                movetoother();
            }
            private void movetoother() {
                Fragment frg = new profilesavefrag();
                Bundle args=new Bundle();
                args.putString("user",username);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame,frg);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
