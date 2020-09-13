package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/** This activity is presenting classroom for student */
public class StudentQueryActivity extends AppCompatActivity implements GLOBAL {
    public RequestQueue requestQ;
    public String query_url = "http://" + Constants.debug + "/CoreFunctionality/queryupload.php";
    public ArrayList<String> itemString = new ArrayList<String>();
    public String ansurl = "http://" + Constants.debug + "/CoreFunctionality/query.php";
    Button post;
    EditText edp;
    Context context;
    ListView lvs;
    String question;
    String stime;
    //Current and previous times are being calculated for window size.
    //If the difference between the two is 7 seconds, the file will be sent to server.
    int current_time = 0;                                 //Current time
    int previous_time = 0;                             //previous time
    int touch_c = 0;                                     //for counting seconds
    int eventCount = 0, eventCount_value = 0;         //for counting number of events
    float pressure = 0, add_pressure = 0, avg_pressure = 0;     //for calculating pressure
    Server server = new Server(); //Server class object
    private Timer AutoUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_query);
        startService(new Intent(getApplication(), service.class));
        post = findViewById(R.id.post);
        edp = findViewById(R.id.etq);
        lvs = findViewById(R.id.lvs);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        func();
        lvs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "sdhifhskfh", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT).show();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edp.getText().equals("")) {
                    stopService(new Intent(StudentQueryActivity.this, service.class));
                    finish();
                    Intent k = new Intent(StudentQueryActivity.this, StudentQueryActivity.class);
                    startActivity(k);
                } else
                    SendDataToServer();
                Toast.makeText(getBaseContext(), "Query sent to Professor", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void func() {
        JSONProcess2();
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemString);
        lvs.setAdapter(ad);
    }

    private void JSONProcess2() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ansurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jso = array.getJSONObject(i);
                        int votes = jso.getInt("votes");
                        String qu = jso.getString("query");
                        itemString.add("Query:\n" + qu + "\nvotes: " + votes);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (requestQ == null) {
            requestQ = Volley.newRequestQueue(this);
        }
        requestQ.add(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();

        stopService(new Intent(this, service.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, service.class));
    }


    public boolean onTouch(View v, MotionEvent event) {

        if (touch_c == 0) {
            previous_time = (int) System.currentTimeMillis();
        }

        touch_c = touch_c + 1; //counting seconds

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_MOVE) {
            eventCount++;                         //Counting number of events
            pressure = event.getPressure();     //pressure per event
            add_pressure = add_pressure + pressure;
        }

        current_time = (int) System.currentTimeMillis();

        eventCount_value = eventCount;
        avg_pressure = add_pressure / eventCount_value; //average pressure = total pressure / total event count

        if ((current_time - previous_time) >= 7000) {

            try {
                save();
                Toast.makeText(context, "Touch file", Toast.LENGTH_SHORT).show();
                server.uploadTouchFile(); //if time diff between current and previous times in 7 seconds, send touch file to server

                touch_c = 0;
                add_pressure = 0;
                eventCount = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean onTouchEvent(final MotionEvent event) {

        if (touch_c == 0) {
            previous_time = (int) System.currentTimeMillis();
        }

        touch_c = touch_c + 1; //counting seconds

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_MOVE) {
            eventCount++;             //Counting number of events
            pressure = event.getPressure();     //pressure per event
            add_pressure = add_pressure + pressure;
        }

        current_time = (int) System.currentTimeMillis();

        eventCount_value = eventCount;
        avg_pressure = add_pressure / eventCount_value; //average pressure = total pressure / total event count

        if ((current_time - previous_time) >= 7000) {

            try {
                save();
                server.uploadTouchFile(); //if time diff between current and previous times in 7 seconds, send touch file to server
                touch_c = 0;
                add_pressure = 0;
                eventCount = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    public void save() throws IOException {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        String filename = folder.toString() + "/" + Constants.student_id + "_touch" + ".csv";

        FileWriter fw = new FileWriter(filename, false);

        fw.append("Event Count");
        fw.append(',');
        fw.append("Avg Pressure");
        fw.append('\n');
        fw.append(String.valueOf(eventCount_value));
        fw.append(',');
        fw.append(String.valueOf(avg_pressure));
        fw.append('\n');
        fw.close();
    }


    @Override
    public void onBackPressed() {


        super.onBackPressed();
        finish();

    }

    public void SendDataToServer() {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {

                //String q=edp.getText().toString();
                @SuppressLint("WrongThread") String q = edp.getText().toString();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("query", q));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(query_url);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    edp.setText("");
                } catch (Exception e) {

                }

                return "Done";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                stopService(new Intent(StudentQueryActivity.this, service.class));
                finish();
                Intent i = new Intent(StudentQueryActivity.this, StudentQueryActivity.class);
                startActivity(i);


            }


        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }


}