package iitg.vedinkaksa;
/*
 * Coded by - Abhishek Kumar
 * For establishing connection
 * Connection props
 * Mainly for Gridview Visualisation
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Visualisation_Connector {

    public static HttpURLConnection connect(String urlAddress) {

        try {
            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("GET");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);

            return con;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
