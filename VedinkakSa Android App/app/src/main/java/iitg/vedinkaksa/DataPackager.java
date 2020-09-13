package iitg.vedinkaksa;
//Created by: Bishwaroop Bhattacharjee, To support the registration page

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class DataPackager {

    String name, roll, password;

    public DataPackager(String name, String roll, String password) {
        this.name = name;
        this.roll = roll;
        this.password = password;
    }

    public String packData() {
        JSONObject jo = new JSONObject();
        StringBuffer packedData = new StringBuffer();
        try {
            jo.put("name", name);
            jo.put("roll", roll);
            jo.put("pass", password);

            Boolean firstValue = true;
            Iterator it = jo.keys();
            do {
                String key = it.next().toString();
                String Value = jo.get(key).toString();

                if (firstValue)
                    firstValue = false;
                else
                    packedData.append("&");

                packedData.append(URLEncoder.encode(key, "UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(Value, "UTF-8"));

            }
            while (it.hasNext());
            return packedData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
