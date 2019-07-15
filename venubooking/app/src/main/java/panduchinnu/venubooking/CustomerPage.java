package panduchinnu.venubooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static panduchinnu.venubooking.profilecustfragment.*;

public class CustomerPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,cusHome.OnFragmentInteractionListener,add_bookings.OnFragmentInteractionListener,profilecustfragment.OnFragmentInteractionListener,EditBookingsfragment.OnFragmentInteractionListener,custom_halls.OnFragmentInteractionListener,EditHallBooking.OnFragmentInteractionListener,cuseditok.OnFragmentInteractionListener,cusviewbooking.OnFragmentInteractionListener,pendingreq.OnFragmentInteractionListener,profilesavecusfragment.OnFragmentInteractionListener,profilesavefrag.OnFragmentInteractionListener,Rating.OnFragmentInteractionListener,BlankFragment.OnFragmentInteractionListener {

    String user;
    int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);
        Intent cit=getIntent();
        user=cit.getStringExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frg = new Rating();
                Bundle args=new Bundle();
                args.putString("customer",user);
                frg.setArguments(args);
                Toast.makeText(getApplicationContext(),""+user,Toast.LENGTH_LONG).show();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2,frg);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment frg = new cusHome();
        Bundle args=new Bundle();
        args.putString("customer",user);
        frg.setArguments(args);
        Toast.makeText(getApplicationContext(),""+user,Toast.LENGTH_LONG).show();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame2,frg);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press the back button again to close.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i220=new Intent(this,customerhome.class);
            startActivity(i220);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Cushome) {
            selectItem(0);
        } else if (id == R.id.Cusaddr) {
            selectItem(1);

        } else if (id == R.id.Cusview) {
            selectItem(2);

        } else if (id == R.id.Cusedit) {
            selectItem(3);

        } else if (id == R.id.Cuspro) {
            selectItem(4);

        } else if (id == R.id.Cuslogout) {
            Intent i220=new Intent(this,customerhome.class);
            startActivity(i220);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectItem(int id) {
        Fragment frg = getHomeFragment(id);
        Bundle args=new Bundle();
        args.putString("customer",user);
        frg.setArguments(args);
        //Toast.makeText(getApplicationContext(),""+user,Toast.LENGTH_LONG).show();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame2,frg);
        fragmentTransaction.commitAllowingStateLoss();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

    private Fragment getHomeFragment(int id) {

        switch(id){
            case 0:
                cusHome hme=new cusHome();
                return hme;

            case 1:
                add_bookings add= new add_bookings();
                return add;

            case 2:
                cusviewbooking eehb= new cusviewbooking();
                return eehb;
            case 3:
                pendingreq prf= new pendingreq();
                return prf;

            case 4:
                profilecustfragment pprf= new profilecustfragment();
                return pprf;
            case 5:
                Intent i220=new Intent(this,customerhome.class);
                startActivity(i220);

            default:
                Home hm=new Home();
                return hm;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
