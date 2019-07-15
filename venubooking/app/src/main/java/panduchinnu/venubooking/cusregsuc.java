package panduchinnu.venubooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cusregsuc extends AppCompatActivity {
    Button bsuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusregsuc);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Customer Registration");




        bsuc=(Button)findViewById(R.id.button12);
        bsuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isucc=new Intent(getApplicationContext(),customerhome.class);
                startActivity(isucc);
            }
        });
    }
}
