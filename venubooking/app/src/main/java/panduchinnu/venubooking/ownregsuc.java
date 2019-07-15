package panduchinnu.venubooking;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ownregsuc extends AppCompatActivity {
    Button ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownregsuc);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Owner Registration");
        ab.setLogo(R.drawable.logo);



        ob=(Button)findViewById(R.id.sucb);
        ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent into=new Intent(getApplicationContext(),Ownerhome.class);
                startActivity(into);
            }
        });

    }
}
