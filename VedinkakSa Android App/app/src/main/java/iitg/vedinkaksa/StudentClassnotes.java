package iitg.vedinkaksa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// Adds the notee taking functionality
public class StudentClassnotes extends AppCompatActivity {

    EditText notesContent;
    String ROOT = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String DIR = ROOT + File.separator + "vedinkakSa"; // create a folder "vedinkakSa" in phone root directory
    String path = DIR + File.separator + "Notes";      // create sub folder "ClassNotes" inside vedinkakSa directory
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classnotes);
        Intent intent = getIntent();
        String slideName = intent.getStringExtra("slideName");
        Log.i("ClassNotes", "slide name: " + slideName);
        fileName = slideName + "_slide.txt";
        notesContent = findViewById(R.id.notes_content);
        String content = readFile(this);
        if (content != null) {
            notesContent.setText(content);
        }

        findViewById(R.id.save_notes).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // if notes id empty
                String content = notesContent.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty Note saved", Toast.LENGTH_SHORT).show();
                }
                // if text are inputted
                else {
                    Toast.makeText(getApplicationContext(), "Notes saved: Check Vedinkaksa/ClassNotes folder ", Toast.LENGTH_LONG).show();
                    saveToFile(content);
                }
                finish();
            }
        });

    }

    public String readFile(Context context) {
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + File.separator + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        } catch (IOException ex) {
            Log.i("Classnotes", ex.getMessage());
        }
        return line;
    }

    public boolean saveToFile(String data) {
        try {
//            new File(path).mkdir();
            File file = new File(path + File.separator + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            return true;
        } catch (IOException ex) {
            Log.i("ClassNotes", ex.getMessage());
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveToFile(notesContent.getText().toString());
    }
}
