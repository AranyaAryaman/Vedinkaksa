package iitg.vedinkaksa;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**Created by: Siddhi Joshi
 */
public class StudentQuizResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz_result);

        // assigning the value of quiz to quiz_points variable
        int quiz_points = getIntent().getExtras().getInt("quiz_points");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        // for showing the result of the quiz
        TextView quizPointText = findViewById(R.id.quiz_points_tv);
        quizPointText.setText(quiz_points + "");
    }
}
