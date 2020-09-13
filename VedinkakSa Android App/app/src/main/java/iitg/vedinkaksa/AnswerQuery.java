package iitg.vedinkaksa;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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

import android.widget.ArrayAdapter;

// Used by the teacher to answer queries in the query dashboard
public class AnswerQuery extends AppCompatActivity {
    public RequestQueue requestQ;
    public ListView lv;
    public ArrayList<String> itemString = new ArrayList<String>();
    public String ansurl = "http://" + Constants.debug + "/CoreFunctionality/test/updateAnswer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_query);
        lv = findViewById(R.id.query_list);
        JSONProcess2();
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemString);
        lv.setAdapter(ad);
    }

    // update answer on the server
    private void JSONProcess2() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ansurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jso = array.getJSONObject(i);
                        int votes = jso.getInt("votes");
                        String qu = jso.getString("query");
                        itemString.add("Query:\n" + qu + "\nvotes: " + votes);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        if (requestQ == null) {
            requestQ = Volley.newRequestQueue(this);
        }
        requestQ.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(AnswerQuery.this, TeacherClassroom.class);
        startActivity(i);
    }
}

