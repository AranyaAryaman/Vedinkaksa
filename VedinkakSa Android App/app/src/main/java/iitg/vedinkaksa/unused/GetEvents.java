package iitg.vedinkaksa.unused;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import iitg.vedinkaksa.R;

// Get touch events for the touch model
public class GetEvents extends Fragment implements View.OnTouchListener {

    int count = 0;
    View mDummyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mDummyView = inflater.inflate(R.layout.activity_get_events, container, false);
        mDummyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("Fragment", "event.x " + event.getX());
                Log.d("Fragment", "event.y " + event.getY());
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
                return false;
            }
        });
        WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows, but only for the current user
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                // Don't let it grab the input focus
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);

// Define the position of the window within the screen
        p.gravity = Gravity.TOP | Gravity.RIGHT;
        p.x = 0;
        p.y = 100;

        Log.i("Fragment", "created");
        return mDummyView;
    }

    // this function is called whenever anything on the screen is touched. This is used to maintain the global touch counts
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        count++;
        Log.d("Fragment", "on touch event");
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment", "paused");
    }
}
