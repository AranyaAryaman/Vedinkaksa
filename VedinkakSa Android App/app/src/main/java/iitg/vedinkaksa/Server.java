package iitg.vedinkaksa;

import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by: Siddhi Joshi
 * This class is used to connect to the server and sending our typing and touch module files to the server.
 */
public class Server {

    private String userId = Constants.student_id; //file name will be according to the username that he student has provided.

    //This method will send the typing module file student_id.csv
    protected void uploadTypeFile() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String SDCARD =
                        Environment.getExternalStorageDirectory().getAbsolutePath();
                String filename = userId + ".csv";

                File f = new File(SDCARD + File.separator + filename);

                String content_type = getMimeType(f.getPath());

                String file_path = f.getAbsolutePath();
                OkHttpClient client = new OkHttpClient();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart("uploaded_file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + Constants.debug + "/uploadToServer.php")
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        throw new IOException("Error : " + response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    //This method will send the touch module file student_id_touch.csv
    protected void uploadTouchFile() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                String SDCARD =
                        Environment.getExternalStorageDirectory().getAbsolutePath();
                String filename = userId + "_touch.csv";

                File f = new File(SDCARD + File.separator + filename);

                String content_type = getMimeType(f.getPath());

                String file_path = f.getAbsolutePath();
                OkHttpClient client = new OkHttpClient();
                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("type", content_type)
                        .addFormDataPart("uploaded_file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .build();
                Request request = new Request.Builder()
                        .url("http://" + Constants.debug + "/uploadToServer.php")
                        .post(request_body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        throw new IOException("Error : " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    //This method will get the extension of the file
    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

}
