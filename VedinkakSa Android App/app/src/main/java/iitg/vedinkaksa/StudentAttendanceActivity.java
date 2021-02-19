package iitg.vedinkaksa;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class StudentAttendanceActivity extends AppCompatActivity {

	private final Context ctx = this;
	private TextView attendanceStatusView;
	private Button registerAttendanceButton;
	private boolean isAttendanceStarted;
	private RequestQueue requestQ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_attendance);
		attendanceStatusView = findViewById(R.id.attendanceStatusView);
		registerAttendanceButton = findViewById(R.id.attendanceButton);
		requestQ = Volley.newRequestQueue(ctx);
		getAttendanceStatus();
	}

	private void getAttendanceStatus() {
		// TODO: url
		StringRequest strReq = new StringRequest(Request.Method.GET, "url",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						boolean result = response.toLowerCase().startsWith("yes");
						if (result) {
							isAttendanceStarted = true;
							startRegistering();
						} else {
							isAttendanceStarted = false;
							stopRegistering();
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

	private void startRegistering() {
		isAttendanceStarted = true;
		attendanceStatusView.setTextColor(Color.GREEN);
		attendanceStatusView.setText(R.string.attendance_is_enabled);
		registerAttendanceButton.setEnabled(true);
	}

	private void stopRegistering() {
		isAttendanceStarted = false;
		attendanceStatusView.setTextColor(Color.RED);
		attendanceStatusView.setText(R.string.attendance_is_disabled);
		registerAttendanceButton.setEnabled(false);
	}

	public void register_attendance(View view) {
		if (!isAttendanceStarted)
			return;
		// TODO: url
		StringRequest strReq = new StringRequest(Request.Method.GET, "url",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Toast.makeText(ctx, "Attendance recorded. The button will now be disabled for you", Toast.LENGTH_LONG).show();
						stopRegistering();
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
}