package iitg.vedinkaksa;
/*
 * Coded by - Abhishek Kumar
 * This code is used to get data from TeacherV( rows and columns) and generate grid accordingly.
 * This code is set on Auto- refresh after every 5 seconds.
 * The grid that'll be generated will be fit-to-screen and non-scrollable irrespective of orientation.
 * DON'T MANIPULATE THE CODE ELSE THE FORMATION WILL BE EFFECTED.
 * */

import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class GridviewVisualisation extends AppCompatActivity implements GLOBAL {

    static final String DB_URL = GRIDVIEW_CONNECTION;                                  //IP of host (here it is vedinkakSa)
    private Timer AutoUpdate;
    public int ab;

    public static int w, h;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_visualisation);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        Constants.cc = 0;
        DisplayMetrics dm = new DisplayMetrics();                                                        //To get the height and width of screen.
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        w = dm.widthPixels;
        h = dm.heightPixels;
        Constants.ht = h;
        Constants.wd = w;
        func();
        TypedValue tv = new TypedValue();                                                                 //To get the height of Action Bar
        if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            float actionBarHeight = TypedValue.complexToDimension(tv.data, getResources().getDisplayMetrics());
            ab = (int) actionBarHeight;
            Constants.abb = ab;
        }
    }

    public void func()                                                                                      // To get the height of status bar
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);
        Constants.st = result;
    }

    @Override
    public void onPause() {

        super.onPause();
        AutoUpdate.cancel();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        AutoUpdate.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        AutoUpdate.cancel();
        Intent i = new Intent(this, Visualisation_screen.class);
        i.putExtra("selector", "NO");
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        AutoUpdate = new Timer();
        AutoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateGV();
                    }
                });
            }
        }, 0, 5000);

    }

    private void updateGV() {                                                                               //Setting the downloaded data into the gridview

        final GridView gv = (GridView) findViewById(R.id.gv);
        gv.setNumColumns(Constants.coll);
        new Downloader_one(GridviewVisualisation.this, DB_URL, gv).execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AutoUpdate.cancel();
            finish();
            startActivity(getIntent());
            Constants.rot += 1;

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AutoUpdate.cancel();
            finish();
            startActivity(getIntent());
            Constants.rot += 1;

        }
    }
}

