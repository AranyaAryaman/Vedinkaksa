package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static iitg.vedinkaksa.Constants.arousal;
import static iitg.vedinkaksa.Constants.coll;
import static iitg.vedinkaksa.Constants.rowss;
import static iitg.vedinkaksa.Constants.valence;

// Grid formation and coloring algorithm for the dynamic visualisation
// This code was being used for static visualisation by using data from a test file.
// We are using this code directly, in order to implement the real time dynamconsecutiveic visualisation
public class DynamicVisualization extends Activity {

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public static int divisionFinder(int num, int threshold) {

        if (primeCheck(num)) {
            return largestFactor(num + 1, threshold);
        } else {
            return largestFactor(num, threshold);
        }

    }

    public static int largestFactor(int num, int threshold) {
        if (num % threshold == 0) {
            return threshold;
        } else {
            int divisor = 2;
            while (divisor < num) {
                if (num % divisor == 0) {
                    return num / divisor;
                }
                
                divisor++;
            }
            return -1;
        }
    }

    public static boolean primeCheck(int num) {
        double sqrtNum = Math.sqrt(num);
        for (int i = 2; i <= sqrtNum; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    ArrayList<Integer> counti = new ArrayList<Integer>(Collections.nCopies(100, 0));

    Date currentTime1 = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh.mm.ss aa");
    String time0 = dateFormat1.format(currentTime1);

    long jt = 1;

    int it = 0;
    String filename = "file.txt";


    ArrayList<ArrayList<String>> myArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> nameArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> rollArray = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> pictureArray = new ArrayList<ArrayList<String>>();

    private JSONArray StudentList;
    private RequestQueue requestQueue = null;
    private Runnable visualisationRunnable = null;
    Handler handler = new Handler();

    public LinearLayout layoutVertical;

    private void writeToFile(File file, String text, Context context) {
        try {

            BufferedWriter buf = new BufferedWriter(new FileWriter(file));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_visualization);


        updateStatesFromDB();
    }

    public void processing() {

        float Thresh_init = -10;

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        globalClass.setKar(1);
        globalClass.setInterval(0);
        globalClass.setThreshold(Thresh_init);

        int m = myArray.size();
        int n = myArray.get(0).size();
        int m1;
        int n1;
        boolean row_prime = false;
        boolean col_prime = false;
        if (primeCheck(m)) {
            m1 = m + 1;
            row_prime = true;
        } else {
            m1 = m;
        }
        if (primeCheck(n)) {
            n1 = n + 1;
            col_prime = true;
        } else {
            n1 = n;
        }
        Log.e("m_direction", "" + m);
        Log.e("n_direction", "" + n);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float height = size.y;
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;


        width = width / xdpi;
        height = height / ydpi;
        double width1 = (double) width / 0.362205;
        double height1 = (double) height / 0.362205;
        int t_c = (int) width1;
        int t_r = (int) height1;
        final int r = largestFactor(m1, t_r);
        final int c = largestFactor(n1, t_c);

        int num = m * n;
        num = num / (r * c);
        final int ir = m1 / r;
        final int ic = n1 / c;
        LinearLayout layoutVertical = (LinearLayout) findViewById(R.id.linear);

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(Color.parseColor("#FF0000")); // Changes this drawbale to use a single color instead of a gradient
        gd1.setCornerRadius(5);
        gd1.setStroke(1, 0xFF000000);

        GradientDrawable gd2 = new GradientDrawable();
        gd2.setColor(Color.parseColor("#FFFF00")); // Changes this drawbale to use a single color instead of a gradient
        gd2.setCornerRadius(5);
        gd2.setStroke(1, 0xFF000000);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);

        final ArrayList<ArrayList<Button>> buttonArray = new ArrayList<ArrayList<Button>>();
//        final Button[][] buttonArray = new Button[r][c];
        final TableLayout table = new TableLayout(getApplicationContext());
        table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        table.setStretchAllColumns(true);
        for (int row = 0; row < r; row++) {
            ArrayList<Button> cur = new ArrayList<Button>();
            TableRow currentRow = new TableRow(getApplicationContext());
            currentRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            currentRow.setGravity(Gravity.CENTER);
            for (int button = 0; button < c; button++) {

                Button currentButton = new Button(getApplicationContext());
                currentButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.5f));

                currentButton.setBackground(gd);
                int k = row * c + button;
                currentButton.setId(k);
                currentButton.setText(Integer.toString(currentButton.getId()));
                cur.add(button, currentButton);
                currentRow.addView(currentButton);
            }
            buttonArray.add(row, cur);
            table.addView(currentRow);
        }
        layoutVertical.addView(table);

