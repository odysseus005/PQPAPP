package pasigqueueportal.com.pqpapp.ui.register;

import android.util.Log;
import android.util.Patterns;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import org.json.JSONObject;

import java.io.IOException;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.app.Endpoints;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private static final String TAG = RegisterPresenter.class.getSimpleName();

    public void registerUser(String username,
                         String password,
                         String confirmPassword,
                         String firstName,
                         String lastName,
                         String birthday,
                         String contact,
                         String address
                         ) {

        if (username.equals("") || password.equals("") || confirmPassword.equals("") || firstName.equals("") || lastName.equals("") || birthday.equals("") ||
                contact.equals("") ||  address.equals("")) {
            getView().showAlert("Fill-up all fields");
        }else if (!Patterns.PHONE.matcher(contact).matches()) { // check if mobile number is valid
            getView().showAlert("Invalid mobile number.");
        }
         else if (password.length() < 8) {
            getView().showAlert("Password must be atleast 8 characters");
        } /*else if (password.matches("[A-Za-z0-9 ]*")) {
            getView().showAlert("Password must have at least 1 numeric and special character");
        } */else if (!password.contentEquals(confirmPassword)) {
            getView().showAlert("Password does not match");
        } else {
            getView().startLoading();
            App.getInstance().getApiInterface().register(Constants.APPJSON ,username, password, firstName,  lastName, birthday, contact, address)
                    .enqueue(new Callback<ResultResponse>() {
                        @Override
                        public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                            getView().stopLoading();
                            if (response.isSuccessful()) {
                                if (response.body().isSuccess()) {
                                   //  getView().showAlert(response.body().getMessage());
                                         getView().onRegistrationSuccess();


                                }else
                                {
                                    getView().showAlert(response.body().getMessage());
                                }
                            } else {

                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        getView().showAlert(jObjError.getString("message"));
                                    } catch (Exception e) {
                                        getView().showAlert("Error Connecting to Server");
                                    }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResultResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: Error calling register api", t);
                            getView().stopLoading();
                            getView().showAlert("Can't Connect to Server");
                        }
                    });
        }

    }





}
