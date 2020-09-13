package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// Teacher side code for quiz module
public class QuizTeacher extends AppCompatActivity {

    //  addresss of apis
    public static final String URL_ADD_QUES = "http://" + Constants.debug + "/CoreFunctionality/quiz/add_ques.php";
    public static final String URL_DEL_PREVIOUS_QUES = "http://" + Constants.debug + "/CoreFunctionality/quiz/del_prev_ques.php";

    private EditText question;
    private EditText option1;
    private EditText option2;
    private EditText option3;
    private EditText option4;
    private EditText correctAnswer;

    private RequestQueue queue;

    private String url = "http://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        question = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        correctAnswer = findViewById(R.id.correctAnswer);

        queue = Volley.newRequestQueue(this);
    }

    public void clearFields() {
        question.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
        correctAnswer.setText("");
    }

    //Add Question from here
    public void add_question(View view) {
        final String questionText = question.getText().toString();
        final String option1Text = option1.getText().toString();
        final String option2Text = option2.getText().toString();
        final String option3Text = option3.getText().toString();
        final String option4Text = option4.getText().toString();
        final String correctAnswerText = correctAnswer.getText().toString();

        Log.d("quizGET", "Starting query");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.QUIZ_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("quizGET", "got Response");
                        Log.d("quizGET", response);

                        Toast.makeText(QuizTeacher.this, "Question added! \n please add more", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("quizGET", "failure :(");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("question_text", questionText);
                params.put("option1", option1Text);
                params.put("option2", option2Text);
                params.put("option3", option3Text);
                params.put("option4", option4Text);
                params.put("correct_answer", correctAnswerText);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        Log.d("quizGET", "sending request");
    }
}
