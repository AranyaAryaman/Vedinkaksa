package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static iitg.vedinkaksa.Constants.debug;

/**
 * This activity is presenting teachers with options of generating and opening PDFs and handling the slides created by PDFs
 */

public class TeacherClassroom extends AppCompatActivity implements GLOBAL {

    String fileNameStoredfromDialog;

    Context context;

    private static int currentView;
    private static int currentPage = 0;

    int stopper, old;

    PdfListLoadTask listTask;

    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mCurrentPage;

    File[] filelist;

    LinearLayout scrollView;

    LinearLayout optionsLayout;

    LinearLayout readLayout;
    ImageView pdfView;


    LinearLayout writeLayout;
    ListView pdfList;
    ArrayAdapter<String> adapter;

    LinearLayout pdfSelectionLayout;
    EditText pdfContent;

    private static final int FILE_SELECT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_classroom);

        findViewById(R.id.read_pdf).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateView(PDF_SELECTION_LAYOUT);
            }
        });
        findViewById(R.id.create_new_pdf).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateView(WRITE_LAYOUT);
            }
        });

        findViewById(R.id.upload_new_pdf).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               showFileChooser();
               // startActivity(new Intent(this, UploadToServer.class));
            }
        });


        findViewById(R.id.pdfView).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentPage--;
                showPage(currentPage);
                updatepdfView();
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentPage++;
                showPage(currentPage);
                updatepdfView();
            }
        });
        optionsLayout = findViewById(R.id.options_layout);
        readLayout = findViewById(R.id.read_layout);
        pdfView = findViewById(R.id.pdfView);
        scrollView = findViewById(R.id.svl);
        pdfSelectionLayout = findViewById(R.id.pdf_selection_layout);
        writeLayout = findViewById(R.id.write_layout);
        pdfList = findViewById(R.id.pdfList);
        pdfList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TeacherClassroom", "clicked");
                Intent intent = new Intent(TeacherClassroom.this, PdfViewer.class);
                intent.putExtra("inputType", "uriTeacher");
                intent.putExtra("uri", Uri.parse(adapter.getItem(position)).toString());
                intent.putExtra("name", getFileName(Uri.parse(adapter.getItem(position))));
                startActivity(intent);
            }

        });
        pdfSelectionLayout = findViewById(R.id.pdf_selection_layout);
        pdfContent = findViewById(R.id.pdf_content);
        findViewById(R.id.generate_pdf).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                if (pdfContent.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter text to generate PDF", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(TeacherClassroom.this);
                    builder.setMessage("Name")
                            .setTitle("Enter a name for the file");

                    final EditText input = new EditText(TeacherClassroom.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fileNameStoredfromDialog = input.getText().toString();
                            if (fileNameStoredfromDialog.length() <= 4 || !fileNameStoredfromDialog.substring(fileNameStoredfromDialog.length() - 4).equals(".pdf")) {
                                fileNameStoredfromDialog += ".pdf";
                            }
                            new PdfGenerationTask().execute(fileNameStoredfromDialog);
                            view.setEnabled(false);
                            updateView(PDF_SELECTION_LAYOUT);

                            Toast.makeText(getApplicationContext(), "Starting Upload", Toast.LENGTH_LONG);
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
        stopper = 0;
        old = 0;
        context = this;
        currentView = OPTIONS_LAYOUT;
        try {
            new BackgroundTaskInput(context, scrollView).execute(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
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
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updatepdfView() {
        pdfView.setDrawingCacheEnabled(true);
        View screenView = pdfView.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(pdfView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        String page = String.valueOf(currentPage);
        new BackgroundTaskOutput().execute(COUNTER_QUERY_URL, page);
        File file = new File(SCREENSHOT_FILE + File.separator + PHOTO + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            uploadImage();
        } catch (IOException e) {
            e.printStackTrace();
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
    public void onBackPressed() {
        stopper = 1;
        if (currentView == READ_LAYOUT) {
            if (listTask != null)
                listTask.cancel(true);
            listTask = new PdfListLoadTask();
            listTask.execute();
            updateView(PDF_SELECTION_LAYOUT);
        } else if (currentView == WRITE_LAYOUT) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(pdfContent.getWindowToken(), 0, null);
            }
            updateView(OPTIONS_LAYOUT);
        } else if (currentView == PDF_SELECTION_LAYOUT) {
            updateView(OPTIONS_LAYOUT);
        } else {
            super.onBackPressed();
        }
        finish();
    }

    private void updateActionBarText() {

        if (currentView == READ_LAYOUT) {

            findViewById(R.id.previous).setEnabled(0 != mCurrentPage.getIndex());
            findViewById(R.id.next).setEnabled(mCurrentPage.getIndex() + 1 < mPdfRenderer.getPageCount());

        }
    }

    private void updateView(int updateView) {

        switch (updateView) {

            case OPTIONS_LAYOUT:

                currentView = OPTIONS_LAYOUT;

                optionsLayout.setVisibility(View.VISIBLE);

                readLayout.setVisibility(View.INVISIBLE);
                writeLayout.setVisibility(View.INVISIBLE);
                pdfSelectionLayout.setVisibility(View.INVISIBLE);

                break;

            case READ_LAYOUT:

                currentView = READ_LAYOUT;

                showPage(currentPage);

                readLayout.setVisibility(View.VISIBLE);

                optionsLayout.setVisibility(View.INVISIBLE);
                writeLayout.setVisibility(View.INVISIBLE);
                pdfSelectionLayout.setVisibility(View.INVISIBLE);

                break;

            case PDF_SELECTION_LAYOUT:

                currentView = PDF_SELECTION_LAYOUT;

                closeRenderer();

                if (listTask != null)
                    listTask.cancel(true);

                listTask = new PdfListLoadTask();
                listTask.execute();

                pdfSelectionLayout.setVisibility(View.VISIBLE);

                optionsLayout.setVisibility(View.INVISIBLE);
                readLayout.setVisibility(View.INVISIBLE);
                writeLayout.setVisibility(View.INVISIBLE);

                break;

            case WRITE_LAYOUT:

                currentView = WRITE_LAYOUT;

                writeLayout.setVisibility(View.VISIBLE);

                optionsLayout.setVisibility(View.INVISIBLE);
                readLayout.setVisibility(View.INVISIBLE);
                pdfSelectionLayout.setVisibility(View.INVISIBLE);

                break;
        }
    }

    private void openRenderer(String filePath) {

        File file = new File(filePath);

        try {

            mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            mPdfRenderer = new PdfRenderer(mFileDescriptor);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeRenderer() {

        try {

            if (mCurrentPage != null)
                mCurrentPage.close();

            if (mPdfRenderer != null)
                mPdfRenderer.close();

            if (mFileDescriptor != null)
                mFileDescriptor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPage(int index) {

        if (mPdfRenderer == null || mPdfRenderer.getPageCount() <= index || index < 0) {

            return;
        }

        try {

            if (mCurrentPage != null) {

                mCurrentPage.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        mCurrentPage = mPdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        pdfView.setImageBitmap(bitmap);
        updateActionBarText();
    }

    @SuppressLint("StaticFieldLeak")
    private class PdfListLoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            filelist = new File(PDF_FILE).listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return ((name.endsWith(".pdf")));

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (filelist != null && filelist.length >= 1) {

                ArrayList<String> fileNameList = new ArrayList<>();

                for (File file : filelist)
//                    fileNameList.add(file.getPath());
                    fileNameList.add(String.valueOf(Uri.fromFile(file)));
//                    fileNameList.add(Uri.fromFile(file));

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, fileNameList);
                Log.i("TeacherClassroom", "pdfList updated");
                pdfList.setAdapter(adapter);

            } else {

                Toast.makeText(getApplicationContext(), "No pdf file found, Please create new Pdf file", Toast.LENGTH_LONG).show();

                updateView(OPTIONS_LAYOUT);
                updateActionBarText();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImage() throws IOException {

        @SuppressLint("StaticFieldLeak")
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            private BackgroundTask rh = new BackgroundTask();

            @Override
            protected String doInBackground(Bitmap... params) {

                String uploadImage = getStringImage(params[0]);

                HashMap<String, String> data = new HashMap<>();
                data.put(IMAGE, uploadImage);

                return rh.sendPostRequest(UPLOAD_SLIDES_URL, data);
            }
        }

        new UploadImage().execute(MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(SCREENSHOT_FILE))));
    }

    @SuppressLint("StaticFieldLeak")
    private class PdfGenerationTask extends AsyncTask<String, Void, String> {

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            PdfDocument document = new PdfDocument();
            View content = findViewById(R.id.pdf_content);

            int pageNumber = 1;

            @SuppressLint("WrongThread") PdfDocument.Page page = document.startPage(new PdfDocument.PageInfo.Builder(content.getWidth(), content.getHeight() - 20, pageNumber).create());

            content.draw(page.getCanvas());
            document.finishPage(page);

            @SuppressLint("SimpleDateFormat")
//            File outputFile = new File(PDF_FILE + File.separator + "PDF_" + new SimpleDateFormat("ddMMyyyyhhmmss").format(Calendar.getInstance().getTime()) + ".pdf");
                    File outputFile = new File(PDF_FILE + File.separator + params[0]);

            try {

                outputFile.createNewFile();
                OutputStream out = new FileOutputStream(outputFile);
                document.writeTo(out);
                document.close();
                out.close();

                PdfUploadFunction(outputFile, params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return outputFile.getPath();
        }

        @Override
        protected void onPostExecute(String filePath) {

            if (filePath != null) {

                findViewById(R.id.generate_pdf).setEnabled(true);
                pdfContent.setText("");
                Toast.makeText(getApplicationContext(), "Pdf saved at " + filePath, Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "Error in Pdf creation", Toast.LENGTH_SHORT).show();

            }
        }
    }


    public void PdfUploadFunction(File samplefile, String name) {
        String PdfNameHolder, PdfPathHolder, PdfID;

        Log.d("MYUPLOAD1", "PdfUploadFunction: started");

        String PDF_UPLOAD_HTTP_URL = "http://" + debug + "/CoreFunctionality/uploadSlide.php";

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
                        .addParameter("name", PdfNameHolder)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .setAutoDeleteFilesAfterSuccessfulUpload(false)
                        .startUpload();

                Log.d("upload", "PdfUploadFunction: complete");

            } catch (Exception exception) {
                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}