package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// Classroom Dashboard for student, for selecting the pdf slide
public class StudentClassroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classroom);
    }

    public void studentSlides(View view) {
        startActivity(new Intent(this, StudentSlides.class));
    }

    public void studentQueryActivity(View view) {
        startActivity(new Intent(this, StudentQueryActivity.class));
    }
}
