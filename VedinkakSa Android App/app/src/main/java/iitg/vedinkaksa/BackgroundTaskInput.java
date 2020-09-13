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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/** This class is used for querying the data from the database*/
public class BackgroundTaskInput extends AsyncTask<Integer, Void, String> implements GLOBAL {

    @SuppressLint("StaticFieldLeak")
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private LinearLayout view;

    BackgroundTaskInput(Context context, LinearLayout view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected String doInBackground(Integer... params) {
        // JSON string for reading the lines
        String JSON_STRING;

        try {
            // Connection to the URL
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(GET_DATA_QUERY_URL).openConnection();
            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == 200) {

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

        return params[0].toString();
    }

    @Override
    protected void onPostExecute(String result) {
        // Updating the view
        view.removeAllViews();

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(linLayoutParam);
        linearLayout.removeAllViews();

        view.addView(linearLayout);

        try {

            JSONArray jsonArray = new JSONObject(result).optJSONArray(SERVER_RESPONSE);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                final String query = jsonObject.optString("query");
                final String id1 = jsonObject.optString("q_id");
                final String answer = jsonObject.optString("answer");
                int likes = Integer.parseInt(jsonObject.optString("likes"));

                ACTION.CURRENT = Integer.parseInt(jsonObject.optString("counter"));

                // Creating new query to add in the list.
                TextView textView = new TextView(context);
                textView.setText(query);

                textView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        ACTION.Action = "VIEWING QUERY";

                        Intent i = new Intent(context, AnswerView.class);
                        i.putExtra("q_id", id1);
                        i.putExtra("query", query);
                        i.putExtra("answer", answer);

                        context.startActivity(i);
                        System.exit(1);
                    }
                });

                linearLayout.addView(textView);

                // Creating a new button to like to add in the list
                Button button = new Button(context);
                button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                button.setText(likes + "");
                button.setTextSize(5);
                button.setId(i);
                button.setBackgroundResource(R.drawable.like);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ACTION.Action = "Liking Query";
                        new BackgroundTaskOutput().execute(GIVE_LIKE_QUERY_URL, id1);
                    }
                });

                linearLayout.addView(button);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
