package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;

/**
 * This activity is presenting login
 */
public class LoginActivity extends Activity1 implements GLOBAL {

    private static final String[] DUMMY_CREDENTIALS = new String[]{"student:student", "teacher:teacher"};
    private static final int MY_PERMISSIONS_REQUEST_1 = 0;
    String roll_check, pass_check, name_check, url_check;
    private RequestQueue requestQueue;
    String seatX, seatY;
    int qrscanned = 0;
    String seatnum;
    private EditText emailView;
    private EditText passwordView;
    private TextView seat;
    private Button qr;
    private TextView reg;
    int flag2 = 0;
    private IntentIntegrator qrScan;
    //  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermission();
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            pretask();
        }

        ACTION.Action = "LOGIN SCREEN";

        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        reg = findViewById(R.id.regis);
        qr = findViewById(R.id.scanqr);
        seat = findViewById(R.id.Seatno);
        qrScan = new IntentIntegrator(this);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                attemptLogin();

            }
        });
        reg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, UploadPics.class);
                startActivity(i);
            }
        });
        qr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailView.getText().toString().equals("") || passwordView.getText().toString().equals(""))
                    Toast.makeText(LoginActivity.this, "Please fill up all the credentials", Toast.LENGTH_SHORT).show();
                else
                    qrScan.initiateScan();
            }
        });
        findViewById(R.id.root).setOnTouchListener(this);
        Log.i("Pressure", "df");

        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
