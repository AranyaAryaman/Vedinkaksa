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
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/** This activity is presenting student notes */
public class StudentNotes extends AppCompatActivity implements GLOBAL {

    LinearLayout linearLayout;
    String json_result;
    String data;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_student_notes);
        new NoteActivitiesRetrival().execute();
        linearLayout = findViewById(R.id.ll);
    }


    @SuppressLint("StaticFieldLeak")
    class NoteActivitiesRetrival extends AsyncTask<Void, Void, String> {

        String json_url = NOTE_ACTIVITIES_QUERY_URL, JSON_STRING;

        @Override
        protected String doInBackground(Void... params) {
            int response;

            try {

                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(json_url).openConnection();
                response = httpURLConnection.getResponseCode();

                if (response == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((JSON_STRING = bufferedReader.readLine()) != null) {

                        stringBuilder.append(JSON_STRING).append("\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return stringBuilder.toString().trim();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONArray jsonArray = new JSONObject(result).optJSONArray("server_response");
                data = "";

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    final String notes_id = jsonObject.optString("notes_id");
                    final String s_id = jsonObject.optString("s_id");
                    final String start_time = jsonObject.optString("start_time");
                    final String stop_time = jsonObject.optString("stop_time");

                    data += notes_id + ". " + s_id + "   " + start_time + "   " + stop_time + "\n\n";
                }

                TextView tv = new TextView(context);
                tv.setWidth(R.id.wrap_content);
                tv.setText(data);

                linearLayout.addView(tv);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(View view) {

        linearLayout.removeAllViews();
        data = "";
        new NoteActivitiesRetrival().execute();
    }
}
