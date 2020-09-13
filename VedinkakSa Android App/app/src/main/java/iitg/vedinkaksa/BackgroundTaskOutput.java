package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/** This class is used for giving the data to the database */
public class BackgroundTaskOutput extends AsyncTask<String, Void, String> implements GLOBAL {

    @Override
    protected String doInBackground(String... params) {
        // Query has to be passed as parameter in execute function
        String query_url = params[0];

        try {
            // Connecting to the given URL
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(query_url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

            // Data to be given to the URL
            String data = "";

            if (query_url.equals(GIVE_ANS_QUERY_URL)) {

                String id = params[2];
                String ans = params[3];
                data = URLEncoder.encode("q_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" + URLEncoder.encode("ans", "UTF-8") + "=" + URLEncoder.encode(ans, "UTF-8");
            }

            if (query_url.equals(COUNTER_QUERY_URL)) {

                String pageno = params[1];
                data = URLEncoder.encode("pageno", "UTF-8") + "=" + URLEncoder.encode(pageno, "UTF-8");
            }

            if (query_url.equals(SEND_QUERY_URL)) {

                String method = params[1];
                String question = params[2];
                data = URLEncoder.encode("method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&" + URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(question, "UTF-8");

                if (method.equals(STOP)) {

                    String stop = params[3];
                    data = data + "&" + URLEncoder.encode("stop", "UTF-8") + "=" + URLEncoder.encode(stop, "UTF-8");
                }
            }

            if (query_url.equals(GIVE_LIKE_QUERY_URL)) {

                String id = params[1];
                data = URLEncoder.encode("q_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            }

            bufferedWriter.write(data);
            bufferedWriter.close();
            OS.close();

            InputStream IS = httpURLConnection.getInputStream();
            IS.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return params[0];
    }
}
