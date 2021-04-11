package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.app.PendingIntent;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static iitg.vedinkaksa.Constants.big_message;

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

        BigNoti();
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


    void BigNoti() {
       // wait(5000);
        // Fetch the old notification here and add it go big_message variable as
        // big_message+="\n"+"***"+ fetched message".
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 400);
        big_message = "This is the big notification page and can be"+"\n"
        +"easily called by altering a global variable value of big_message "+"\n"
        +"Here it is used for initial Teacher notification such as the results in the last class "+"\n"+
        "what we have studied in the last class"+"\n"+
        "Is there any quiz scheduled or not";


        Log.i("main", "Big notification");
        Intent viewIntent = new Intent(this, Bignoti.class);
        viewIntent.putExtra("NotiID", "Notification ID is " + 1);

        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);



        NotificationCompat.Action.WearableExtender inlineActionForWear2 =
                new NotificationCompat.Action.WearableExtender()
                        .setHintDisplayActionInline(true)
                        .setHintLaunchesActivity(true);
        NotificationCompat.Action bignotiaction =
                new NotificationCompat.Action.Builder(
                        R.drawable.ic_action_time2,
                        "Tap to watch on mobile",
                        viewPendingIntent)
                        .extend(inlineActionForWear2)
                        .build();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "id")
                        .setSmallIcon(R.drawable.ic_launcher2)
                        .setContentTitle("VedinKaksha")
                        .setContentText("This notification contain big text please click to view on mobile........")
                        .setChannelId("id")
                        .addAction(bignotiaction);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(1, notificationBuilder.build());
        //notificationID++;

    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
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