package iitg.vedinkaksa;
// Created by Bishwaroop Bhattacharjee

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

//This activity is for uploading user picture and new user data to the server
public class UploadPics extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    int btn = 1;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText editText, namet, passet, passet2;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pics);

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views

        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.rolll);
        namet = (EditText) findViewById(R.id.nname);
        passet = (EditText) findViewById(R.id.passet);
        passet2 = (EditText) findViewById(R.id.passet2);

        //Setting clicklistener
        imageView.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }


    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart() {
        //getting name for the image
        String id = editText.getText().toString();
        String n = namet.getText().toString();
        String p = passet.getText().toString();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            UploadNotificationConfig x = new UploadNotificationConfig();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file

                    .addParameter("name", n)
                    .addParameter("roll", id)
                    .addParameter("pass", p)//Adding text parameter to the request
                    .setNotificationConfig(x)
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
            Toast.makeText(this, "Please wait till image is uploaded", Toast.LENGTH_LONG).show();
            Intent k = new Intent(UploadPics.this, LoginActivity.class);
            startActivity(k);


        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {

        if (passet.getText().toString().equals(passet2.getText().toString())) {
            if (v == imageView) {
                btn = 2;
                showFileChooser();
            }
            if (v == buttonUpload && btn == 2 && !editText.getText().toString().equals("") && !namet.getText().toString().equals("")
                    && !passet.getText().toString().equals("") && passet.getText().toString().equals(passet2.getText().toString())) {
                //Toast.makeText(this, "Please enter your roll no. ", Toast.LENGTH_SHORT).show();
                uploadMultipart();


                Intent k = new Intent(UploadPics.this, LoginActivity.class);
                startActivity(k);
                finish();
            } else {
                Toast.makeText(this, "Please enter all credentials. \n Please check if image is selected. ", Toast.LENGTH_SHORT).show();
                // uploadMultipart();
            }


        } else {
            passet.setText("");
            passet2.setText("");
            passet2.setHint("Password did not match");
        }
    }


}