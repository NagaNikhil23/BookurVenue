package panduchinnu.venubooking;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class custReg extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5;
    Button bt1;
    String cuname,cuemail,cupwd,curepwd,cumobile;
    String mobpat="[0-9]{10}";
    String emailpat="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_reg);

        ActionBar ab=getSupportActionBar();
        ab.setTitle("Customer Registration");
        ab.setLogo(R.drawable.logo);


        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText5);
        e3=(EditText)findViewById(R.id.editText6);
        e4=(EditText)findViewById(R.id.editText7);
        e5=(EditText)findViewById(R.id.editText8);
        bt1=(Button)findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuname=(String)e1.getText().toString();
                cuemail=(String)e2.getText().toString();
                cupwd=(String)e3.getText().toString();
                curepwd=(String)e4.getText().toString();
                cumobile=(String)e5.getText().toString();
                if(cuname.length()>30){
                    Toast.makeText(getApplicationContext(), "Please enter name less than 30 characters", Toast.LENGTH_LONG).show();
                }
                else if((!(cuemail.matches(emailpat))) || cuemail.length()<10){
                    Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_LONG).show();

                }
                else if(!(cumobile.matches(mobpat)))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid 10 digit mobile number", Toast.LENGTH_LONG).show();

                }else if((!cupwd.equals(curepwd)) || cupwd.length()<8 || cupwd.length()>15){
                    Toast.makeText(getApplicationContext(), "Please check passwords and enter them correctly,it should be between 8-15 chars", Toast.LENGTH_LONG).show();

                }else if(cuemail.equals("")||cupwd.equals("")||cumobile.equals("")||cuname.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill the empty fields",Toast.LENGTH_LONG).show();

                }else {
                    Snackbar.make(v, "" + cuname + " " + cuemail + "" + cupwd + "" + curepwd + "" + cumobile, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Dbcus dbc = new Dbcus(getApplicationContext());
                    String res = dbc.insertEntry(cuname, cuemail, cupwd, cumobile);
                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                    String s3=""+generateOtp();
                    sendotp(cumobile,s3);
                    Intent icust = new Intent(getApplicationContext(), cusotp.class);
                    startActivity(icust);

                }



            }
        });

    }
    private int generateOtp() {
        Random r=new Random();
        return r.nextInt(10000);
    }



    private void sendotp(String s3, String otp) {
        String sent="message sent";
        String delivered="message delivered";
        Intent i10=new Intent(getApplicationContext(),custReg.class);
        PendingIntent sentint=PendingIntent.getBroadcast(getApplicationContext(),0,i10,0);

        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(s3,null,"Greetings from BookUrVenue,Your Otp is "+otp,sentint,null);
        //Toast.makeText(getApplicationContext(),"hiiii",Toast.LENGTH_LONG).show();
    }

}
