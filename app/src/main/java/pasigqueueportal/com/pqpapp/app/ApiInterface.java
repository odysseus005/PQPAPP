package pasigqueueportal.com.pqpapp.app;



import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.model.response.AppointmentResponse;
import pasigqueueportal.com.pqpapp.model.response.BarangayResponse;
import pasigqueueportal.com.pqpapp.model.response.CurrentServingResponse;
import pasigqueueportal.com.pqpapp.model.response.FeedbackResponse;
import pasigqueueportal.com.pqpapp.model.response.LoginResponse;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import pasigqueueportal.com.pqpapp.model.response.TaxTypeResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiInterface {

    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    Call<LoginResponse> login(@Field(Constants.TAG) String tag,
                              @Field(Constants.USERNAME) String username,
                              @Field(Constants.PASSWORD) String password);


    @FormUrlEncoded
    @POST(Endpoints.REGISTER)
    Call<ResultResponse>register( @Header(Constants.ACCEPT) String json,
                                  @Field(Constants.USERNAME) String user,
                                  @Field(Constants.PASSWORD) String password,
                                  @Field(Constants.FIRST_NAME) String firstName,
                                  @Field(Constants.LAST_NAME) String lastName,
                                  @Field(Constants.BIRTHDAY) String birthday,
                                  @Field(Constants.CONTACT) String contact,
                                  @Field(Constants.ADDRESS) String address,
                                          @Field(Constants.EMAIL) String email

    );


    @FormUrlEncoded
    @POST(Endpoints.FORGOTPASS)
    Call<ResultResponse>forgot( @Header(Constants.ACCEPT) String json,
                                  @Field(Constants.EMAIL) String email

    );


    @GET(Endpoints.GET_USER)
    Call<ResultResponse>getUser( @Header(Constants.ACCEPT) String json,
                                 @Header(Constants.AUTHORIZATION) String authorization

    );

    @FormUrlEncoded
    @POST(Endpoints.TOKEN)
    Call<LoginResponse>refreshToken( @Header(Constants.ACCEPT) String json,
                                     @Field(Constants.TOKEN_REFRESH) String refresh

                                     );
    @FormUrlEncoded
    @PATCH(Endpoints.VERIFY_CODE)
    Call<ResultResponse> updateUserCode(@Header(Constants.ACCEPT) String json,
                                        @Header(Constants.AUTHORIZATION) String authorization,
                                        @Field(Constants.CODE) String refresh
    );



    @FormUrlEncoded
    @PATCH(Endpoints.UPDATEPASS)
    Call<ResultResponse> changePassword(@Header(Constants.ACCEPT) String json,
                                        @Header(Constants.AUTHORIZATION) String authorization,
                                        @Field(Constants.PASSWORD) String password,
                                        @Field(Constants.NEW_PASSWORD) String newpass
                                        );

    @FormUrlEncoded
    @PATCH(Endpoints.UPDATEUSER)
    Call<ResultResponse> updateUser(@Header(Constants.ACCEPT) String json,
                          @Header(Constants.AUTHORIZATION) String authorization,
                          @Field(Constants.FIRST_NAME) String firstName,
                          @Field(Constants.LAST_NAME) String lastName,
                          @Field(Constants.BIRTHDAY) String birthday,
                          @Field(Constants.ADDRESS) String address
                                 );

    @Multipart
    @POST(Endpoints.UPLOAD_PICTURE)
    Call<ResultResponse> uploadFile(@Header(Constants.ACCEPT) String json,
                                    @Header(Constants.AUTHORIZATION) String authorization,
                                    @Part MultipartBody.Part image,
                                    @Part(Constants.IMAGE) RequestBody name);


    //Firebase

    @GET(Endpoints.SAMPLE_FCM)
    Call<ResultResponse>getFCM(@Header(Constants.ACCEPT) String json,
                                                @Header(Constants.AUTHORIZATION) String authorization

    );

    @FormUrlEncoded
    @PATCH(Endpoints.SEND_ID)
    Call<ResultResponse>sendFCM(@Header(Constants.ACCEPT) String json,
                                          @Header(Constants.AUTHORIZATION) String authorization,
                                          @Field("token") String fcmid

    );



    //Appointment

    @GET(Endpoints.GET_APPOINT)
    Call<AppointmentResponse>getUserAppointment(@Header(Constants.ACCEPT) String json,
                                                @Header(Constants.AUTHORIZATION) String authorization

    );

    @PATCH(Endpoints.CANCEL_APPOINT)
    Call<ResultResponse>cancelAppointment(@Header(Constants.ACCEPT) String json,
                                                @Header(Constants.AUTHORIZATION) String authorization,
                                               @Path("id") String queueid

    );




    @FormUrlEncoded
    @POST(Endpoints.APPOINTMENT)
    Call<ResultResponse>assessAppointment( @Header(Constants.ACCEPT) String json,
                                        @Header(Constants.AUTHORIZATION) String authorization,
                                  @Field(Constants.TRANSACTION_TYPE) String trans_type,
                                  @Field(Constants.TAX_TYPE_ID) String tax_type_id,
                                  @Field(Constants.TRANSACTION_DATE) String trans_date,
                                  @Field(Constants.BARANGAY_ID) String baranagay_id

    );

    @FormUrlEncoded
    @POST(Endpoints.APPOINTMENT)
    Call<ResultResponse>paymentAppointment( @Header(Constants.ACCEPT) String json,
                                        @Header(Constants.AUTHORIZATION) String authorization,
                                            @Field(Constants.TRANSACTION_TYPE) String trans_type,
                                            @Field(Constants.TAX_TYPE_ID) String tax_type_id,
                                            @Field(Constants.TRANSACTION_DATE) String trans_date,
                                            @Field(Constants.BARANGAY_ID) String baranagay_id,
                                            @Field(Constants.QUEUE_ID) String que_id

    );

    @GET(Endpoints.GET_TAXTYPE)
    Call<TaxTypeResponse>getTaxType(@Header(Constants.ACCEPT) String json,
                                 @Header(Constants.AUTHORIZATION) String authorization

    );

    @GET(Endpoints.GET_BARANGAY)
    Call<BarangayResponse>getBarangay(@Header(Constants.ACCEPT) String json,
                                      @Header(Constants.AUTHORIZATION) String authorization

    );


    @GET(Endpoints.CURRENT_SERVING)
    Call<CurrentServingResponse>currentServing(@Header(Constants.ACCEPT) String json,
                                               @Header(Constants.AUTHORIZATION) String authorization,
                                               @Path("id") String windowid

    );


    //Feedback
    @FormUrlEncoded
    @POST(Endpoints.FEEDBACK)
    Call<ResultResponse>sendFeedback( @Header(Constants.ACCEPT) String json,
                                           @Header(Constants.AUTHORIZATION) String authorization,
                                           @Field(Constants.EMPLOYEE_ID) String employee_id,
                                           @Field(Constants.APPOINTMENT_ID) String queueid,
                                           @Field(Constants.RATING) String rating,
                                           @Field(Constants.MESSAGE) String message);

    @GET(Endpoints.FEEDBACK)
    Call<FeedbackResponse>getFeedback(@Header(Constants.ACCEPT) String json,
                                      @Header(Constants.AUTHORIZATION) String authorization

    );

}
