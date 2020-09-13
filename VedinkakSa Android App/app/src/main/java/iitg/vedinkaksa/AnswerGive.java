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
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity is presenting giving answer for the teacher
 */
public class AnswerGive extends AppCompatActivity implements GLOBAL {

    TextView qid;
    TextView query;
    EditText answer;
    String give_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_give);

        ACTION.Action = "GIVE ANSWER SCREEN";

        qid = findViewById(R.id.questionid2);
        query = findViewById(R.id.questionV2);
        answer = findViewById(R.id.answerV2);

        answer.setInputType(InputType.TYPE_CLASS_TEXT);
        qid.setText(getIntent().getExtras().getString("q_id"));
        query.setText(getIntent().getExtras().getString("query"));

        findViewById(R.id.submitanswer).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new BackgroundTaskOutput().execute(GIVE_ANS_QUERY_URL, getIntent().getExtras().getString("q_id"), answer.getText().toString());

            }
        });
    }
}