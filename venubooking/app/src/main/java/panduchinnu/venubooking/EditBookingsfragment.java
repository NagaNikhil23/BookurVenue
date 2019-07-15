package panduchinnu.venubooking;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditBookingsfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditBookingsfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditBookingsfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String hallname,owner,cust;
    EditText ert2,ert3;
    TextView ert1;
    DatePicker dp1;
    Button b1;
    String hallnamee,datee,bookedby,status;


    private OnFragmentInteractionListener mListener;

    public EditBookingsfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditBookingsfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditBookingsfragment newInstance(String param1, String param2) {
        EditBookingsfragment fragment = new EditBookingsfragment();
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
            hallname=getArguments().getString("hallname");
            owner=getArguments().getString("owner");
            cust=getArguments().getString("cust");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_edit_hall_booking, container, false);
        final Dbcus dbcus=new Dbcus(getActivity());
        ert1=(TextView)v.findViewById(R.id.edth2);
        ert1.setText(hallname);
        //ert2=(EditText)v.findViewById(R.id.edth1);
        ert3=(EditText)v.findViewById(R.id.edth3);
        ert3.setText(cust);
        ert2=(EditText)v.findViewById(R.id.edth1);
        b1=(Button)v.findViewById(R.id.button6);
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hallnamee=ert1.getText().toString();
                datee=ert2.getText().toString();
                bookedby=ert3.getText().toString();
                //Toast.makeText(getActivity(),"onclick",Toast.LENGTH_LONG).show();
                Cursor res2=dbcus.getStatusofhall(hallnamee,datee);
                if(res2.getCount()==0){
                    String res=dbcus.insertBooking(hallnamee,datee,bookedby,"Pending",owner);
                    Toast.makeText(getActivity(),""+res,Toast.LENGTH_LONG).show();
                    ert1.setText("");
                    ert2.setText("");
                    ert3.setText("");
                    Fragment frg = new cuseditok();
                    Bundle args=new Bundle();
                    args.putString("customer",cust);
                    frg.setArguments(args);
                    android.support.v4.app.FragmentManager fm=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fm.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame2,frg);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                while(res2.moveToNext()){
                        Toast.makeText(getActivity(),"There is a booking for this date already",Toast.LENGTH_LONG).show();
                }
            }
        });
        ert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                ert2.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return  v;}

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
