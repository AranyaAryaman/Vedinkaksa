package iitg.vedinkaksa;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TeacherAssignment extends AppCompatActivity {
	private boolean isAssignmentStarted = false;
	private final Context ctx = this;
	private EditText assignmentEditText;
	private TextView indicatorTextView;
	private Button assignmentButton;
	private RequestQueue requestQ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_assignment);
		assignmentEditText = findViewById(R.id.assignmet_name_edittext);
		indicatorTextView = findViewById(R.id.assignmentIndicator);
		assignmentButton = findViewById(R.id.assignment_toggle);
		requestQ = Volley.newRequestQueue(this);
		getAssignmentDB();
	}

	String getAssignmentNameEditText() {
		Editable a = assignmentEditText.getText();
		if (a.length() == 0 || a.toString().equals(""))
			return "";
		if (a.toString().trim().length() == 0) {
			return "";
		}
		return a.toString();
	}

	void setAssignmentNameEditText(String name) {
		assignmentEditText.setText(name);
	}

	void startAssignmentSubmission(String name) {
		if (name == null || name.equals("")) {
			Toast.makeText(this, "Please provide a valid assignment name", Toast.LENGTH_SHORT).show();
			return;
		}
		isAssignmentStarted = true;
		indicatorTextView.setTextColor(Color.rgb(0x00, 0xff, 0x00));
		indicatorTextView.setText(R.string.assignment_submission_is_enabled);
		assignmentButton.setText(R.string.stop_assignment_submission);
	}

	void stopAssignmentSubmission() {
		isAssignmentStarted = false;
		indicatorTextView.setTextColor(Color.rgb(0xff, 0x00, 0x00));
		indicatorTextView.setText(R.string.assignment_submission_is_disabled);
		assignmentButton.setText(R.string.start_assignment_submission);
	}

	void getAssignmentDB() {
		StringRequest strReq = new StringRequest(Request.Method.GET, Constants.GET_ASSIGNMENT_STATUS,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						String result = response.trim();
						if (result == null || result.equals("")) {
							stopAssignmentSubmission();
						} else {
							startAssignmentSubmission(result);
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(ctx, "Network Error. Retry again later", Toast.LENGTH_SHORT).show();
					}
				});
		requestQ.add(strReq);
	}

	public void toggleAssignemtStatus(View view) {
		StringRequest strReq = new StringRequest(Request.Method.POST, Constants.SET_ASSIGNMENT_STATUS,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (response.toLowerCase().startsWith("true")) {
							Toast.makeText(ctx, "Started assignments", Toast.LENGTH_LONG).show();
							startAssignmentSubmission(getAssignmentNameEditText());
						} else {
							Toast.makeText(ctx, "Stopped assignments", Toast.LENGTH_LONG).show();
							stopAssignmentSubmission();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(ctx, "Network Error. Retry again later", Toast.LENGTH_SHORT).show();
					}

				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<>();
				if (isAssignmentStarted) {
					params.put("assignment_name", "");
				} else {
					params.put("assignment_name", getAssignmentNameEditText());
				}

				return params;
			}
		};
		requestQ.add(strReq);
	}
}

