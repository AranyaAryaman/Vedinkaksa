package iitg.vedinkaksa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Flicker extends AppCompatActivity {
        //String Info;
       // TextView logger;
        final static int INTERVAL = 500;   // 1000=1sec
        private static View myView = null;
        boolean whichColor = true;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);    //will hide the title
            getSupportActionBar().hide();      // hide the title bar
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.flickerr);
            myView = (View) findViewById(R.id.LinearLayout3);
            myView.setBackgroundColor(Color.RED);    // set initial colour
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(INTERVAL);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateColor();
                        whichColor = !whichColor;
                    }
                }
            }).start();

        }
        private void updateColor() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (whichColor)
                        myView.setBackgroundColor(Color.RED);
                    else
                        myView.setBackgroundColor(Color.GREEN);
                }
            });
        }
}
