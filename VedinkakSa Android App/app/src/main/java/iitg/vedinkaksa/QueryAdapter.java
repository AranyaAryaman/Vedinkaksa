package iitg.vedinkaksa;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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
import java.util.HashMap;
import java.util.Map;

// Custom adapter for implementing the query list in the query dashboard
public class QueryAdapter extends BaseExpandableListAdapter {
    public Context context;
    ArrayList<String> sortedQueries;
    HashMap<String, HashMap<String, String>> queries;
    RequestQueue requestQueue;
    String inputType;

    public QueryAdapter(@NonNull Context context, ArrayList<String> sortedQueries, HashMap<String, HashMap<String, String>> queries, String inputType) {
        this.context = context;
        this.sortedQueries = sortedQueries;
        this.queries = queries;
        this.inputType = inputType;
    }

    @Override
    public int getGroupCount() {
        return this.sortedQueries.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return this.sortedQueries.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.queries.get(this.sortedQueries.get(i)).get("answer");
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {
        // It's exactly same right now for both cases but some things needs to be changed, which will be done at some later point of time
        if (inputType.equals("uriTeacher")) {
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.query_list_row, viewGroup, false);
            }

            TextView query = view.findViewById(R.id.query);
            TextView likes = view.findViewById(R.id.likes);
            Button likeButton = view.findViewById(R.id.likeButton);
            likeButton.setFocusable(false);

            final String qid = sortedQueries.get(position);
            Log.i("Query Adapter", "pos: " + position + ", " + qid);
            if (queries.get(qid) != null) {
                final HashMap<String, String> queryAttributes = queries.get(qid);
                query.setText(queryAttributes.get("question"));
                Log.i("Query Adapter", "pos: " + position + ",  " + qid + ", " + queryAttributes.get("question"));
                final String noOfLikes = queryAttributes.get("likes");
                likes.setText(noOfLikes);
            }
        } else {
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.query_list_row, viewGroup, false);
            }

            TextView query = view.findViewById(R.id.query);
            TextView likes = view.findViewById(R.id.likes);
            Button likeButton = view.findViewById(R.id.likeButton);
            likeButton.setFocusable(false);

            final String qid = sortedQueries.get(position);
            Log.i("Query Adapter", "pos: " + position + ", " + qid);
            if (queries.get(qid) != null) {
                final HashMap<String, String> queryAttributes = queries.get(qid);
                query.setText(queryAttributes.get("question"));
                Log.i("Query Adapter", "pos: " + position + ",  " + qid + ", " + queryAttributes.get("question"));
                final String noOfLikes = queryAttributes.get("likes");
                likes.setText(noOfLikes);
                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int newNoOfLikes = Integer.valueOf(noOfLikes) + 1;
                        queryAttributes.put("likes", String.valueOf(newNoOfLikes));
                        updateLikes(qid);
                        sortedQueries.remove(qid);
                        sortedQueries.add(qid);
                        notifyDataSetChanged();
                    }
                });
            }
        }
        return view;
    }

    public void updateLikes(final String qid) {
        String updateLikesUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/updateLike.php";
        StringRequest updateLikes = new StringRequest(Request.Method.POST, updateLikesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                reqMap.put("q_id", qid);
                Log.e("request", "login" + reqMap);

                return reqMap;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        requestQueue.add(updateLikes);
    }

    void showDialog(final String qid, String initialAns) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage(queries.get(qid).get("question"));

        final EditText input = new EditText(this.context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        if (!initialAns.equals("null")) {
            input.setText(initialAns);
        }
        builder.setView(input);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String answer = input.getText().toString();
                queries.get(qid).put("answer", answer);
                Log.i("Query", "Answer: " + answer);
                updateAnswer(qid, answer);
                notifyDataSetChanged();
                Toast.makeText(context, "Answer Saved", Toast.LENGTH_SHORT).show();
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

    public void updateAnswer(final String qid, final String answer) {
        String updateAnswerUrl = "http://" + Constants.debug + "/CoreFunctionality/QuerySynchronization/updateAnswer.php";
        StringRequest updateAnswer = new StringRequest(Request.Method.POST, updateAnswerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                reqMap.put("q_id", qid);
                reqMap.put("answer", answer);
                Log.e("request", "login" + reqMap);

                return reqMap;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        requestQueue.add(updateAnswer);
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String qid = sortedQueries.get(i);
        if (inputType.equals("uriTeacher")) {
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.teacher_child_row, viewGroup, false);
            }
            final String answer = this.queries.get(this.sortedQueries.get(i)).get("answer");
            TextView answerView = view.findViewById(R.id.answer);
            Button answerQues = view.findViewById(R.id.answerQuestion);
            answerQues.setFocusable(false);
            Button editAnswer = view.findViewById(R.id.editAnswer);
            editAnswer.setFocusable(false);

            if (answer.equals("null")) {
                answerView.setVisibility(View.GONE);
                editAnswer.setVisibility(View.GONE);
                answerQues.setVisibility(View.VISIBLE);
            } else {
                answerView.setText(answer);
                answerQues.setVisibility(View.GONE);
                answerView.setVisibility(View.VISIBLE);
                editAnswer.setVisibility(View.VISIBLE);
            }
            answerQues.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(qid, answer);
                }
            });
            editAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(qid, answer);
                }
            });
            Log.i("Query", "Answer after change: " + queries.get(qid).get("answer"));
        } else {
            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.listchildrow, viewGroup, false);
            }
            String answer = this.queries.get(this.sortedQueries.get(i)).get("answer");
            TextView answerView = view.findViewById(R.id.answer);
            answerView.setText(answer);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}