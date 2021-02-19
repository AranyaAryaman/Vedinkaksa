package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * This activity is presenting student activity
 */
public class StudentActivity extends AppCompatActivity {

    private String rollNo;
    public int counter = 1;
    // api address for recording attendance
    public static final String URL_ATNDC = "http://" + Constants.debug + "/vishal/attendance_api/addatt.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        ACTION.Action = "STUDENT MAIN SCREEN";
        Intent intent = getIntent();
        // getting roll number from login activity
        rollNo = intent.getStringExtra("roll_id");

        if (getIntent().hasExtra("aKey")) {

            TextView textView = findViewById(R.id.username);
            textView.setText("You are logged in as " + getIntent().getStringExtra("aKey"));
        }
        saveLog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.isKeyboardOn = false;
    }

    // function to record attendance
    public void saveLog() {

        // taking current date and time
        Date now = new Date();
        long timestamp = now.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss", Locale.US);
        // storing the time-stamp in dateStr string
        String dateStr = sdf.format(timestamp);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        final String total_attendance = String.valueOf(counter);
        final String last_login = dateStr;

        // STORING roll number to roll varialbe
        final String roll = rollNo;

        class Attendance extends AsyncTask<Void, Void, String> {

            ProgressDialog pdLoading = new ProgressDialog(StudentActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler res = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("roll_id", roll); // sending roll number to database
                params.put("last_login", last_login); // adding last login detail to databse
                //returning the response
                return res.sendPostRequest(URL_ATNDC, params); // returning the api url and parameter


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentActivity.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }

        }

        Attendance prod_exec = new Attendance();
        prod_exec.execute();
    }

    public void studentexamintion(View view) {
        startActivity(new Intent(this, QuizStudents.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent k = new Intent(StudentActivity.this, LoginActivity.class);
        startActivity(k);
        finish();

    }

    public void studentclassroom(View view) {

        startActivity(new Intent(this, StudentSlides.class));

    }

    public void studentquery(View view) {

        startActivity(new Intent(this, StudentQueryActivity.class));

    }


    public void studentattendance(View view) {

        startActivity(new Intent(this, StudentAttendanceActivity.class));

    }

    public void classnotes(View view) {

        startActivity(new Intent(this, StudentClassnotes.class));

    }

    public void logout(View view) {

        startActivity(new Intent(this, LoginActivity.class));

    }
}