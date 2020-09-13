package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentSlides extends AppCompatActivity {
    public ArrayList<String> names = new ArrayList<String>();
    public ArrayList<String> paths = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    RequestQueue requestQueue;
    ListView pdfList;

    // get the info for all the slides present on the server.
    // In case the class is going on, open the slide that is being presented
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_slides);
        pdfList = findViewById(R.id.pdfList);
        getSlidesInfo();
        // handler for opening a slide from the list of all the slides.
        pdfList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentSlides.this, PdfViewer.class);
                intent.putExtra("inputType", "url");
                intent.putExtra("url", paths.get(position));
                intent.putExtra("name", names.get(position));
                startActivity(intent);
            }

        });
        timerHandler.postDelayed(timerRunnable, 0);
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            getCurrentSlideInfo();
            timerHandler.postDelayed(this, 5000);
        }
    };

    // Check of the teacher is presenting or not.
    // In case a slide is being presented, open that slide.
    private void getCurrentSlideInfo() {
        Log.i("timer1", "checking slide update");
        String getCurrentSlideUrl = "http://" + Constants.debug + "/CoreFunctionality/getCurrentSlide.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getCurrentSlideUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    String name, path;
                    int pageNo;
                    JSONObject json = new JSONObject(response);
                    name = json.getString("name");
                    path = json.getString("path");
                    path = path.replace("localhost", Constants.debug);
                    pageNo = json.getInt("page");
                    if (!name.equals("")) {
                        Intent intent = new Intent(StudentSlides.this, PdfViewer.class);
                        intent.putExtra("inputType", "dynamic");
                        intent.putExtra("name", name);
                        intent.putExtra("url", path);
                        intent.putExtra("page", pageNo);
                        startActivity(intent);
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
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(stringRequest);
    }

    // get the info for all the slides that have been uploaded to the DB
    private void getSlidesInfo() {
        String slidesUrl = "http://" + Constants.debug + "/CoreFunctionality/fileList.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, slidesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        String name = json.getString("name");
                        String path = json.getString("path");
                        path = path.replace("localhost/Sites/Server", Constants.debug);
                        names.add(name);
                        paths.add(path);
                        Log.i("files", name + " " + path);
                    }
                    adapter = new ArrayAdapter<>(StudentSlides.this, android.R.layout.simple_list_item_1, names);
                    pdfList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.isKeyboardOn = false;
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }
}
