package panduchinnu.venubooking;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ownerhome extends AppCompatActivity {
    Button ownhome, ownhome2;
    EditText eo1, eo2;
    String owusst, owpwst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerhome);

        ActionBar ab=getSupportActionBar();
        ab.setTitle("Owner Login");
        ab.setLogo(R.drawable.logo);


        eo1 = (EditText) findViewById(R.id.edit3);
        eo2 = (EditText) findViewById(R.id.edit4);
        ownhome = (Button) findViewById(R.id.ownsig);
        ownhome2 = (Button) findViewById(R.id.ownlogin);
        ownhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itt = new Intent(getApplicationContext(), ownreg.class);
                startActivity(itt);

            }
        });
        ownhome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owusst = (String) eo1.getText().toString();
                owpwst = (String) eo2.getText().toString();
                if (owpwst.equals("") || owusst.equals("")) {
                    Toast.makeText(Ownerhome.this, "fill empty fields", Toast.LENGTH_LONG).show();
                } else {
                    Dbcus dbo = new Dbcus(getApplicationContext());
                    Cursor cursor1 = dbo.getOwnerDetails(owusst);
                    if (cursor1.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "Data not available Sign up before login", Toast.LENGTH_LONG).show();
                        return;
                    }
                    while (cursor1.moveToNext()) {
                        String str1 = cursor1.getString(0);
                        if (owpwst.equals(str1)) {
                            Toast.makeText(getApplicationContext(), "ur login successfull", Toast.LENGTH_LONG).show();
                            Intent cut = new Intent(getApplicationContext(), Ownerpage.class);
                            cut.putExtra("user", owusst);
                            startActivity(cut);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                    Toast.makeText(Ownerhome.this, "" + owpwst + owusst, Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
