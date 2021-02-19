package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/** This activity is presenting teacher activity */
public class TeacherActivity extends Activity1 {
    Button visu;                                                // Line 19: Added by- Abhishek Kumar for Visualisation in TeacherActivity screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_teacher);
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

        visu = (Button) findViewById(R.id.visb);                                                           //Line 30: For VIsualisation
        ACTION.Action = "TEACHER MAIN SCREEN";

        if (getIntent().hasExtra("aKey")) {

            TextView priceTextView = findViewById(R.id.username);
            priceTextView.setText("You are logged in as " + getIntent().getStringExtra("aKey"));

        }
        visu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                               //Line 39: For VIsualisation
                Intent k = new Intent(TeacherActivity.this, DynamicVisualization.class);
                if (Constants.cc == 1)                                                                         // Line 43: For Visualisation; Constants used, created by Bishwaroop Bhattacharjee
                    k.putExtra("selector", "YES");
                else
                    k.putExtra("selector", "NO");
                startActivity(k);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.isKeyboardOn = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent k = new Intent(TeacherActivity.this, LoginActivity.class);
        startActivity(k);
        finish();

    }

    public void teacherclassroom(View view) {

        startActivity(new Intent(this, TeacherClassroom.class));

    }

    public void teachergetattendance(View view) {

        startActivity(new Intent(this, TeacherAttendanceActivity.class));
    }

    public void teacherexaminations(View view) {

        startActivity(new Intent(this, QuizTeacherMenu.class));
    }

    public void teacherpoll(View view) {

        startActivity(new Intent(this, ComingSoon.class));
    }

    public void teachergetresult(View view) {

        startActivity(new Intent(this, ComingSoon.class));
    }

    public void teachermyactivites(View view) {

        startActivity(new Intent(this, ComingSoon.class));
    }

    public void teacherstudentactivites(View view) {

        startActivity(new Intent(this, StudentNotes.class));
    }

    public void logout(View view) {

        startActivity(new Intent(this, LoginActivity.class));
    }

}