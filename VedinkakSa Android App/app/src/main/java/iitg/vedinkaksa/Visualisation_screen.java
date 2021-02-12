package iitg.vedinkaksa;
/*
 * Coded by - Abhishek Kumar
 * This code is for the screen that has Symbol Based and Image Based as options
 * Teacher can independently switch between these two options and also with other Teacher Activities without any loss of data.
 * Data will be lost when Teacher exits or Logout
 */

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Visualisation_screen extends AppCompatActivity {

    private Button dynvis;
    public static String selector;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, TeacherActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation_screen);
        Constants.rot = 0;
        Intent k = getIntent();
        Bundle b = k.getExtras();
        selector = b.getString("selector");
        dynvis = (Button) findViewById(R.id.dynamic_vis);
       /* dynvis.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText(Visualisation_screen.this, "Please fill up all the credentials", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(Visualisation_screen.this, DynamicVisualization.class);
                startActivity(i);
            }
        });*/
    }

    // symbol based visualization
    /*public void sym_based(View view) {
        Constants.sym = 1;
        if (selector.equals("YES"))
            startActivity(new Intent(this, TeacherV.class));
        else
            startActivity(new Intent(this, GridviewVisualisation.class));
    }

    // image based visualization
    public void image_based(View view) {
        Constants.sym = 0;
        if (selector.equals("YES"))
            startActivity(new Intent(this, TeacherV.class));
        else
            startActivity(new Intent(this, GridviewVisualisation.class));
    }*/

    // dynamic visualization
    public void dynamic_based(View view) {


        startActivity(new Intent(Visualisation_screen.this, DynamicVisualization.class));
    }


}
