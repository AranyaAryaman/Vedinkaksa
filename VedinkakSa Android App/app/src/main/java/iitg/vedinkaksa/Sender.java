package iitg.vedinkaksa;
/**
 * Created by: Siddhi Joshi
 * This class is used to connect to the server and sending our typing and touch module files to the server.
 */
//Supports the register page

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class Sender extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAdress;
    EditText nameed, rolled, passed;
    String name, roll, pass;
    ProgressDialog pd;
    int flag = 0;

    public Sender(Context c, String urlAdress, EditText... editTexts) {
        this.c = c;
        this.urlAdress = urlAdress;
        this.nameed = editTexts[0];
        this.rolled = editTexts[1];
        this.passed = editTexts[2];

        name = nameed.getText().toString();
        roll = rolled.getText().toString();
        pass = passed.getText().toString();

    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending");
        pd.show();

    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String response) {
        pd.dismiss();
        if (response != null) {
            Toast.makeText(c, "" + response, Toast.LENGTH_LONG).show();

            nameed.setText("");
            rolled.setText("");
            passed.setText("");
            flag = 1;
        } else
            Toast.makeText(c, "Unsuccessful", Toast.LENGTH_SHORT).show();

    }

    private String send() {
        HttpURLConnection con = Connector.connect(urlAdress);
        if (con == null)
            return null;
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new DataPackager(name, roll, pass).packData());
            bw.flush();
            bw.close();
            os.close();
            int responseCode = con.getResponseCode();
            if (responseCode == con.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null)
                    response.append(line);

                br.close();
                return response.toString();
            } else {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
