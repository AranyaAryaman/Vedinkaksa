package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

/** This activity is presenting viewing answer */
public class AnswerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_view);

        ACTION.Action = "VIEWING QUERY";

        TextView answer = findViewById(R.id.answerV);
        TextView qid = findViewById(R.id.questionid);
        TextView query = findViewById(R.id.questionV);

        if (Objects.equals(getIntent().getExtras().getString("answer"), ""))
            answer.setText("Not answered yet.");

        else
            answer.setText(getIntent().getExtras().getString("answer"));

        qid.setText(getIntent().getExtras().getString("q_id"));
        query.setText(getIntent().getExtras().getString("query"));
    }
}