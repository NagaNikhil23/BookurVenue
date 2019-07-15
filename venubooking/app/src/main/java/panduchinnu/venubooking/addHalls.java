package panduchinnu.venubooking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addHalls.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addHalls#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addHalls extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tt1;
    Button bt3,bt4,btt1;
    EditText et11,et12,et13,et14,et15,e16;
    RadioGroup rg1,rg2;
    ImageView imageView;
    RadioButton rb1,rb2,rb3,rb4;
    String venname,vencapa,venarea,venadd,venroom,vensuited,venac,user,vencost;
    double latti=0,longi=0;
    byte [] image;
    public final int REQUEST_CODE_GALEERY=999;

    private OnFragmentInteractionListener mListener;

    public addHalls() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addHalls.
     */
    // TODO: Rename and change types and number of parameters
    public static addHalls newInstance(String param1, String param2) {
        addHalls fragment = new addHalls();
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
            user= getArguments().getString("user");
            latti=getArguments().getDouble("latti");
            longi=getArguments().getDouble("longi");


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Toast.makeText(getActivity(),"onReceive",Toast.LENGTH_LONG).show();
        if(requestCode == REQUEST_CODE_GALEERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST_CODE_GALEERY);
                //Toast.makeText(getActivity(),"you have permissions",Toast.LENGTH_LONG).show();

            }
            else{
                //Toast.makeText(getActivity(),"you dont have permissions",Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getActivity(),"onActivity",Toast.LENGTH_LONG).show();
        if(requestCode==REQUEST_CODE_GALEERY && resultCode<= 1 && data != null) {
            Uri uri = data.getData();

            try{
                InputStream is=getActivity().getContentResolver().openInputStream(uri);
                Bitmap bm= BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bm);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  v=inflater.inflate(R.layout.fragment_add_halls, container, false);
        bt3=(Button)v.findViewById(R.id.add8);
        btt1=(Button)v.findViewById(R.id.add99);
        tt1=(TextView)v.findViewById(R.id.text111);
        if (latti != 0 && longi != 0) {
            tt1.setText("Change Location");
        }

        et11=(EditText)v.findViewById(R.id.adde1);
        et12=(EditText)v.findViewById(R.id.adde2);
        et13=(EditText)v.findViewById(R.id.adde3);
        et14=(EditText)v.findViewById(R.id.adde4);
        et15=(EditText)v.findViewById(R.id.adde5);
        rg1=(RadioGroup)v.findViewById(R.id.radioGroup) ;
        rg2=(RadioGroup)v.findViewById(R.id.radioGP);
        rb1=(RadioButton)v.findViewById(R.id.addr1);
        rb2=(RadioButton)v.findViewById(R.id.addr2);
        rb3=(RadioButton)v.findViewById(R.id.addr3);
        rb4=(RadioButton)v.findViewById(R.id.addr4);
        e16=(EditText)v.findViewById(R.id.adde6);
        bt4=(Button)v.findViewById(R.id.bt6);
        imageView=(ImageView)v.findViewById(R.id.imageView2);

        btt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = new MapsFrag();
                Bundle args=new Bundle();
                args.putString("user",user);
                frg.setArguments(args);
                android.support.v4.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame,frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });



        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venname   =(String)et11.getText().toString();
                venarea   =(String)et12.getText().toString();
                venadd   =(String)et13.getText().toString();
                vencapa   =(String)et14.getText().toString();
                venroom   =(String)et15.getText().toString();
                vencost    =(String)e16.getText().toString();
                if(rb1.isChecked()){
                    vensuited="Business";
                }
                if(rb2.isChecked()){
                    vensuited="Family";
                }
                if(rb3.isChecked()){
                    venac="yes";
                }
                if(rb4.isChecked()){
                    venac="no";
                }
                try{
                   image= imagetobyte(imageView);
                }catch(Exception e){
                    Toast.makeText(getActivity(),"unable to convert",Toast.LENGTH_LONG);
                }
                if(venname.equals("")||venarea.equals("")||venadd.equals("")||vencapa.equals("")||venroom.equals("")||vencost.equals("")){
                    Snackbar.make(v,"Fill the empty fields",Snackbar.LENGTH_LONG).show();
                }
                else{
                    Dbcus dbcus=new Dbcus(getActivity());
                    Toast.makeText(getActivity(),""+image,Toast.LENGTH_LONG).show();
                    String result=dbcus.insertHalls(venname,venarea,venadd,vencapa,venroom,vensuited,venac,user,vencost,image,latti,longi);
                    Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(),""+venname+""+venarea+""+venadd+""+vencapa+""+venroom+""+vensuited+""+venac,Toast.LENGTH_LONG).show();
                    Fragment frg = new addok();
                    Bundle args=new Bundle();
                    args.putString("user",user);
                    frg.setArguments(args);
                    android.support.v4.app.FragmentManager fm=getFragmentManager();
                    FragmentTransaction fragmentTransaction=fm.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame,frg);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALEERY);
                //Toast.makeText(getActivity(),"you ",Toast.LENGTH_LONG).show();
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST_CODE_GALEERY);
                //Toast.makeText(getActivity(),"you have permissions bt",Toast.LENGTH_LONG).show();

            }
        });

        return  v;

    }

    private byte[] imagetobyte(ImageView imageView) {
        //Toast.makeText(getActivity(),"you "+imageView,Toast.LENGTH_LONG).show();
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0,stream);
        byte[] byteArray =stream.toByteArray();
        Toast.makeText(getActivity(),"you "+byteArray,Toast.LENGTH_LONG).show();
        return byteArray;
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
