package iitg.vedinkaksa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Student side code for quiz module
public class QuizStudents extends Activity {
    private TextView quizQuestion; // question
    private RadioGroup radioGroup;
    private RadioButton optionOne;  // option1
    private RadioButton optionTwo;  // option2
    private RadioButton optionThree;  // option3
    private RadioButton optionFour;  // option4
    public int quizpointcounter = 0; // counting the correct number of selected answers

    private int current;
    private int score;
    private ArrayList<Integer> answers;
    private int size;
    private RequestQueue queue;
    private JSONArray questionList;

    private Button previousButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_students);

        quizQuestion = findViewById(R.id.quiz_question);

        radioGroup = findViewById(R.id.radioGroup);
        optionOne = findViewById(R.id.radio0);
        optionTwo = findViewById(R.id.radio1);
        optionThree = findViewById(R.id.radio2);
        optionFour = findViewById(R.id.radio3);

        previousButton = findViewById(R.id.previousquiz);
        nextButton = findViewById(R.id.nextquiz);

        previousButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);

        queue = Volley.newRequestQueue(this);
        current = 0;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.QUIZ_GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("quizPOST", response);
                        try {
                            questionList = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        size = questionList.length();

                        if (size == 0) {
                            quizQuestion.setText("No Quiz Yet!");
                            radioGroup.setVisibility(View.INVISIBLE);
                        } else {

                            previousButton.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.VISIBLE);
                            answers = new ArrayList<Integer>();
                            for (int i = 0; i < size; i++) {
                                answers.add(0);
                            }
                            setUpQuestion();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                quizQuestion.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void selectRadios() {
        if (answers.get(current) != 0) {
            int correct = answers.get(current);
            if (correct == 1) {
                optionOne.toggle();
            } else if (correct == 2) {
                optionTwo.toggle();
            } else if (correct == 3) {
                optionThree.toggle();
            } else {
                optionFour.toggle();
            }
        }
    }

    public void evaluate() {
        Log.d("evaluationstats", "starting evaluation");
        score = 0;
        try {
            for (int i = 0; i < size; i++) {
                if (questionList.getJSONObject(i).getInt("correct_answer") == answers.get(i)) {
                    score++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView textView = findViewById(R.id.score);
        textView.setText("Score: " + score + " out of " + size);

        nextButton.setVisibility(View.INVISIBLE);
        previousButton.setVisibility(View.INVISIBLE);
    }

    public void setUpQuestion() {
        JSONObject object = null;
        try {
            object = questionList.getJSONObject(current);
            quizQuestion.setText(object.getString("question"));
            optionOne.setText(object.getString("option1"));
            optionTwo.setText(object.getString("option2"));
            optionThree.setText(object.getString("option3"));
            optionFour.setText(object.getString("option4"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        uncheckedRadioButton();
        selectRadios();
    }

    private int getSelectedAnswer(int radioSelected) {

        int answerSelected = 0;
        if (radioSelected == R.id.radio0) {
            answerSelected = 1;
        }
        if (radioSelected == R.id.radio1) {
            answerSelected = 2;
        }
        if (radioSelected == R.id.radio2) {
            answerSelected = 3;
        }
        if (radioSelected == R.id.radio3) {
            answerSelected = 4;
        }
        return answerSelected;
    }


    private void uncheckedRadioButton() {
        radioGroup.clearCheck();
    }

    @Override
    public void onBackPressed() {
        quizpointcounter = 0;
        super.onBackPressed();
    }

    public void nextQuestionFunction(View view) {
        if (current == size) {
            evaluate();
        } else {
            int radioSelected = radioGroup.getCheckedRadioButtonId();
            int userSelection = getSelectedAnswer(radioSelected);

            answers.set(current, userSelection);
            current++;
            if (current == size) {
                nextButton.setText("Submit");
                quizQuestion.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
            } else {
                setUpQuestion();
            }
        }
    }

    public void prevQuestionFunction(View view) {
        if (current <= 0) {
            return;
        }

        if (current == size) {
            nextButton.setText("Next Question");
            quizQuestion.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);

        } else {
            int radioSelected = radioGroup.getCheckedRadioButtonId();
            int userSelection = getSelectedAnswer(radioSelected);
            answers.set(current, userSelection);
        }
        current--;
        setUpQuestion();
    }
}
