package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

// Test quiz class. not in use anymore
public class QuizRemoveQuestions extends AppCompatActivity {

    private RequestQueue queue;
    private ArrayList<String> questions;
    private JSONArray questionList;
    private int size;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_remove_questions);

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
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                questions
        );
    }

    public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> list = new ArrayList<String>();
        private Context context;

        public MyCustomAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.quiz_list_item, null);
            }

            //Handle TextView and display string from your list
            TextView tvContact = (TextView) view.findViewById(R.id.tvContact);
            tvContact.setText(list.get(position));

            //Handle buttons and add onClickListeners
            Button delbtn = (Button) view.findViewById(R.id.delete);

            delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


            return view;
        }
    }
}
