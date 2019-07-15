package panduchinnu.venubooking;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button b, f1;
    ImageView slidingimage;
    int backButtonCount = 0;
    int currentimageindex = 0;
    int REQUEST_SMS_GRANT=99;
    private int[] IMAGE_IDS = {R.drawable.thy, R.drawable.thd, R.drawable.ther, R.drawable.ijojlo, R.drawable.tht};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab=getSupportActionBar();
        ab.setLogo(R.drawable.logo);


        final Handler mHandler = new Handler();
        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                AnimateandSlideShow();
            }
        };

        int delay = 1000; // delay for 1 sec.

        int period = 1500; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_GSERVICES},REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE
                },REQUEST_SMS_GRANT
        );
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},REQUEST_SMS_GRANT);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_WIFI_STATE},REQUEST_SMS_GRANT);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission_group.LOCATION},REQUEST_SMS_GRANT);

        b = (Button) findViewById(R.id.cusbut);
        f1 = (Button) findViewById(R.id.ownbut);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icus = new Intent(getApplicationContext(), customerhome.class);
                //ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},REQUEST_SMS_GRANT);
                startActivity(icus);
            }
        });
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iof = new Intent(getApplicationContext(), Ownerhome.class);
                //ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS,},REQUEST_SMS_GRANT);

                //Intent iof = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(iof);
            }
        });
    }

    private void AnimateandSlideShow() {

        ImageView slidingimage = (ImageView) findViewById(R.id.imageView);

        if (currentimageindex <= 4) {
            slidingimage.setImageResource(IMAGE_IDS[currentimageindex]);
            currentimageindex++;
        } else {
            currentimageindex = 0;
            return;
        }
        Animation rotateimage = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        slidingimage.startAnimation(rotateimage);
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press the back button again to close.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }

    }
}
