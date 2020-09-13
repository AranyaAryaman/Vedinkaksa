package iitg.vedinkaksa;
/*
 * Coded by - Abhsihek Kuamr
 * This code will recieve the json string that we downloaded.
 * Here we parse this particular json, filling an arraylist.
 * Then we bind our arraylist of data our view component to Visualisation_CustomAdapter
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParser extends AsyncTask<Void, Void, Integer> {

    Context c;
    String jsondata;
    GridView gv;


    ProgressDialog pd;
    ArrayList<Visualisation_student_table> student_tbd = new ArrayList<>();

    public DataParser(Context c, String jsondata, GridView gv) {
        this.c = c;
        this.jsondata = jsondata;
        this.gv = gv;

    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parsedata();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing..");
        pd.show();

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();

        if (result == 0) {
            Toast.makeText(c, "Unable to Parse", Toast.LENGTH_SHORT).show();
        } else {
            //bind data to gridview
            Visualisation_CustomAdapter adapter = new Visualisation_CustomAdapter(c, student_tbd);
            gv.setAdapter(adapter);

        }

    }

    private int parsedata() {

        try {
            JSONArray ja = new JSONArray(jsondata);
            JSONObject jo = null;

            student_tbd.clear();
            Visualisation_student_table student_table_data;

            for (int i = 0; i < ja.length(); i++) {

                jo = ja.getJSONObject(i);
                int id = jo.getInt("id");
                String State_in = jo.getString("State_in");
                String image_url = jo.getString("image_url");
                Integer PosX = jo.getInt("PosX");
                Integer PosY = jo.getInt("PosY");

                student_table_data = new Visualisation_student_table();
                student_table_data.setId(id);
                student_table_data.setState_in(State_in);
                student_table_data.setImage_url(image_url);
                student_table_data.setPosX(PosX);
                student_table_data.setPosY(PosY);

                student_tbd.add(student_table_data);

            }

            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}