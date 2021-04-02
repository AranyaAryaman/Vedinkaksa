package iitg.vedinkaksa;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.view.KeyEventDispatcher;

import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import static iitg.vedinkaksa.Constants.arousal;
import static iitg.vedinkaksa.Constants.classroom_activity;
import static iitg.vedinkaksa.Constants.emotional_state;
import static iitg.vedinkaksa.Constants.engagement;
import static iitg.vedinkaksa.Constants.involvement;
import static iitg.vedinkaksa.Constants.mental_state;
import static iitg.vedinkaksa.Constants.student_id;
import static iitg.vedinkaksa.Constants.valence;
import static iitg.vedinkaksa.Constants.visualisation_state;

/**
 * This acts as a background service for getting all the sensor values and event data, and proceeds to push these
 * to the server for the mental state calculation
 */
public class service extends Service implements View.OnTouchListener, SensorEventListener, GLOBAL {

    Handler handler = new Handler();
    public static final String TAG = service.class.getSimpleName();

    private SensorManager sensorManager;

    private Sensor light;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor gravity;
    private Sensor rotationVector;
    private Sensor gameRotationVector;
    private Sensor linearAcceleration;

    private float luminescence;

    private Runtime runtime;
    ActivityManager.MemoryInfo mi;

    private double[] raw_acclerometer = new double[3];
    private float[] raw_gyroscope = new float[3];
    private float[] raw_gravity = new float[3];
    private float[] raw_rotationVector = new float[3];
    private float[] raw_gameRotationVector = new float[3];
    private float[] raw_linearAcceleration = new float[3];

    //For counting shake value
    double prev_x = 0, prev_y = 0, prev_z = 0;
    double accelerometer_difference = 0;
    double constant_threshold = 0.4; //Shake frequency above is counted for shake count
    int SHAKE_COUNT = 0; //how many times shake frequency of device was above threshold

    //For Window size
    int define_window_size = 7; //Variable window size. if window size is to be changed, change the value of this variable.
    int window_size = define_window_size + 1;
    int window_count = 1;

    int prev_text_length = 0, prev_keys_pressed = 0; //previous values of text length and keys pressed.

    private String time;
    private String packagename;
    private String category;
    private long TotalMemory;
    private long FreeMemory;
    private float batteryTemperature;
    private float timestamp;
    private ActivityManager activityManager;

    private int delay = 1000 * 1;


    private int[] flags = new int[8];

    final Context context = this;

    private RequestQueue requestQueue = null;

    private Runnable touchRunnable = null;
    private Runnable typeRunnable = null;

    private Runnable showPopupRunnable = null;
    private Runnable hidePopupRunnable = null;

    private Runnable involvementRunnable = null;
    private Runnable stateRunnable = null;
    private Runnable accelerationRunnable = null;
    private double accelerationSum;
    private double gyroscopeSum;
    private int InvolvementTimer;

    private boolean prevStatusTouch = true;
    private boolean prevStatusType = false;

    AlertDialog alertDialog;

    int count = 0;
    int counter = 0;

    private WindowManager mWindowManager;
    // linear layout will use to detect touch event
    private LinearLayout touchLayout;

    Server server = new Server();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {

        super.onCreate();
        runtime = Runtime.getRuntime();
        mi = new ActivityManager.MemoryInfo();
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        InvolvementTimer = 0;
        accelerationSum = 0;

        // create an overlay on the screen in order to capture all the touch events.
        touchLayout = new LinearLayout(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
        touchLayout.setLayoutParams(lp);

        // fetch window manager object
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // set layout parameter of window manager
        LayoutParams params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new LayoutParams(
                    1, /* width */
                    1, /* height */
                    LayoutParams.TYPE_APPLICATION_OVERLAY,
                    LayoutParams.FLAG_NOT_FOCUSABLE |
                            LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSPARENT
            );
        } else {
            params = new LayoutParams(
                    1, /* width */
                    1, /* height */
                    LayoutParams.TYPE_PHONE,
                    LayoutParams.FLAG_NOT_FOCUSABLE |
                            LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSPARENT
            );
        }
        params.gravity = Gravity.LEFT | Gravity.TOP;
        touchLayout.setOnTouchListener(this);

        // start sending the sensor and event data of the device to the server for state calculation
        startTouchService();
        startTypeService();
        startStateService();
        startInvolvementService();

        mWindowManager.addView(touchLayout, params);
        Log.i("FragmentService", "add View");

        // Create the dialog box for the surprise popups
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you paying attention?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("service", "User is paying attention");
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("service", "User is not paying attention");
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        if (Constants.teacher == false) {
            handlePopup();
        }

        registerSensors();
    }

