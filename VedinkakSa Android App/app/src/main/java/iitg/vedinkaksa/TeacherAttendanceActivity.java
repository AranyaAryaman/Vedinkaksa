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

import java.util.HashMap;
import java.util.Map;

public class TeacherAttendanceActivity extends AppCompatActivity {

	private final Context ctx = this;
	private TextView attendanceStatusView;
	private Button registerAttendanceButton;
	private boolean isAttendanceStarted;
	private RequestQueue requestQ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_attendance);
		attendanceStatusView = findViewById(R.id.attendanceStatusView);
		registerAttendanceButton = findViewById(R.id.attendanceButton);
		requestQ = Volley.newRequestQueue(ctx);
		getAttendanceStatus();
	}

	private void getAttendanceStatus() {
		StringRequest strReq = new StringRequest(Request.Method.GET, Constants.GET_ATTENDANCE_STATUS,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						boolean result = response.toLowerCase().startsWith("true");
						if (result) {
							startRegistering();
						} else {
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
		registerAttendanceButton.setText(R.string.stop_attendance);
	}

	private void stopRegistering() {
		isAttendanceStarted = false;
		attendanceStatusView.setTextColor(Color.RED);
		attendanceStatusView.setText(R.string.attendance_is_disabled);
		registerAttendanceButton.setText(R.string.start_attendance);
	}

	public void change_attendance_status(View view) {
		StringRequest strReq = new StringRequest(Request.Method.POST, Constants.SET_ATTENDANCE_STATUS,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (response.toLowerCase().startsWith("true")) {
							Toast.makeText(ctx, "Started attendance", Toast.LENGTH_LONG).show();
							startRegistering();
						} else {
							Toast.makeText(ctx, "Stopped attendance", Toast.LENGTH_LONG).show();
							stopRegistering();
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
				if (isAttendanceStarted) {
					params.put("attendance_status", "false");
				} else {
					params.put("attendance_status", "true");
				}

				return params;
			}
		};
		requestQ.add(strReq);
	}
}