        final HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        final HashMap<Integer, ArrayList<String>> map_name = new HashMap<Integer, ArrayList<String>>();
        final HashMap<Integer, ArrayList<String>> map_roll = new HashMap<Integer, ArrayList<String>>();
        final HashMap<Integer, Float> mapval = new HashMap<Integer, Float>();
        float threshold = Threshold(map, map_name, map_roll, mapval, m, n, m1, n1, r, c, myArray, nameArray, rollArray);
        globalClass.setThreshold(threshold);


        Colourer(mapval, threshold, c, buttonArray, gd1, gd2);
        Interval(globalClass, m, n, myArray, nameArray, rollArray, map, map_name, map_roll, mapval, m1, n1, r, c, buttonArray, gd, gd1, gd2);

        final File file1 = new File(getExternalFilesDir(null), "stats1.txt");


        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Task Started", Toast.LENGTH_LONG);
                toast.show();
                String text = "Interval:- " + globalClass.getInterval() + " , Task:- " + globalClass.getKar();
                String text1 = readFile(filename);
                if (it != 0)
                    text = text1 + "\n\n" + text;
                saveFile(filename, text);
                it++;
                writeToFile(file1, text, getApplicationContext());
                int a = globalClass.getKar();
                a = a + 1;
                globalClass.setKar(a);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Task Ended", Toast.LENGTH_LONG);
                toast.show();
                int a0 = globalClass.getKar() - 1;
                String text = "  Task " + a0 + " ended.";
                String text1 = readFile(filename);
                if (it != 0)
                    text = text1 + "\n\n" + text;
                saveFile(filename, text);
                writeToFile(file1, text, getApplicationContext());
            }
        });

        final boolean rp = row_prime;
        final boolean cp = col_prime;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {

                buttonArray.get(i).get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(),"clicked: "+v.getId()+" -> states "+map.get(v.getId()),Toast.LENGTH_SHORT).show();

                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss aa");
                        int k = counti.get(v.getId());
                        counti.set(v.getId(), k + 1);
                        String text = dateFormat.format(currentTime) + "  button  " + v.getId() + " times " + counti.get(v.getId());
                        String text1 = readFile(filename);
                        if (it != 0)
                            text = text1 + "\n" + text;
                        saveFile(filename, text);
                        writeToFile(file1, text, getApplicationContext());
                        it++;
                        Intent i = new Intent(DynamicVisualization.this, DynamicVisualSecond.class);
                        i.putExtra("hashMap", map.get(v.getId()));
                        i.putExtra("nameMap", map_name.get(v.getId()));
                        i.putExtra("rollMap", map_roll.get(v.getId()));
                        i.putExtra("valmap", mapval);
                        if (rp && (v.getId() / c == r - 1))
                            i.putExtra("irow", (ir - 1));
                        else
                            i.putExtra("irow", ir);
                        if (cp && (v.getId() % c == c - 1))
                            i.putExtra("icol", (ic - 1));
                        else
                            i.putExtra("icol", ic);
                        i.putExtra("row", r);
                        i.putExtra("col", c);
                        startActivity(i);
                    }
                });
            }
        }

    }


    public void saveFile(String file, String text) {
        try {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String file) {
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void Interval(final GlobalClass globalClass, final int m, final int n, final ArrayList<ArrayList<String>> myArray, final ArrayList<ArrayList<String>> nameArray, final ArrayList<ArrayList<String>> rollArray, final HashMap<Integer, ArrayList<String>> map, final HashMap<Integer, ArrayList<String>> map_name, final HashMap<Integer, ArrayList<String>> map_roll, final HashMap<Integer, Float> mapval, final int m1, final int n1, final int r, final int c, final ArrayList<ArrayList<Button>> buttonArray, final GradientDrawable gd, final GradientDrawable gd1, final GradientDrawable gd2) {
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                int li = globalClass.getInterval();
                globalClass.setInterval(li + 1);
                globalClass.setKar(1);
                if (globalClass.getInterval() > 1) {
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j < n; j++) {
                            if (myArray.get(i).get(j).compareTo("n") == 0) {
                                myArray.get(i).set(j, "lc");
                            } else if (myArray.get(i).get(j).compareTo("lc") == 0) {
                                myArray.get(i).set(j, "c");
                            } else {
                                myArray.get(i).set(j, "n");
                            }
                        }
                    }
                    Log.i("vals", "m: " + m + " n: " + n + " m1: " + m1 + " n1: " + n + " row: " + r + " c: " + c);
                    float threshold = Threshold(map, map_name, map_roll, mapval, m, n, m1, n1, r, c, myArray, nameArray, rollArray);
                    for (int i = 0; i < r; i++) {
                        for (int j = 0; j < c; j++) {
                            buttonArray.get(i).get(j).setBackground(gd);
                        }
                    }
                    globalClass.setThreshold(threshold);
                    Colourer(mapval, threshold, c, buttonArray, gd1, gd2);
                }
            }
        };
        timer.schedule(hourlyTask, 0, 1000 * 60 * 10);
    }

    public float Threshold(final HashMap<Integer, ArrayList<String>> map, final HashMap<Integer, ArrayList<String>> map_name, final HashMap<Integer, ArrayList<String>> map_roll, final HashMap<Integer, Float> mapval, int m, int n, int m1, int n1, final int r, final int c, ArrayList<ArrayList<String>> myArray, ArrayList<ArrayList<String>> nameArray, ArrayList<ArrayList<String>> rollArray) {
        int count2 = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((myArray.get(i).get(j)).compareTo("n") != 0) {
                    count2++;
                }
            }
        }
        float minCS = 100000;
        float maxCS = -1;
        float avgCS = -1;

        avgCS = count2 / (r * c);


        float CS;
        float EF;

        int z = -1;


        ArrayList<Float> arraylist2 = new ArrayList<Float>();
        ArrayList<Float> arraylist3 = new ArrayList<Float>();
        ArrayList<Float> arraylist4 = new ArrayList<Float>();
        ArrayList<Float> modes = new ArrayList<Float>();

        for (int k = 0; k < m1; k = k + (m1 / r)) {
            for (int l = 0; l < n1; l = l + (n1 / c)) {
                z = z + 1;
                ArrayList<String> arraylist1 = new ArrayList<String>();
                ArrayList<String> arraylist24 = new ArrayList<String>();
                ArrayList<String> arraylist25 = new ArrayList<String>();
                float count = 0;
                for (int i = k; i < k + (m1 / r); i++) {
                    for (int j = l; j < l + (n1 / c); j++) {
                        if (i < m && j < n) {
                            arraylist1.add(myArray.get(i).get(j));
                            arraylist24.add(rollArray.get(i).get(j));
                            arraylist25.add(nameArray.get(i).get(j));
                            if ((myArray.get(i).get(j)).compareTo("n") != 0) {
                                count++;
                            }
                        }

                    }

                }
                float a = count;
                arraylist2.add(count);
                arraylist3.add(count);
                arraylist4.add(count);
                CS = count;
                if (CS > maxCS) {
                    maxCS = CS;
                } else if (CS < minCS) {
                    minCS = CS;
                }

                map.put(z, arraylist1);
                map_name.put(z, arraylist25);
                map_roll.put(z, arraylist24);
                mapval.put(z, a);

            }
        }

        float medCS;
        float modeCS;
        float threshold;
        int x = (m * n) / (r * c);

        if (avgCS <= x / 2) {
            EF = (maxCS - minCS) / x;
        } else {
            EF = (maxCS - avgCS) / x;
        }
        Collections.sort(arraylist2);
        int p = arraylist2.size();
        if (p % 2 == 1) {
            medCS = (arraylist2.get(p / 2) + arraylist2.get(p / 2 - 1)) / 2;

        } else {
            medCS = arraylist2.get(p / 2);
        }

        int high = 0;
        for (Float x1 : arraylist3) {
            int freq = Collections.frequency(arraylist4, x1);
            if (freq == high) {
                modes.add(x1);
            }
            if (freq > high) {
                modes.clear();
                modes.add(x1);
                high = freq;
            }
        }
        modeCS = modes.get(0);


        threshold = (1 + EF) * avgCS;

        if (threshold >= maxCS) {
            if (avgCS >= medCS && avgCS >= modeCS) {
                threshold = avgCS;
            } else if (medCS > avgCS && medCS > modeCS) {
                threshold = medCS;
            } else if (modeCS > avgCS && modeCS >= medCS) {
                threshold = modeCS + 1;
            } else {
                if (avgCS <= x / 2) {
                    threshold = 100000;
                } else {
                    threshold = -100000;
                }

            }

        }
        return threshold;
    }

    ;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Colourer(final HashMap<Integer, Float> mapval, float threshold, final int c, final ArrayList<ArrayList<Button>> buttonArray, GradientDrawable gd1, GradientDrawable gd2) {

        final HashMap<Integer, Float> mapi = sortByValues(mapval);
        int aki = 0;
        for (Map.Entry<Integer, Float> entry : mapi.entrySet()) {
            if (entry.getValue() < threshold) {
                break;
            } else if ((entry.getValue() >= threshold) && aki < 3) {
                int hi = entry.getKey();
                int row1 = hi / c;
                int col1 = hi % c;
                buttonArray.get(row1).get(col1).setBackground(gd1);
                aki++;
            } else if ((entry.getValue() >= threshold) && aki < 6 && aki >= 3) {
                int hi1 = entry.getKey();
                int row2 = hi1 / c;
                int col2 = hi1 % c;
                buttonArray.get(row2).get(col2).setBackground(gd2);
                aki++;
            }
        }


    }


