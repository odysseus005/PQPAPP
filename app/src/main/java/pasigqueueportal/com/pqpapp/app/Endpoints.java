package pasigqueueportal.com.pqpapp.app;



public class Endpoints {

    public static final String _ID = "{id}/";
    public static final String BASE_URL = "http://queueportal.hostingerapp.com";
    public static final String URL_IMAGE = BASE_URL+ "/images/";
    public static final String API_URL = BASE_URL+ "/api/";
    public static final String IMAGE_UPLOAD = BASE_URL + "/src/v1/";


    //User
    public static final String VERIFY_CODE = "verify";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String GET_USER = "user";
    public static final String UPDATEUSER = "profile";
    public static final String UPDATEPASS = "changepass";
    public static final String FORGOTPASS = "forgotPass";
    public static final String FIRSTLOGIN = "firstLogin";


    //Appointment
    public static final String GET_APPOINT = "user/queue";
    public static final String GET_TAXTYPE = "taxtypes";
    public static final String GET_BARANGAY = "barangay";
    public static final String APPOINTMENT = "queue";

}
