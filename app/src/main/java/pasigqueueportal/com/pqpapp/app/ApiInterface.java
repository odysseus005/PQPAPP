package pasigqueueportal.com.pqpapp.app;



import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.model.response.AppointmentResponse;
import pasigqueueportal.com.pqpapp.model.response.BarangayResponse;
import pasigqueueportal.com.pqpapp.model.response.LoginResponse;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import pasigqueueportal.com.pqpapp.model.response.TaxTypeResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {

    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    Call<LoginResponse> login(@Field(Constants.TAG) String tag,
                              @Field(Constants.USERNAME) String username,
                              @Field(Constants.PASSWORD) String password);


    @FormUrlEncoded
    @POST(Endpoints.REGISTER)
    Call<ResultResponse>register( @Header(Constants.ACCEPT) String json,
                                  @Field(Constants.USERNAME) String email,
                                  @Field(Constants.PASSWORD) String password,
                                  @Field(Constants.FIRST_NAME) String firstName,
                                  @Field(Constants.LAST_NAME) String lastName,
                                  @Field(Constants.BIRTHDAY) String birthday,
                                  @Field(Constants.CONTACT) String contact,
                                  @Field(Constants.ADDRESS) String address

    );


    @GET(Endpoints.GET_USER)
    Call<ResultResponse>getUser( @Header(Constants.ACCEPT) String json,
                                 @Header(Constants.AUTHORIZATION) String authorization

    );

    @PATCH(Endpoints.VERIFY_CODE)
    Call<ResultResponse> updateUserCode(@Header(Constants.ACCEPT) String json,
                                        @Header(Constants.AUTHORIZATION) String authorization);



    @FormUrlEncoded
    @POST(Endpoints.UPDATEPASS)
    Call<ResultResponse> changePassword(@Field(Constants.TAG) String tag,
                                        @Field(Constants.USER_ID) String user_id,
                                        @Field(Constants.PASSWORD) String password);

    @FormUrlEncoded
    @POST(Endpoints.UPDATEUSER)
    Call<User> updateUser(@Header(Constants.ACCEPT) String json,
                          @Header(Constants.AUTHORIZATION) String authorization,
                          @Field(Constants.FIRST_NAME) String firstName,
                          @Field(Constants.LAST_NAME) String lastName,
                          @Field(Constants.BIRTHDAY) String birthday,
                          @Field(Constants.ADDRESS) String address
                                 );

    @Multipart
    @POST("upload.php?")
    Call<ResultResponse> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);



    //Appointment

    @GET(Endpoints.GET_APPOINT)
    Call<AppointmentResponse>getUserAppointment(@Header(Constants.ACCEPT) String json,
                                                @Header(Constants.AUTHORIZATION) String authorization

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

}
