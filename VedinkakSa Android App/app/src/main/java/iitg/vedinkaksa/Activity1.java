package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

// Dummy activity for testing screen touch events and pressure sensor values
public class Activity1 extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Activity1", "Activity1 being called");
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
//        Log.i("Activity1", "screen touched: " + event.toString());
//        return false;
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i("Activity1", "screen 1 touched: " + motionEvent.toString());
        Log.i("Activity1", "screen 1 pressure: " + motionEvent.getPressure());
        Log.i("Activity1", "screen 1 area: " + motionEvent.getSize());
        return false;
    }


    @Override
    public void onClick(View view) {
        Log.i("Activity1", "screen 1 clicked: ");
//        return false;
    }
}
