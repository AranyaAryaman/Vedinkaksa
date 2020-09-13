package iitg.vedinkaksa;
/*
 * Coded by - Abhsihek Kuamr
 * For the screen in which Teacher can generate their classroom size in a grid independently.
 * This class is common for both Symbol Based and Image Based.
 * New session will truncate the whole table data
 * And for a new set of students it will make a blank table
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherV extends AppCompatActivity implements GLOBAL {

    String ServerURL = VISUALISATION_QUERY_URL;
    EditText columntext, rowtext;
    Button btn;
    String column, row;
    String extra;
    ProgressDialog pd1;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_v);
        columntext = findViewById(R.id.et2);
        rowtext = findViewById(R.id.et1);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       GetData();
                                       extra = columntext.getText().toString();
                                       InsertData(column, row);
                                   }
                               }
        );
    }

    public void GetData() {                                                                              //Getting the data as rows and columns

        column = columntext.getText().toString();
        row = rowtext.getText().toString();
        Constants.rowss = Integer.parseInt(row);
        Constants.coll = Integer.parseInt(column);
    }

    public void InsertData(final String columntext, final String rowtext) {                               // For sending the input data to GridviewVisualisation

        class RequestAsynctask extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pd1 = new ProgressDialog(c);
                pd1.setTitle("Retrieve");
                pd1.setMessage("Please..wait");
                pd1.show();

            }

            @Override
            protected String doInBackground(String... params) {                                         // Sending the data to image_tbl in database for generation of columns.

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("column", columntext));
                nameValuePairs.add(new BasicNameValuePair("row", rowtext));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Data Insertion Successful";
            }

            @Override
            protected void onPostExecute(String jsondata) {                                                // Sending the data to GridviewVisualisation

                super.onPostExecute(jsondata);
                pd1.dismiss();
                if (jsondata != null) {
//                    Intent ib = new Intent(TeacherV.this, GridviewVisualisation.class);
                    Intent ib = new Intent(TeacherV.this, TeacherActivity.class);
                    startActivity(ib);
                    finish();
                }
            }
        }
        RequestAsynctask RequestAsynctask = new RequestAsynctask();

        RequestAsynctask.execute(column, row);
    }
}

