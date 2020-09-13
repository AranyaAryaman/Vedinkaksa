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
import android.view.View;
import android.widget.Button;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import com.nbsp.materialfilepicker.MaterialFilePicker;

public class TeacherClass extends AppCompatActivity {
    Button ansq, buttonpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class);
        ansq = findViewById(R.id.btnanswer);
        buttonpdf = (Button) findViewById(R.id.btnpdf);
        ansq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeacherClass.this, AnswerQuery.class);
                startActivity(i);
            }
        });

        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable_button();
            }
        });
    }

    private void enable_button() {

        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(TeacherClass.this)
                        .withRequestCode(10)
                        .start();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            enable_button();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }

}
