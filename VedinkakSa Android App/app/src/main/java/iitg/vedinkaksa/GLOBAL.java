package iitg.vedinkaksa;
/**
 * DISCLAIMER: Any Part or segment of the comments presented as documentation cannot be modified or removed without the proper permission from the current author. If any of the documentation in the public domain is found without proper credits and permission of the author, it will be dealt as plagiarism of the original code. However, part of the codes can be customized and used as per needs without any permission for personal use.
 * <p>
 * Author: Shashank Kotyan
 * Contact details: shashank15100@iiitnr.edu.in; shashankkotyan@gmail.com
 * Developed for: Affective Computing Team, IIT-Guwahati for development of vedinkakSa, a sensitive classroom application.
 */

import java.io.File;

/** This interface is for all the constants which are used.*/
public interface GLOBAL {

    String SERVER_ADDRESS = "http://" + Constants.debug + "/CoreFunctionality/oldphp/";

    String SEND_QUERY_URL = SERVER_ADDRESS + "sendquery.php";
    String GIVE_ANS_QUERY_URL = SERVER_ADDRESS + "giveans.php";
    String UPLOAD_SLIDES_URL = "http://" + Constants.debug + "/Vishal/slidesurl/upload.php";
    String GET_DATA_QUERY_URL = SERVER_ADDRESS + "get_data.php";
    String GIVE_LIKE_QUERY_URL = SERVER_ADDRESS + "givelike.php";
    String COUNTER_QUERY_URL = SERVER_ADDRESS + "counter.php";
    String NOTE_ACTIVITIES_QUERY_URL = SERVER_ADDRESS + "noteactivities.php";
    String UPLOAD_QUERY_URL = SERVER_ADDRESS + "upload.php";
    String VISUALISATION_QUERY_URL = "http://" + Constants.debug + "/CoreFunctionality/truncating.php";
    String GRIDVIEW_CONNECTION = "http://" + Constants.debug + "/CoreFunctionality/student.php";

    String PHOTO = "empty.jpeg";
    String PHOTO_URL = "http://" + Constants.debug + "/CoreFunctionality/uploads/";

    String ASKING = "asking";
    String START = "start";
    String STOP = "stop";
    String IMAGE = "image";
    String KEY = "aKey";
    String SERVER_RESPONSE = "server_response";

    int OPTIONS_LAYOUT = 1;
    int READ_LAYOUT = 2;
    int WRITE_LAYOUT = 3;
    int PDF_SELECTION_LAYOUT = 4;
    int ANSWER_QUERY_LAYOUT = 5;

    String ROOT = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String DIR = ROOT + File.separator + "vedinkakSa";
    String PDF_FILE = DIR + File.separator + "PDF";
    String CLASSNOTES_FILE = DIR + File.separator + "ClassNotes";
    String DATA_FILE = DIR + File.separator + "Data";
    String NOTES_FILE = DIR + File.separator + "Notes";
    String SLIDES_FILE = DIR + File.separator + "Slides";
    String SCREENSHOT_FILE = DIR + File.separator + "Screenshots";

    String NOTES_FILE_NAME = "Notes.txt";
    String DATA_FILE_NAME = "Data.csv";
}