//    ArrayList<ArrayList<String>> myArray = new ArrayList<ArrayList<String>>();
//    ArrayList<ArrayList<String>> nameArray = new ArrayList<ArrayList<String>>();
//    ArrayList<ArrayList<String>> rollArray = new ArrayList<ArrayList<String>>();

    void setEmptyString(ArrayList<ArrayList<String>> input) {
        input.clear();

        for (int i = 0; i < rowss; i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < coll; j++) {
                row.add("");
            }
            input.add(row);
        }
    }

    public void setupDataStructures() {

        setEmptyString(myArray);
        setEmptyString(nameArray);
        setEmptyString(rollArray);
        setEmptyString(pictureArray);

        int size = StudentList.length();
        for (int i = 0; i < size; i++) {
            JSONObject student = null;
            try {
                student = StudentList.getJSONObject(i);

                int row = student.getInt("seat_row");
                int col = student.getInt("seat_column");

                String name = student.getString("student_id");
                int state = student.getInt("Visualization_State");
                String roll = student.getString("student_id");
//                String pictureUrl = student.getString("image");

                nameArray.get(row - 1).set(col - 1, name);
                rollArray.get(row - 1).set(col - 1, roll);
//                pictureArray.get(row - 1).set(col - 1, pictureUrl);


                String stringState = "";

                if (state == 1) {
                    stringState = "n";
                } else if (state == 2) {
                    stringState = "n";
                } else if (state == 3) {
                    stringState = "c";
                } else {
                    stringState = "lc";
                }

                myArray.get(row - 1).set(col - 1, stringState);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void updateStatesFromDB() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        String url = Constants.GET_ALL_STATES;

        Log.i("updateStatesFromDB", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // textView.setText("Response is: "+ response.substring(0,500));

                        Log.i("visualisationPOST", response);
                        try {
                            StudentList = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        setupDataStructures();
                        processing();

                        Log.i("GET_ALL_STATES", response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("GET_ALL_STATES", "touchservice error" + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
//        Log.i("touchService", "loop fdsfajk jfadlk ajfkl ");
    }


    public void startVisualisationService() {
        if (visualisationRunnable == null) {
            visualisationRunnable = new Runnable() {
                @Override
                public void run() {
                    updateStatesFromDB();
                    handler.postDelayed(this, 7000);
                }
            };
            Log.i("stateService", "stateRunnable created");
            Log.i("stateService", "outside stateRunnable created");

            handler.postDelayed(visualisationRunnable, 7000);
        }
    }
}
