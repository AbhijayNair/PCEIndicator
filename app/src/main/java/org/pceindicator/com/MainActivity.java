package org.pceindicator.com;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    LinearLayout layoutMap,layoutRes,layoutVid,layoutChat,layoutPush,layoutTime;
    LinearLayout root;
    FirebaseAuth mAuth;
    String[] emailSplit,email2;
    FloatingActionButton pushNotificationButton;
    TextView userName,userEmail;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        sp = getSharedPreferences("USER_DATA",MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PCE Indicator");
        pushNotificationButton = findViewById(R.id.fab_pushNoti);
        drawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, R.drawable.ic_drawer,
                R.string.open_desc,
                R.string.close_desc);
        drawerLayout.setDrawerListener(mDrawerToggle);
        layoutPush = findViewById(R.id.layout_pushNoti);
        layoutTime = findViewById(R.id.layout_time);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        userName = header.findViewById(R.id.navbar_text);
        userEmail = header.findViewById(R.id.textView_email);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(sp.contains("YEAR")&&sp.contains("BRANCH")&&sp.contains("DIVISION")){
            //do nothing
        }
        else {
            if(currentUser != null){
                String email = currentUser.getEmail();
                email2 = email.split("@");
                if (email2[1].equals("student.mes.ac.in"))
                    startActivity(new Intent(MainActivity.this, UserInfo.class));
                else if(email2[1].equals("mes.ac.in"))
                    layoutTime.setVisibility(View.GONE);
                else {
                    mAuth.signOut();
                    finish();
                }
            }
            else{
                mAuth.signOut();
                finish();
            }
        }
        if(currentUser != null){
            String EMAIL = currentUser.getEmail();
            userEmail.setText(EMAIL);
            emailSplit = EMAIL.split("@");
            if(!emailSplit[1].equals("student.mes.ac.in")){
                mAuth.signOut();
                finish();
                Toast.makeText(MainActivity.this,"MES ID not used",Toast.LENGTH_SHORT).show();

            }
          if(currentUser.getDisplayName() != null ){
              userName.setText(currentUser.getDisplayName());
          }
          else
              userName.setText(emailSplit[0]);


            if(emailSplit[1].equals("mes.ac.in")){
                layoutPush.setVisibility(View.VISIBLE);
                header.setBackgroundColor(Color.parseColor("#FC5C7D"));
                pushNotificationButton.setVisibility(View.VISIBLE);
            }
            else{
//                layoutPush.setVisibility(View.GONE);
//                header.setBackgroundColor(Color.parseColor("#6A82FB"));
//                pushNotificationButton.setVisibility(View.GONE);
            }
        }
        root = findViewById(R.id.content_main_app);
        layoutMap = findViewById(R.id.layout_map);
        layoutMap.setOnClickListener(this);
        layoutChat = findViewById(R.id.layout_chat);
        layoutChat.setOnClickListener(this);
        layoutVid = findViewById(R.id.layout_videos);
        layoutVid.setOnClickListener(this);
        layoutRes = findViewById(R.id.layout_results);
        layoutRes.setOnClickListener(this);
        layoutTime.setOnClickListener(this);
        layoutPush.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(getFragmentManager().getBackStackEntryCount()>0) {
            root.setBackgroundColor(Color.WHITE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getFragmentManager().popBackStack();
            root.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if(id == R.id.nav_about){
            Intent browserAboutIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pce.ac.in"));
            if(isInternetOn())
                startActivity(browserAboutIntent);
            else{
                Toast.makeText(MainActivity.this,"Not connected to internet",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id == R.id.nav_contact){
            try{String uriText = "mailto:abhijaynair9@gmail.com" +
                    "?subject=" + URLEncoder.encode("PCE Indicator Feedback","UTF-8");
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                startActivity(Intent.createChooser(sendIntent, "Send Email"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(id == R.id.nav_news){
             startActivity(new Intent(MainActivity.this,NewsActivity.class));
         }
        else if(id == R.id.nav_signout){
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public final boolean isInternetOn() {

        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_map:
//                Toast.makeText(MainActivity.this,"To be added",Toast.LENGTH_SHORT).show();

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_contain,new MapFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.layout_results:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://203.115.126.37/pceonline/PIITResult.aspx"));
                if(isInternetOn())
                    startActivity(browserIntent);
                else{
                    Toast.makeText(MainActivity.this,"Not connected to internet",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_videos:
                if(isInternetOn())
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/channel/UCfqP3NEaaXPsgHn8QX3qilw")));
                else{
                    Toast.makeText(MainActivity.this,"Not connected to internet",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_chat:
                startActivity(new Intent(MainActivity.this,ChatActivity.class));
                break;
            case R.id.layout_time:
                startActivity(new Intent(MainActivity.this,TimeTable.class));
                break;
            case R.id.layout_pushNoti:
                startActivity(new Intent(MainActivity.this,PushNotifications.class));
                break;
        }
    }
}
