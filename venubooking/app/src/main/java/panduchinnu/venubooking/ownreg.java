package panduchinnu.venubooking;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class ownreg extends AppCompatActivity {
    Button bt2;
    EditText etr1,etr2,etr3,etr4,etr5,etr6,etr7;
    String owname,owpwd,owrepwd,owmail,owmobile,owadd,owaad;
    String mobpat="[0-9]{10}";
    String adhpat="[0-9]{12}";
    String emailpat="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownreg);

        ActionBar ab=getSupportActionBar();
        ab.setTitle("Owner Registration");
        ab.setLogo(R.drawable.logo);


        etr1=(EditText)findViewById(R.id.edit9);
        etr2=(EditText)findViewById(R.id.editText10);
        etr3=(EditText)findViewById(R.id.editText11);
        etr4=(EditText)findViewById(R.id.editText12);
        etr5=(EditText)findViewById(R.id.editText13);
        etr6=(EditText)findViewById(R.id.editText14);
        etr7=(EditText)findViewById(R.id.editText15);
        bt2=(Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owname=(String)etr1.getText().toString();
                owpwd=(String)etr2.getText().toString();
                owrepwd=(String)etr7.getText().toString();
                owmail=(String)etr3.getText().toString();
                owmobile=(String)etr4.getText().toString();
                owadd=(String)etr5.getText().toString();
                owaad=(String)etr6.getText().toString();
                if(owmail.equals("")||owpwd.equals("")||owmobile.equals("")||owname.equals("")||owaad.equals("")||owadd.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill the empty fields",Toast.LENGTH_LONG).show();
                }
                else if((!(owmail.matches(emailpat))) || owmail.length()<10){
                    Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_LONG).show();
                     }
                else if(!(owmobile.matches(mobpat)))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid 10 digit mobile number", Toast.LENGTH_LONG).show();

                }else if((!owpwd.equals(owrepwd)) || owpwd.length()<8 || owpwd.length()>15){
                    Toast.makeText(getApplicationContext(), "Please check passwords and enter them correctly,it should be between 8-15 chars", Toast.LENGTH_LONG).show();

                }else if(owname.length()>30){
                    Toast.makeText(getApplicationContext(), "Please enter name less than 30 characters", Toast.LENGTH_LONG).show();

                }else if(owadd.equals("")||(!owaad.matches(adhpat))){
                    Toast.makeText(getApplicationContext(),"Enter address and adhar number correctly",Toast.LENGTH_LONG).show();

                }else {
                    Dbcus dbOwn = new Dbcus(getApplicationContext());
                    String res = dbOwn.insert(owname, owpwd, owmail, owmobile, owadd, owaad);
                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                    String s22=""+generateOtp();
                    sendotp(owmobile,s22);
                    Intent it2 = new Intent(getApplicationContext(), ownotp.class);
                    startActivity(it2);
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
        Intent i10=new Intent(getApplicationContext(),ownreg.class);
        PendingIntent sentint=PendingIntent.getBroadcast(getApplicationContext(),0,i10,0);

        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(s3,null,"Greetings from BookUrVenue,Your Otp is "+otp,sentint,null);
        //Toast.makeText(getApplicationContext(),"hiiii",Toast.LENGTH_LONG).show();
    }


}
