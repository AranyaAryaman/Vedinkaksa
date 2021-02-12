package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

// Demo Activity for features that have not yet been implemented
public class AutomatedActivity extends AppCompatActivity {

    private RequestQueue queue;
    private Button playButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automated);

        playButton = findViewById(R.id.start);
        playButton.setVisibility(View.VISIBLE);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                playButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
            }
        });

        stopButton = findViewById(R.id.stop);
        stopButton.setVisibility(View.GONE);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                stopButton.setVisibility(View.GONE);
                playButton.setVisibility(View.VISIBLE);
            }
        });

        queue = Volley.newRequestQueue(this);
    }

    public void start_attendance(View view){
        Log.d("attendanceStart", "Starting Attendance");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ATTENDANCE_START,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("attendanceStart", "We got Response");
                        Log.d("attendanceStart", response);

                        Toast.makeText(AutomatedActivity.this, "Started Attendance", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("attendanceStart", "failure :(");
            }
        });
    }


}
