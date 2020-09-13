package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

// Menu for the teacher side quiz module
public class QuizTeacherMenu extends AppCompatActivity {

    private RequestQueue queue;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_teacher_menu);
        url = "";
        queue = Volley.newRequestQueue(this);
    }

    public void newQuiz(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.QUIZ_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startIntent();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                quizQuestion.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void startIntent() {
        startActivity(new Intent(this, QuizTeacher.class));
    }

    public void addQuestion(View view) {
        startActivity(new Intent(this, QuizTeacher.class));
    }

    public void removeQuestions(View view) {
        startActivity(new Intent(this, QuizTeacher.class));
    }

    public void viewQuiz(View view) {
        startActivity(new Intent(this, QuizView.class));
    }
}
