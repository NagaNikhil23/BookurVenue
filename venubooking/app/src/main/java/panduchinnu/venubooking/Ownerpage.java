package panduchinnu.venubooking;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import static panduchinnu.venubooking.R.id.decor_content_parent;
import static panduchinnu.venubooking.R.id.frame;

public class Ownerpage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,EditHallBooking.OnFragmentInteractionListener,profilefragment.OnFragmentInteractionListener,Home.OnFragmentInteractionListener,profilesavefrag.OnFragmentInteractionListener,addHalls.OnFragmentInteractionListener,pendingrequestsfrag.OnFragmentInteractionListener,addok.OnFragmentInteractionListener,editok.OnFragmentInteractionListener,viewbookings.OnFragmentInteractionListener,pendingreq.OnFragmentInteractionListener,Rating.OnFragmentInteractionListener,MapsFrag.OnFragmentInteractionListener {

    String user;
    int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerpage);
        Intent cut=getIntent();
        user=cut.getStringExtra("user");

       /* bttt = (Button) findViewById(R.id.addhall);
        bttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittt1 = new Intent(getApplicationContext(), Addhall.class);
                startActivity(ittt1);
            }
        });*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frg = new Rating();
                Bundle args=new Bundle();
                args.putString("owner",user);
                frg.setArguments(args);
                Toast.makeText(getApplicationContext(),""+user,Toast.LENGTH_LONG).show();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame,frg);
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

        Fragment frg = new Home();
        Bundle args=new Bundle();
        args.putString("user",user);
        frg.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame,frg);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ownerpage, menu);
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
            Intent intent = new Intent(this,Ownerhome.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.homeoption) {
            selectItem(0);
        } else if (id == R.id.addoption) {
            selectItem(1);
        } else if (id == R.id.viewoption) {
            selectItem(2);

        } else if (id == R.id.requestsoption) {
            selectItem(3);

        } else if (id == R.id.profile) {
            selectItem(4);

        } else if (id == R.id.logout) {
                Intent intent = new Intent(this,Ownerhome.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectItem(int id) {
        Fragment frg = getHomeFragment(id);
        Bundle args=new Bundle();
        args.putString("user",user);
        frg.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame,frg);
        fragmentTransaction.commitAllowingStateLoss();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

    private Fragment getHomeFragment(int id) {

        switch(id){
            case 0:
                Home hme=new Home();
                return hme;

            case 1:
                addHalls add= new addHalls();
                return add;

            case 2:
                viewbookings ehb= new viewbookings();
                return ehb;
            case 3:
                pendingrequestsfrag prfr= new pendingrequestsfrag();
                return prfr;

            case 4:
                profilefragment prf= new profilefragment();
                return prf;
            /*case 5:
                profilefragment prf= new profilefragment();
                return prf;*/


            default:
                Home hm=new Home();
                return hm;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
