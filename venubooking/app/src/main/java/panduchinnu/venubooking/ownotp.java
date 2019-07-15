package panduchinnu.venubooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ownotp extends AppCompatActivity {

    Button butt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownotp);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Owner Registration");


        butt1=(Button)findViewById(R.id.ownotpok);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itr1=new Intent(getApplicationContext(),ownregsuc.class);
                startActivity(itr1);
            }
        });

    }
}
