package iitg.vedinkaksa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

// This class implements the PDF viewer and its core functionalities :- slide synchronisation and query synchronisation
public class PdfViewer extends AppCompatActivity {
    Intent intent;
    PDFView pdfView;
    Button askQuestion;
    ExpandableListView queryView1;
    public String currentSlideUrl = "";
    public HashMap<String, HashMap<String, String>> queries;
    public ArrayList<String> sortedQuery;
    public QueryAdapter queryAdapter;
    public int currentSlidePageNo = 0;
    RequestQueue requestQueue;
    String dummyTime = "0001-01-01 01:01:01";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public Date lastQuestionAdded;
    public Date lastAnswerAdded;
    public int noOfExpandedQues = 0;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        intent = getIntent();
        fileName = intent.getStringExtra("name");
        Log.d("Vinit", fileName);
        queries = new HashMap<>();
        final queryComparator<String> qc = new queryComparator<>();
        sortedQuery = new ArrayList<String>() {
            public boolean add(String mt) {
                int index = Collections.binarySearch(this, mt, qc);
                if (index < 0) index = ~index;
                super.add(index, mt);
                return true;
            }
        };
        try {
            lastQuestionAdded = df.parse(dummyTime);
            lastAnswerAdded = df.parse(dummyTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pdfView = findViewById(R.id.PdfView);
        askQuestion = findViewById(R.id.askQuestion);
        if (!intent.getStringExtra("inputType").equals("dynamic")) {
            askQuestion.setVisibility(View.GONE);
        }

        queryView1 = findViewById(R.id.ExpandableQueryView);
        queryAdapter = new QueryAdapter(this, sortedQuery, queries, intent.getStringExtra("inputType"));
        queryView1.setAdapter(queryAdapter);
        queryView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                noOfExpandedQues += 1;
                timerHandler.removeCallbacks(timerRunnable1);
                Log.i("Query", groupPosition + ", " + queries.get(sortedQuery.get(groupPosition)).get("answer") + ", " + queries.get(sortedQuery.get(groupPosition)).get("question"));
            }
        });

        queryView1.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                noOfExpandedQues -= 1;
                if (noOfExpandedQues == 0) {
                    timerHandler.postDelayed(timerRunnable1, 5000);
                }
            }
        });

        queryView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        sortedQuery.get(groupPosition)
                                + " -> "
                                + queries.get(sortedQuery.get(groupPosition)).get("answer"), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.isKeyboardOn = false;
        showPdf();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_visualisation, menu);

        if (!Constants.teacher) {
            menu.findItem(R.id.visualisation).setVisible(false);
        } else {
            menu.findItem(R.id.notes).setVisible(false);
        }

        return true;
    }

    // Create an overflow menu for notes and visualisation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.visualisation:
                openVisualisation();
                return true;
            case R.id.notes:
                openNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Open the visualisation pane for the teach
    private void openVisualisation() {
        startActivity(new Intent(PdfViewer.this, DynamicVisualization.class));
    }

    // Open the notes pane for the student to start taking notes
    void openNotes() {
        startActivity(new Intent(PdfViewer.this, StudentClassnotes.class).putExtra("slideName", fileName));
    }

    // Create the PDF viewer according to the use case
    private void showPdf() {
        String inputType = intent.getStringExtra("inputType");
        Log.i("file", "inputType : " + inputType);
        final String uri, name;
        switch (inputType) {
            // in case the teacher opens the slide.
            case "uriTeacher":
                uri = intent.getStringExtra("uri");
                name = intent.getStringExtra("name");
                Log.i("file", "Uri: " + uri);
                currentSlideUrl = uri;
                // update the current page number on the DB whenever the teacher changes the slide page
                OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(final int page, int pageCount) {
                        Log.i("pdf", "page changed to " + page + " pageCount: " + pageCount);
                        currentSlidePageNo = page;
                        clearQueryinfo();
                        updateServer(name, String.valueOf(page));
                    }
                };
                pdfView.fromUri(Uri.parse(uri))
                        .onPageChange(onPageChangeListener)
                        .swipeHorizontal(true)
                        .pageFling(true)
                        .load();
                timerHandler.postDelayed(timerRunnable1, 1000);
                break;
            // in case the slide is present in the internal storage
            case "uri":
                uri = intent.getStringExtra("uri");
                Log.i("file", "Uri: " + uri);
                pdfView.fromUri(Uri.parse(uri))
                        .swipeHorizontal(true)
                        .pageFling(true)
                        .load();
                break;
            // in case the slide is not present in the internal memory and is being streamed from the server
            case "url":
                String url = intent.getStringExtra("url");
                if (url == null) {
                    break;
                }
                new showPdfromUrl().execute(url);
                break;
            // in case the class is already in progress and the student opens the slide
            case "dynamic":
                timerHandler.postDelayed(timerRunnable, 5000);
                break;
        }
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            updateSlide();
            timerHandler.postDelayed(this, 5000);
        }
    };

    Runnable timerRunnable1 = new Runnable() {

        @Override
        public void run() {
            updateQuery();
            timerHandler.postDelayed(this, 5000);
        }
    };

    // create a server request for updating the current slide value
    private void updateServer(final String name, final String page) {
        String update_url = "http://" + Constants.debug + "/CoreFunctionality/postCurrentSlide.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, update_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("pdf", "page changed to " + page + " Response: success");
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.i("Volley", "error in updateServer->MyStringRequest");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", name);
                MyData.put("page", String.valueOf(page));
                Log.i("slides", "name: " + name + " page: " + page);
                return MyData;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PdfViewer.this);
        }
        requestQueue.add(MyStringRequest);
    }

    // Clear the query list adapter
    private void clearQueryinfo(boolean reset) {
        queries.clear();
        sortedQuery.clear();
        queryAdapter.notifyDataSetChanged();
        Log.i("Cleardata", sortedQuery.toString());
    }

    private void clearQueryinfo() {
        try {
            lastQuestionAdded = df.parse(dummyTime);
            lastAnswerAdded = df.parse(dummyTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        queries.clear();
        sortedQuery.clear();
        queryAdapter.notifyDataSetChanged();
        Log.i("Cleardata", sortedQuery.toString());
    }

    // In case the teacher is presenting the slide, this function updates the slide, and the queries corresponding to that slide.
    private void updateSlide() {
        Log.i("timer", "checking slide update, currentslide: " + currentSlideUrl + " page: " + currentSlidePageNo);
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
                    path = path.replace("localhost/Sites/Server", Constants.debug);
                    pageNo = json.getInt("page");
                    if (!name.equals("") && !path.equals(currentSlideUrl)) {
                        currentSlideUrl = path;
                        currentSlidePageNo = pageNo;
                        clearQueryinfo();
                        new showPdfromUrl().execute(currentSlideUrl);
                        pdfView.jumpTo(pageNo);
                    } else if (!name.equals("") && pageNo != currentSlidePageNo) {
                        currentSlidePageNo = pageNo;
                        clearQueryinfo();
                        pdfView.jumpTo(pageNo);
                    } else if (name.equals("")) {
                        finish();
                    }
                    updateQuery();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "error in updateSlide->getCurrentSlideUrl");
            }
        });
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PdfViewer.this);
        }
        requestQueue.add(stringRequest);
    }

    // display the PDF from the URL
    class showPdfromUrl extends AsyncTask<String, Void, Void> {
        InputStream inputStream;

        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];
            try {
                inputStream = new URL(url).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pdfView.fromStream(inputStream)
                    .swipeHorizontal(true)
                    .pageFling(true)
                    .load();
        }
    }

    // Custom comparator for sorting the queries based on like count
    public class queryComparator<String> implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int like1 = Integer.valueOf(queries.get(s1).get("likes"));
            int like2 = Integer.valueOf(queries.get(s2).get("likes"));
            if (like1 < like2) {
                return 1;
            } else if (like1 > like2) {
                return -1;
            } else {
                return s1.toString().compareTo(s2.toString());
            }
        }
    }

    String getSlideInfo(String slideUrl) {
        String[] slideInfos = slideUrl.trim().split("/");
        for (int i = 0; i < slideInfos.length; i++) {
            Log.i("array", i + " " + slideInfos[i]);
        }
        return slideInfos[slideInfos.length - 1];
    }

    // this function is called whenever the student adds a question
    public void askQuestion(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please ask your question");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                final String question = input.getText().toString();
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("question", question);
                    jsonBody.put("page", "1");
                    jsonBody.put("slide", "5");
                    final String requestBody = jsonBody.toString();

                    String askQuesUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/sendQuestion.php";
                    // Create a POST request to add the question to the server
                    StringRequest askQues = new StringRequest(Request.Method.POST, askQuesUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            timerHandler.postDelayed(timerRunnable1, 100);
                            Toast.makeText(PdfViewer.this, "Question Saved", Toast.LENGTH_SHORT).show();
                            Log.i("VOLLEY", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Queries", "error in getAllQuery");
                            Toast.makeText(PdfViewer.this, "Connection error: Try Again", Toast.LENGTH_LONG).show();
                            Log.i("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public Map<String, String> getParams() {
                            Map<String, String> reqMap = new HashMap<>();
                            reqMap.put("question", question);
                            reqMap.put("page", String.valueOf(currentSlidePageNo));
                            reqMap.put("slide", getSlideInfo(currentSlideUrl));

                            Log.e("request", "login" + reqMap);

                            return reqMap;
                        }
                    };
                    if (requestQueue == null) {
                        requestQueue = Volley.newRequestQueue(PdfViewer.this);
                    }
                    requestQueue.add(askQues);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // update the query dashboard with new questions, answers, and updated like cound
    private void updateQuery() {
        Log.i("Update Query", df.format(lastQuestionAdded) + ", " + dummyTime);

        if (df.format(lastQuestionAdded).equals(dummyTime)) {
            Log.i("Update Query", "getAllQuery()");
            getAllQuery();
        } else {
            Log.i("Update Query", "getNewQuery()");
            getNewQueries();
            getLikes();
            getAnsweredQueries();
        }
    }

    // get all the queries, and correspoding answers and like counts, that have been posted so far for the current page
    private void getAllQuery() {
        String getAllQueryUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/getAllQuery.php";
        StringRequest getAllQuery = new StringRequest(Request.Method.POST, getAllQueryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("print VOLLEY", "getAllQuery " + response);
                try {
                    String question, qid, likes, timestamp, isAnswered;
                    if (response.equals("")) {
                        return;
                    }
                    clearQueryinfo(false);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        qid = json.getString("q_id");
                        question = json.getString("query");
                        likes = json.getString("likes");
                        timestamp = json.getString("timestamp");
                        isAnswered = json.getString("is_answered");
                        HashMap<String, String> queryAttributes = new HashMap<>();
                        queryAttributes.put("qid", String.valueOf(qid));
                        queryAttributes.put("question", String.valueOf(question));
                        queryAttributes.put("likes", String.valueOf(likes));
                        queryAttributes.put("timestamp", String.valueOf(timestamp));
                        queryAttributes.put("isAnswered", String.valueOf(isAnswered));
                        if (json.has("answer")) {
                            queryAttributes.put("answer", json.getString("answer"));
                            try {
                                Date answerAdded = df.parse(json.getString("answer_added"));
                                if (answerAdded.compareTo(lastAnswerAdded) > 0) {
                                    lastAnswerAdded = answerAdded;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
//                            queryAttributes.put("answer", "No Answer Yet.");
                        }
                        try {
                            Date questionAdded = df.parse(json.getString("timestamp"));
                            if (questionAdded.compareTo(lastQuestionAdded) > 0) {
                                lastQuestionAdded = questionAdded;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        queries.put(qid, queryAttributes);
                        sortedQuery.remove(qid);
                        sortedQuery.add(qid);
                        queryAdapter.notifyDataSetChanged();
                        Log.i("Queries", qid + ", " + question);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        // In case of error
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Queries", "error in getAllQuery");
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> reqMap = new HashMap<>();
                reqMap.put("page", String.valueOf(currentSlidePageNo));
                reqMap.put("slide", getSlideInfo(currentSlideUrl));

                Log.e("request", "getAllQuery: " + reqMap);

                return reqMap;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PdfViewer.this);
        }
        requestQueue.add(getAllQuery);
    }

    // get queries, and correspoding answers and like counts, that have been posted, after the last fetch request time stamp
    private void getNewQueries() {
        String getNewQueryUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/getNewQueries.php";
        StringRequest getNewQueries = new StringRequest(Request.Method.POST, getNewQueryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Print Volley", "getNewQuery: " + response);
                try {
                    String question, qid, likes, timestamp, isAnswered;
                    if (response.equals("")) {
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        qid = json.getString("q_id");
                        question = json.getString("query");
                        likes = json.getString("likes");
                        timestamp = json.getString("timestamp");
                        isAnswered = json.getString("is_answered");
                        HashMap<String, String> queryAttributes = new HashMap<>();
                        queryAttributes.put("qid", String.valueOf(qid));
                        queryAttributes.put("question", String.valueOf(question));
                        queryAttributes.put("likes", String.valueOf(likes));
                        queryAttributes.put("timestamp", String.valueOf(timestamp));
                        queryAttributes.put("isAnswered", String.valueOf(isAnswered));
                        if (json.has("answer") && (json.getString("answer") != null)) {
                            queryAttributes.put("answer", json.getString("answer"));
                            try {
                                Date answerAdded = df.parse(json.getString("answer_added"));
                                if (answerAdded.compareTo(lastAnswerAdded) > 0) {
                                    lastAnswerAdded = answerAdded;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {
//                            queryAttributes.put("answer", "No Answer Yet.");
                        }
                        try {
                            Date questionAdded = df.parse(json.getString("timestamp"));
                            if (questionAdded.compareTo(lastQuestionAdded) > 0) {
                                lastQuestionAdded = questionAdded;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        queries.put(qid, queryAttributes);
                        sortedQuery.remove(qid);
                        sortedQuery.add(qid);
                        queryAdapter.notifyDataSetChanged();
                        Log.i("Queries", qid + ", " + question);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Queries", "error in getAllQuery");
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> reqMap = new HashMap<>();
                reqMap.put("page", String.valueOf(currentSlidePageNo));
                reqMap.put("slide", getSlideInfo(currentSlideUrl));
                reqMap.put("lastQuestionAdded", df.format(lastQuestionAdded));

                Log.e("request", "getNewQuery " + reqMap);

                return reqMap;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PdfViewer.this);
        }
        requestQueue.add(getNewQueries);
//        Volley.newRequestQueue(this).add(getNewQueries);
    }

    // update the like count for all the fetched questions on the user device
    private void getLikes() {
        String getLikesUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/getLikes.php";
        StringRequest getLikes = new StringRequest(Request.Method.POST, getLikesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String qid, likes;
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        qid = json.getString("q_id");
                        likes = json.getString("likes");
                        if (queries.get(qid) != null) {
                            HashMap<String, String> queryAttributes = queries.get(qid);
                            if (!queryAttributes.get("likes").equals(likes)) {
                                queryAttributes.put("likes", likes);
                                queries.put(qid, queryAttributes);
                                sortedQuery.remove(qid);
                                sortedQuery.add(qid);
                            }
                        }
                    }
                    queryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Queries", "error in getLikes");
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> reqMap = new HashMap<>();
                reqMap.put("page", String.valueOf(currentSlidePageNo));
                reqMap.put("slide", getSlideInfo(currentSlideUrl));

                Log.e("request", "login" + reqMap);

                return reqMap;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PdfViewer.this);
        }
        requestQueue.add(getLikes);
//        Volley.newRequestQueue(this).add(getLikes);
    }

    // Update the answers for all the fetched questions on the user device
    private void getAnsweredQueries() {
        String getAnsweredQueriesUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/getAnsweredQueries.php";
        StringRequest getAnsweredQueries = new StringRequest(Request.Method.POST, getAnsweredQueriesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String qid, answer;
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        qid = json.getString("q_id");
                        answer = json.getString("answer");
                        if (queries.get(qid) != null && answer != null) {
                            try {
                                Date answerAdded = df.parse(json.getString("answer_added"));
                                if (answerAdded.compareTo(lastAnswerAdded) > 0) {
                                    lastAnswerAdded = answerAdded;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            HashMap<String, String> queryAttributes = queries.get(qid);
                            queryAttributes.put("isAnswered", "1");
                            queryAttributes.put("answer", answer);
                            queries.put(qid, queryAttributes);
                        }
                    }
                    queryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Queries", "error in getAnsweredQueries");
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> reqMap = new HashMap<>();
                reqMap.put("page", String.valueOf(currentSlidePageNo));
                reqMap.put("slide", getSlideInfo(currentSlideUrl));
                reqMap.put("lastAnswerAdded", df.format(lastAnswerAdded));

                Log.e("request", "login" + reqMap);

                return reqMap;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(PdfViewer.this);
        }
        requestQueue.add(getAnsweredQueries);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        timerHandler.removeCallbacks(timerRunnable1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        String inputType = intent.getStringExtra("inputType");
        if (inputType.equals("uriTeacher")) {
            updateServer("", "0");
        }
    }
}
