package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends Activity {
    Switch mriderOrDriverSwitch;

    public void getStarted(View view){

        String riderOrDriver;
        if(mriderOrDriverSwitch.isSelected()){
            riderOrDriver="Driver";
        }else {
            riderOrDriver="Rider";
        }
        ParseUser.getCurrentUser().put("riderOrDriver",riderOrDriver);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("myApp","User Logged in");
                }
            }
        });

    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
      mriderOrDriverSwitch= (Switch) findViewById(R.id.riderOrDriverSwitch);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    getActionBar().hide();

      if(ParseUser.getCurrentUser() ==null) {
          ParseAnonymousUtils.logIn(new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                  if (e != null) {
                      Log.d("MyApp", "Anonymous login failed.");
                  } else {
                      Log.d("MyApp", "Anonymous user logged in.");
                  }
              }
          });
      }else if(ParseUser.getCurrentUser() !=null){
          Log.i("myApp","Redirect User");
      }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
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
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
