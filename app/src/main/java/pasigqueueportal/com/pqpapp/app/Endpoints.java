package pasigqueueportal.com.pqpapp.app;



public class Endpoints {

    public static final String _ID = "{id}/";
    public static final String BASE_URL = "http://queueportal.hostingerapp.com";
    public static final String URL_IMAGE = BASE_URL+ "/images/";
    public static final String API_URL = BASE_URL+ "/api";
    public static final String IMAGE_UPLOAD = BASE_URL + "/src/v1/";


    //User
    public static final String CLIENT = "new_client.php?";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String UPDATEUSER = "editProfile";
    public static final String UPDATEPASS = "editPassword";
    public static final String FORGOTPASS = "forgotPass";
    public static final String FIRSTLOGIN = "firstLogin";



    //Garage
    public static final String GARAGE = "new_car.php?";
    public static final String GET_GARAGE = "allGarage";
    public static final String ADD_GARAGE = "registerGarageCar";
    public static final String UPDATE_GARAGE = "editGarage";
    public static final String DELETE_GARAGE = "deleteGarage";


    //Data
    public static final String DATA = "new_alldata.php?";
    public static final String GET_DEALER = "allDealer";
    public static final String GET_SERVICE = "allService";
    public static final String GET_PMS = "allPMS";
    public static final String GET_ADVISOR = "allAdvisor";


    //Schedule
    public static final String SCHEDULE = "new_schedule.php?";
    public static final String GET_APPOINTMENT = "allReservation";
    public static final String GET_APPOINTMENTPAST = "allPastReservation";
    public static final String GET_TIMESLOT = "daySchedule";
    public static final String RESERVE_TIMESLOT = "registerReservation";
    public static final String CANCEL_APPOINTMENT = "cancelReservation";
    public static final String RESCHED_APPOINTMENT = "reschedReservation";

}
