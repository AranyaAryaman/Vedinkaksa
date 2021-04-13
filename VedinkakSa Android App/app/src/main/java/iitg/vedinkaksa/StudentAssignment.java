package iitg.vedinkaksa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
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

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static iitg.vedinkaksa.Constants.debug;

public class StudentAssignment extends AppCompatActivity {

	private final Context ctx = this;
	private TextView assignmentStatusView;
	private Button submitAssignmentButton;
	private boolean isAssignmentStarted;
	private RequestQueue requestQ;
	private int FILE_SELECT_CODE = 100; // any nonce works

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_assignment);
		assignmentStatusView = findViewById(R.id.assignmentStatusView);
		submitAssignmentButton = findViewById(R.id.assignmentSubmitButton);
		requestQ = Volley.newRequestQueue(ctx);
		getAssignmentStatus();
	}

	private void getAssignmentStatus() {
		// TODO: url
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

	private void startAssignmentSubmission(String name) {
		isAssignmentStarted = true;
		assignmentStatusView.setTextColor(Color.GREEN);
		assignmentStatusView.setText(R.string.assignment_submission_is_enabled);
		submitAssignmentButton.setText("Submit - " + name);
		submitAssignmentButton.setEnabled(true);
	}

	private void stopAssignmentSubmission() {
		isAssignmentStarted = false;
		assignmentStatusView.setTextColor(Color.RED);
		assignmentStatusView.setText(R.string.assignment_submission_is_disabled);
		submitAssignmentButton.setText(R.string.submit_assignment);
		submitAssignmentButton.setEnabled(false);
	}

	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("application/pdf");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(intent, "Select a File to Upload"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(this, "Please install a File Manager.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public String getFileName(Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			} finally {
				cursor.close();
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf('/');
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FILE_SELECT_CODE) {
			if (resultCode == RESULT_OK) {
				// Get the Uri of the selected file
				Uri uri = data.getData();
				Log.d("TAG", "File Uri: " + uri.toString());
				// Get the path
				String name = getFileName(uri);
				Log.d("TAG", "File Path: " + name);
				// Initiate the upload

				try {
					File outFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "vedinkakSa" + File.separator + "PDF" + File.separator + name);
//                        InputStream in = new FileInputStream(new File(uri.getPath()));
					InputStream in = getContentResolver().openInputStream(uri);
					OutputStream out = new FileOutputStream(outFile);

					// Copy the bits from instream to outstream
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
					PdfUploadFunction(outFile, name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void submit_assignment(View view) {
		if (!isAssignmentStarted)
			return;
		showFileChooser();
	}
	public void PdfUploadFunction(File samplefile, String name) {
		String PdfNameHolder, PdfPathHolder, PdfID;

		Log.d("MYUPLOAD1", "PdfUploadFunction: started");

		String PDF_UPLOAD_HTTP_URL = Constants.SUBMIT_ASSIGNMENT;

		PdfNameHolder = name;

		PdfPathHolder = FilePath.getPath(this, Uri.fromFile(samplefile));
		Log.d("upload", "PdfUploadFunction: args captured");

		if (PdfPathHolder == null) {
			Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();
		} else {
			try {
				PdfID = UUID.randomUUID().toString();
				new MultipartUploadRequest(getApplicationContext(), PdfID, PDF_UPLOAD_HTTP_URL)
						.addFileToUpload(PdfPathHolder, "pdf")
						.addParameter("roll", Constants.student_id)
						.setNotificationConfig(new UploadNotificationConfig())
						.setMaxRetries(5)
						.setAutoDeleteFilesAfterSuccessfulUpload(false)
						.startUpload();

				Log.d("upload", "PdfUploadFunction: complete");
				Toast.makeText(ctx, "Uploading Assignment", Toast.LENGTH_SHORT).show();
			} catch (Exception exception) {
				Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}