    // Return the total ram being used
    private double getTotalRAM() {

        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.availMem / 0x100000L;

        //Percentage can be calculated for API 16+
        double percentAvail = mi.availMem / (double) mi.totalMem * 100.0;

        double memUsed = (mi.totalMem / 0x100000L) - availableMegs;
        Log.i("RAM", "availableMegs: " + availableMegs + " percentAvail: " + percentAvail + " memUsed: " + memUsed);

        return memUsed;
    }

    // Return the amount of ram being used
    private double getRAM() {
        final long usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
        final long maxHeapSizeInMB = runtime.maxMemory() / 1048576L;
        final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;

        Log.i("RAM", "availHeapSizeInMB: " + availHeapSizeInMB + " maxHeapSizeInMB: " + maxHeapSizeInMB + " usedMemInMB: " + usedMemInMB);
        return usedMemInMB;
    }

    // Function to register the sensors to the service.
    private void registerSensors() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryInfoReceiver, intentFilter);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }

        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyroscope, 1000000, 1000000);
        }


    }

    /**
     * send the touch event data to the server, and
     * get the arousal and valence from the touch model.
     */
    public void touchRequest() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        String url = Constants.TOUCH;
        url += "?roll=" + Constants.student_id + "&noOfevents=" + Constants.no_of_touch + "&touchPressure=" + (Constants.no_of_touch != 0 ? (Constants.touch_pressure / Constants.no_of_touch) : 0);
        Constants.no_of_touch = 0;
        Constants.touch_pressure = 0;
        Log.i("touchService", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // textView.setText("Response is: "+ response.substring(0,500));

                        int stateResponse, arousalResponse, valenceResponse;
                        stateResponse = Integer.valueOf(response);
                        if (stateResponse == 1) {
                            arousalResponse = 1;
                            valenceResponse = 1;
                        } else if (stateResponse == 2) {
                            arousalResponse = 0;
                            valenceResponse = 1;
                        } else if (stateResponse == 3) {
                            arousalResponse = 0;
                            valenceResponse = 0;
                        } else {
                            arousalResponse = 1;
                            valenceResponse = 0;
                        }

                        arousal = arousalResponse;
                        valence = valenceResponse;

                        Log.i("touchService", response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("touchService", "touchservice error" + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    /**
     * send the required sensor and event data to the server, and
     * get the corresponding state from the involvement model.
     */
    public void involvementRequest() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        String url = Constants.INVOLVEMENT;
        url += "?roll=" + Constants.student_id + "&batteryTemp=" + batteryTemperature + "&avgMemory=" + getTotalRAM() + "&acceleration=" + (accelerationSum / 7) + "&rotationSpeed=" + gyroscopeSum;

        Log.i("involvmentService", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int stateResponse, involvementResponse;
                        stateResponse = Integer.valueOf(response);
                        if (stateResponse == 1) {
                            involvementResponse = 0;
                        } else {
                            involvementResponse = 1;
                        }

                        involvement = involvementResponse;

                        Log.i("involvmentService", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("involvmentService", "involvmentService error" + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
//        Log.i("touchService", "loop fdsfajk jfadlk ajfkl ");
    }

    // calculate the mental and visualisation states from the
    // valance, involvement, and arousal values.
    public void updateState() {
        if (engagement == 0) {
            mental_state = 1;
            visualisation_state = 4;
        } else if (classroom_activity == 1 && arousal == 1 && valence == 1) {
            mental_state = 2;
            visualisation_state = 1;
        } else if (classroom_activity == 1 && arousal == 1 && valence == 0) {
            mental_state = 3;
            visualisation_state = 2;
        } else if (classroom_activity == 1 && arousal == 0 && valence == 1) {
            mental_state = 4;
            visualisation_state = 1;
        } else if (classroom_activity == 1 && arousal == 0 && valence == 0) {
            mental_state = 5;
            visualisation_state = 1;
        } else if (classroom_activity == 0 && arousal == 1 && valence == 1) {
            mental_state = 6;
            visualisation_state = 3;
        } else if (classroom_activity == 0 && arousal == 1 && valence == 0) {
            mental_state = 7;
            visualisation_state = 2;
        } else if (classroom_activity == 0 && arousal == 0 && valence == 1) {
            mental_state = 8;
            visualisation_state = 1;
        } else if (classroom_activity == 0 && arousal == 0 && valence == 0) {
            mental_state = 9;
            visualisation_state = 1;
        }

        Log.d("StateUpdated", "new state: classroom activity: " + classroom_activity + " , arousal: " + arousal + " , valence: " + valence + " , mental_state: " + mental_state + " , visualisation_state: " + visualisation_state);
    }

    // send the current mental and visualisation states of the student to the server
    public void stateRequest() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.STATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("statePOST", "got Response");
                        Log.d("statePOST", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("statePOST", "failure :(");
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", String.valueOf(student_id));
                params.put("valence", String.valueOf(valence));
                params.put("arousal", String.valueOf(arousal));
                params.put("involvement", String.valueOf(involvement));
                params.put("classroom_activity", String.valueOf(classroom_activity));
                params.put("emotional_state", String.valueOf(emotional_state));
                params.put("mental_state", String.valueOf(mental_state));
                params.put("visualisation_state", String.valueOf(visualisation_state));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * send the typing event data to the server, and
     * get the arousal and valence from the typing model.
     */
    public void typeRequest() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        String url = Constants.TYPE;
        url += "?roll=" + Constants.student_id + "&typingSpeed=" + ((float) Constants.key_presses_without_bks / 7) + "&maxText=" + Constants.max_length + "&touchCount=" + Constants.touch_count + "&shakeCount=" + Constants.shk_freq;
        Log.i("typeService", url);
        Constants.key_presses_without_bks = 0;
        Constants.max_length = 0;
        Constants.touch_count = 0;
        Constants.shk_freq = 0;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // textView.setText("Response is: "+ response.substring(0,500));
                        int stateResponse, arousalResponse, valenceResponse;

                        stateResponse = Integer.valueOf(response);
                        if (stateResponse == 1) {
                            arousalResponse = 1;
                            valenceResponse = 1;
                        } else if (stateResponse == 2) {
                            arousalResponse = 0;
                            valenceResponse = 1;
                        } else if (stateResponse == 3) {
                            arousalResponse = 0;
                            valenceResponse = 0;
                        } else {
                            arousalResponse = 1;
                            valenceResponse = 0;
                        }

                        arousal = arousalResponse;
                        valence = valenceResponse;
                        Log.i("typeService", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // textView.setText("That didn't work!");
                Log.i("typeService", "typeservice error \n" + error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    // start sending required sensor and event data to the server (touch model) at regular intervals
    public void startTouchService() {
        if (touchRunnable == null) {
            touchRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!Constants.isKeyboardOn) {
                        if (prevStatusTouch) {
                            Log.i("touchService", "inside touchRunnable");
                            touchRequest();
                        } else {
                            prevStatusTouch = true;
                        }
                    } else {
                        prevStatusTouch = false;
                    }
                    handler.postDelayed(this, 5000);
                }
            };
            Log.i("touchService", "touchRunnable created");
            Log.i("touchService", "outside touchRunnable created");

            handler.postDelayed(touchRunnable, 5000);
        }
    }

    // start sending required sensor and event data to the server (involvement model) at regular intervals
    public void startInvolvementService() {
        if (involvementRunnable == null) {
            involvementRunnable = new Runnable() {
                @Override
                public void run() {

                    InvolvementTimer++;
                    accelerationSum += Math.sqrt(Math.pow((raw_acclerometer[0]), 2) + Math.pow((raw_acclerometer[1]), 2) + Math.pow((raw_acclerometer[2]), 2));
                    gyroscopeSum += Math.sqrt(Math.pow((raw_gyroscope[0]), 2) + Math.pow((raw_gyroscope[1]), 2) + Math.pow((raw_gyroscope[2]), 2));

                    if (InvolvementTimer == 7) {
                        involvementRequest();
                        InvolvementTimer = 0;
                        accelerationSum = 0;
                        gyroscopeSum = 0;
                    }

                    handler.postDelayed(this, 1000);
                }
            };
            Log.i("InvolvementService", "involvementRunnable created");
            Log.i("InvolvementService", "outside involvementRunnable created");

            handler.postDelayed(involvementRunnable, 1000);
        }
    }

    // start sending the mental and visualisation states of the user to the server at regular intervals
    public void startStateService() {
        if (stateRunnable == null) {
            stateRunnable = new Runnable() {
                @Override
                public void run() {
                    updateState();
                    stateRequest();
                    handler.postDelayed(this, 7000);
                }
            };
            Log.i("stateService", "stateRunnable created");
            Log.i("stateService", "outside stateRunnable created");

            handler.postDelayed(stateRunnable, 7000);
        }
    }

    // start sending required typing data to the server (typing model) at regular intervals
    public void startTypeService() {
        if (typeRunnable == null) {
            typeRunnable = new Runnable() {
                @Override
                public void run() {
                    if (Constants.isKeyboardOn) {
                        if (prevStatusType) {
                            Log.i("typeService", "inside typeRunnable");
                            typeRequest();
                        } else {
                            prevStatusType = true;
                        }
                    } else {
                        prevStatusType = false;
                    }
                    handler.postDelayed(this, 7000);
                }
            };
            Log.i("typeService", "typeRunnable created");
            Log.i("typeService", "outside typeRunnable created");

            handler.postDelayed(typeRunnable, 7000);
        }
    }

    // generate a random integer between low and high
    int generateRandom(int low, int high) {
        Random randGen = new Random();
        int rand = randGen.nextInt(high - low) + low;
        return rand;
    }

    // create the surprise popups at random intervals.
    void handlePopup() {
        if (showPopupRunnable == null) {
            showPopupRunnable = new Runnable() {
                @Override
                public void run() {
                    alertDialog.show();
                    int next = generateRandom(5, 15);
                    Log.i("popup", "next popup in " + next + " minutes");
                    // hide the popup after 5 seconds
                    handler.postDelayed(hidePopupRunnable, 5000);
                    handler.postDelayed(this, next * 60000);
                }
            };
        }
        if (hidePopupRunnable == null) {
            hidePopupRunnable = new Runnable() {
                @Override
                public void run() {
                    alertDialog.hide();
                }
            };
        }
        int next = generateRandom(5, 15);
        Log.i("popup", "next popup in " + next + " minutes");
        handler.postDelayed(showPopupRunnable, next * 60000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int count1 = 0;

    // get the number and corresponding touch pressure, for all the touch events
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        count1++;
        Log.i("touchService", "Count " + count1 + " " + motionEvent.getActionMasked());
        float x = motionEvent.getPressure();
        if (x != 0) {
            Constants.no_of_touch += 1;
            Constants.touch_pressure += x;
            Log.i("touch", "touch event");
            Log.i("touchPressure", String.valueOf(x));
        }
        Log.i("touchService", "touch area: " + motionEvent.getSize());
        return false;
    }

    // handler for extracting the accelerometer and gyroscope values
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {

            raw_acclerometer[0] = event.values[0];
            raw_acclerometer[1] = event.values[1];
            raw_acclerometer[2] = event.values[2];
            flags[2] = 1;

            accelerometer_difference = Math.sqrt(Math.pow((raw_acclerometer[0] - prev_x), 2) + Math.pow((raw_acclerometer[1] - prev_y), 2) + Math.pow((raw_acclerometer[2] - prev_z), 2));
            prev_x = raw_acclerometer[0];
            prev_y = raw_acclerometer[1];
            prev_z = raw_acclerometer[2];
            if (accelerometer_difference > constant_threshold) {
                SHAKE_COUNT++; //counting shake values
                Constants.shk_freq += 1;
            }
            //Log.i("Sensor Event", "accelerometer X: " + raw_acclerometer[0] + " Y: " + raw_acclerometer[1] + " Z: " + raw_acclerometer[2]);
        } else if (event.sensor == gyroscope) {
            raw_gyroscope[0] = event.values[0];
            raw_gyroscope[1] = event.values[1];
            raw_gyroscope[2] = event.values[2];
            flags[2] = 1;

            //Log.i("Sensor Event", "gyroscope X: " + raw_gyroscope[0] + " Y: " + raw_gyroscope[1] + " Z: " + raw_gyroscope[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Register the battery info receiver
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        sensorManager.unregisterListener(this, accelerometer);
        unregisterReceiver(batteryInfoReceiver);
        stopSelf();
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBatteryData(intent);
        }
    };

    // get the status of the device battery
    private void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        if (present) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            String healthlabel = "";

            switch (health) {

                case BatteryManager.BATTERY_HEALTH_COLD:

                    healthlabel = "COLD";
                    break;

                case BatteryManager.BATTERY_HEALTH_DEAD:

                    healthlabel = "DEAD";
                    break;

                case BatteryManager.BATTERY_HEALTH_GOOD:

                    healthlabel = "GOOD";
                    break;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:

                    healthlabel = "OVER VOLTAGE";
                    break;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:

                    healthlabel = "OVER HEAT";
                    break;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:

                    healthlabel = "FAILURE";
                    break;

                case BatteryManager.BATTERY_HEALTH_UNKNOWN:

                    healthlabel = "UNKNOWN";
                    break;

            }
        }

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if (level != -1 && scale != -1) {
            int batteryPercentage = (int) ((level / (float) scale) * 100f);
        }

        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        String pluggedLabel = "";

        switch (plugged) {

            case BatteryManager.BATTERY_PLUGGED_WIRELESS:

                pluggedLabel = "WIRELESS";
                break;

            case BatteryManager.BATTERY_PLUGGED_USB:

                pluggedLabel = "USB";
                break;

            case BatteryManager.BATTERY_PLUGGED_AC:

                pluggedLabel = "AC";
                break;

        }

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        String statusLabel = "";

        switch (status) {

            case BatteryManager.BATTERY_STATUS_CHARGING:

                statusLabel = "CHARGING";
                break;

            case BatteryManager.BATTERY_STATUS_DISCHARGING:

                statusLabel = "DISCHARGING";
                break;

            case BatteryManager.BATTERY_STATUS_FULL:

                statusLabel = "FULL";
                break;

            case BatteryManager.BATTERY_STATUS_UNKNOWN:

                statusLabel = "UNKNOWN";
                break;

            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:

                statusLabel = "NOT CHARGING";
                break;

        }

        if (intent.getExtras() != null) {
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
        }

        int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

        BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        long chargeCounter = 0;

        if (mBatteryManager != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            }
            long capacity = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            }
            long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
        }

        batteryTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10f;
        Log.i("Battery", String.valueOf(batteryTemperature));
        getRAM();
        getTotalRAM();
    }
}