//                Log.i("Keyboard", "Key pressed " + event.toString());
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("Keyboard", "Done pressed");
                    if (event != null) {
                        Log.i("Keyboard", "Key pressed " + event.toString());
                    }
                    //Do something
                }
                return false;
            }
        };

        passwordView.setOnEditorActionListener(onEditorActionListener);

    }


    public void SendDataToServer(final String name, final String position, final String urlc) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String n = name;
                String p = position;
                String px = (seatX);
                String py = (seatY);
                String ul = urlc;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", n));
                nameValuePairs.add(new BasicNameValuePair("pos", p));
                nameValuePairs.add(new BasicNameValuePair("posXX", px));
                nameValuePairs.add(new BasicNameValuePair("posYY", py));
                nameValuePairs.add(new BasicNameValuePair("iurl", ul));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(Constants.QR_URL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submitted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission();
            } else {
                pretask();
                // Do as per your logic
            }

        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                //if qrcode has nothing in it
                if (result.getContents() == null) {
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    //if qr contains data
                    try {
                        //converting the data to json
                        JSONObject obj = new JSONObject(result.getContents());
                        //setting values to textviews


                        seatnum = obj.getString("X") + "/" + obj.getString("Y");

                        if (obj.has("name") && obj.get("name").equals("SUBCODE")) {
                            seat.setText(" QR scanned\n" + obj.getString("X") + '/' + obj.getString("Y"));

                            seatX = obj.getString("X");
                            seatY = obj.getString("Y");
                            qrscanned = 1;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        //if control comes here
                        //that means the encoded format not matches
                        //in this case you can display whatever data is available on the qrcode
                        //to a toast
                        //  Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
        pretask();
    }

    private void pretask() {
        // Checking Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_LOGS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.PACKAGE_USAGE_STATS) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.READ_LOGS,
                            Manifest.permission.CAMERA,
                    }, MY_PERMISSIONS_REQUEST_1);

            Toast.makeText(this, "Kindly Permit !", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.putExtra("inWhichCondition", 1);
            startActivityForResult(intent, 1);
        } else {
            createDirAndStartService();
        }

    }

    public void createDirAndStartService() {
        Log.i("permission", "permission granted");
        // Creating Direcotry
        File dir = new File(DIR);
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(PDF_FILE);
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(DATA_FILE);
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(NOTES_FILE);
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(SCREENSHOT_FILE);
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(SLIDES_FILE);
        if (!dir.exists())
            dir.mkdirs();

        // Starting Service
        startService(new Intent(getApplication(), service.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", grantResults.toString());
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    createDirAndStartService();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "You must give these permissions to proceed", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_1);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void JSONProcess2(String loginurl) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject jso = array.getJSONObject(0);

                    roll_check = jso.getString("roll");
                    pass_check = jso.getString("pass");
                    name_check = jso.getString("name");
                    url_check = jso.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Please check your network connectivity", Toast.LENGTH_SHORT).show();
            }
        });
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(stringRequest);
    }


    public void attemptLogin() {   //String temp=Constants.LOGIN_URL+"?rollcheck="+emailView.getText().toString();
        //JSONProcess2(temp);
        flag2 += 1;
        if (qrscanned == 1 && !emailView.getText().toString().startsWith("IITG")) {
            String temp = Constants.LOGIN_URL + "?rollcheck=" + emailView.getText().toString();
            JSONProcess2(temp);

            if (flag2 == 2) {
                if (emailView.getText().toString().equals(roll_check) && passwordView.getText().toString().equals(pass_check) && qrscanned == 1) {

                    String nm = emailView.getText().toString();
                    SendDataToServer(nm, seatnum, url_check);
                    Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();

                    Intent l = new Intent(LoginActivity.this, StudentActivity.class);
                    // Toast.makeText (this,"Welcome Roll No - "+emailView.getText ().toString (),Toast.LENGTH_LONG).show ();
                    l.putExtra("roll_id", emailView.getText().toString());
                    startActivity(l);
                    Constants.student_id = nm;
                    seat.setText("Press the QR code \nto scan\n(Only required for students)");
                    qrscanned = 0;

                } else if (emailView.getText().toString().equals(roll_check) && passwordView.getText().toString().equals(pass_check) && qrscanned == 0) {
                    Toast.makeText(this, "Scan QR", Toast.LENGTH_SHORT).show();
                } else if ((emailView.getText().toString().equals("Teacher") || emailView.getText().toString().equals("teacher")) && passwordView.getText().toString().equals("Teacher") || passwordView.getText().
                        toString().equals("Teacher")) {
                    Intent i = new Intent(LoginActivity.this, TeacherActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Log in failed.\n Please check the credentials.\n Please check your connectivity.", Toast.LENGTH_SHORT).show();
                }

                flag2 = 0;
            }
        } else if (emailView.getText().toString().startsWith("IITG") && (qrscanned == 1)) {
            Toast.makeText(this, "Faculties need not scan QR.\n Please click 'Log in' again.", Toast.LENGTH_SHORT).show();
            qrscanned = 0;
            qr.setVisibility(View.GONE);
            seat.setText("");
            String temp = Constants.LOGIN_URL + "?rollcheck=" + emailView.getText().toString();
            JSONProcess2(temp);
            if (emailView.getText().toString().equals(roll_check) && passwordView.getText().toString().equals(pass_check)) {

                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(LoginActivity.this, TeacherV.class);

//                Intent l = new Intent(LoginActivity.this, TeacherActivity.class);
                Constants.teacher = true;
                Constants.cc = 1;
                //  l.putExtra("selector", "YES");
                startActivity(l);

                qr.setVisibility(View.VISIBLE);
            }
        } else if (emailView.getText().toString().startsWith("IITG") && (qrscanned == 0)) {

            String temp = Constants.LOGIN_URL + "?rollcheck=" + emailView.getText().toString();
            JSONProcess2(temp);
            if (emailView.getText().toString().equals(roll_check) && passwordView.getText().toString().equals(pass_check)) {

                Constants.teacher = true;
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();

                Intent l = new Intent(LoginActivity.this, TeacherV.class);
                Constants.cc = 1;
                startActivity(l);


            }
        } else
            Toast.makeText(this, "Please scan a valid QR", Toast.LENGTH_SHORT).show();

    }


}


