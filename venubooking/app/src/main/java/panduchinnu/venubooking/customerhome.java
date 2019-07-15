package panduchinnu.venubooking;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class customerhome extends AppCompatActivity {
    Button cussig,button3;
    EditText e1,e2;
    String cuusst,cupwst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhome);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Customer Login");
        ab.setLogo(R.drawable.logo);


        cussig=(Button)findViewById(R.id.cussig);
        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText4);


        button3=(Button)findViewById(R.id.button3);


        cussig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icus1=new Intent(getApplicationContext(),custReg.class);
                startActivity(icus1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuusst=(String)e1.getText().toString();
                cupwst=(String)e2.getText().toString();

                if(cuusst.equals("")||cupwst.equals("")){
                    Snackbar.make(v,"Fill the empty fields",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    return;
                }
                else {
                    Dbcus dbc=new Dbcus(getApplicationContext());
                    Cursor cursor= dbc.getCustDetails(cuusst);
                    if (cursor.getCount()==0){
                        Toast.makeText(getApplicationContext(),"Data not available Sign up before login",Toast.LENGTH_LONG).show();
                        return;
                    }
                    while(cursor.moveToNext()){
                        String str=cursor.getString(0);
                        if (cupwst.equals(str)){
                            Toast.makeText(getApplicationContext(),"ur login successfull",Toast.LENGTH_LONG).show();
                            Intent cit=new Intent(getApplicationContext(),CustomerPage.class);
                            cit.putExtra("user",cuusst);
                            startActivity(cit);
                        }else{
                            Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_LONG).show();
                        }

                    }

                }
                Snackbar.make(v,cuusst+""+cupwst,Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i222=new Intent(this,MainActivity.class);
        startActivity(i222);
    }
}
