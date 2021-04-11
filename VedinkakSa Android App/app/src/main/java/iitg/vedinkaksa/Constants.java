package iitg.vedinkaksa;

// Global constants used throughout the app
public class Constants {

    // BASE IP
    public static final String SERVER_IP = "192.168.1.16"; // Don't put '/' in the end

    public static final String debug = SERVER_IP + "/Sites/Server";
    public static final String debug1 = SERVER_IP + ":5000";

    // URL for getting the quiz, adding a question, and resetting the quiz
    public static final String QUIZ_GET = "http://" + debug + "/CoreFunctionality/quiz/index.php";
    public static final String QUIZ_POST = "http://" + debug + "/CoreFunctionality/quiz/add_ques.php";
    public static final String QUIZ_DELETE = "http://" + debug + "/CoreFunctionality/quiz/del_all.php";

    public static final String ATTENDANCE_START = "http://" + debug + "/CoreFunctionality/attendance/teachatt.php";
    public static final String GET_ATTENDANCE_STATUS = "http://" + debug + "/CoreFunctionality/attendance/get_attendance_status.php";
    public static final String SET_ATTENDANCE_STATUS = "http://" + debug + "/CoreFunctionality/attendance/set_attendance_status.php";
    public static final String REGISTER_ATTENDANCE = "http://" + debug + "/CoreFunctionality/attendance/register_attendance.php";

    // URL for logging in user
    public static final String UPLOAD_URL = "http://" + debug + "/CoreFunctionality/upload.php";
    public static final String LOGIN_URL = "http://" + debug + "/CoreFunctionality/login.php";
    public static final String QR_URL = "http://" + debug + "/CoreFunctionality/qrupload.php";

    // URL for communicating with the touch, type and involvement models
    public static final String TOUCH = "http://" + debug1 + "/touchModel";
    public static final String TYPE = "http://" + debug1 + "/typingModel";
    public static final String INVOLVEMENT = "http://" + debug1 + "/involvementModel";
    // For updating and getting the student states
    // TODO
    public static final String STATE = "http://" + debug + "/CoreFunctionality/Visualization/UpdateState2.php";
    public static final String GET_ALL_STATES = "http://" + debug + "/CoreFunctionality/Visualization/getAllVisualizationStates2.php";

    // Global variables
    public static int rowss, ht, abb, st, wd, coll;
    // rowss and coll are setup at teacher login.
    public static int sym = 0;
    public static int rot = 0;
    public static int cc = 0;
    public static String student_id;

    // user flag to check if the user is a student or teacher
    public static boolean teacher = false;

    public static int typ_spd = 0;
    public static int no_of_words = 0;
    public static int touch_count = 0; // no of key presses
    public static int max_length = 0;
    public static int key_presses = 0;
    public static int key_presses_without_bks = 0;
    public static int shk_freq = 0;

    public static boolean isKeyboardOn = false;
    public static float touch_pressure = 0;
    public static int no_of_touch = 0;

    // global variables for storing state values
    public static int valence = 0;
    public static int arousal = 0;
    public static int involvement = 1;
    public static int classroom_activity = 1;
    public static int emotional_state = 0;
    public static int mental_state = 0;
    public static int visualisation_state = 0;
    public static int engagement = 1;

    public static String big_message = null;

}
