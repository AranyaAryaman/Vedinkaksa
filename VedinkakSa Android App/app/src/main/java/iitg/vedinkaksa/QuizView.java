package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;

// Allows teacher to view all the quiz questions.
public class QuizView extends AppCompatActivity {

    private RequestQueue queue;
    private ArrayList<String> questions;
    private JSONArray questionList;
    private int size;
    ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);

        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.QUIZ_GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            questionList = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        size = questionList.length();
                        questions = new ArrayList<String>();

                        try {
                            for (int i = 0; i < size; i++) {
                                String test = questionList.getJSONObject(i).getString("question");
                                questions.add(test);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        setupList();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                quizQuestion.setText("That didn't work!");
            }
        });


        queue.add(stringRequest);

    }

    public void setupList() {
        adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                questions
        );

        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }
}
