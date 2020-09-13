package iitg.vedinkaksa;
/*
 * Coded by - Abhsihek Kuamr
 * For downloading jsondata from network
 * Downloading will happen in background thread using Asynctask, meanwhile we'll show progress dialog.
 * After this, we'll send our jsondata to DataParser class to be parsed.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Downloader_one extends AsyncTask<Void, Void, String> {

    Context c;
    String urlAddress;
    GridView gv;


    ProgressDialog pd;

    public Downloader_one(Context c, String urlAddress, GridView gv) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.gv = gv;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Retrieve");
        pd.setMessage("Please..wait");
        pd.show();

    }

    @Override
    protected String doInBackground(Void... params) {

        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String jsondata) {
        super.onPostExecute(jsondata);
        pd.dismiss();

        if (jsondata == null) {
            Toast.makeText(c, "Unsuccessful.No Data Retrieved", Toast.LENGTH_SHORT).show();
        } else {

            //PARSE
            DataParser parser = new DataParser(c, jsondata, gv);
            parser.execute();
        }

    }

    private String downloadData() {
        // ESTABLISH CONNECTION

        HttpURLConnection con = Connector.connect(urlAddress);
        if (con == null) {

            return null;
        }

        InputStream is = null;
        try {
            //GET INPUT FROM STREAM

            is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            StringBuffer jsondata = new StringBuffer();
            if (br != null) {
                //READ

                while ((line = br.readLine()) != null) {
                    jsondata.append(line + "\n");
                }
                //RESOURCES CLOSED
                br.close();

            } else {
                return null;
            }
            //RETURN jsondata
            return jsondata.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
