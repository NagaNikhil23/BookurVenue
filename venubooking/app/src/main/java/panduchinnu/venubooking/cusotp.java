package panduchinnu.venubooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class cusotp extends AppCompatActivity {
    Button bcnf,bcan;
    EditText cotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusotp);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Customer Registration");


        cotp=(EditText)findViewById(R.id.editText2);
        bcnf=(Button)findViewById(R.id.button10);
        bcan=(Button)findViewById(R.id.button11);
        bcnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icnf=new Intent(getApplicationContext(),cusregsuc.class);
                startActivity(icnf);
            }
        });
        bcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ican=new Intent(getApplicationContext(),custReg.class);
                startActivity(ican);
            }
        });
    }
}
