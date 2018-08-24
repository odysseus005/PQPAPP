package pasigqueueportal.com.pqpapp.ui.profile;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.app.Endpoints;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfilePresenter extends MvpNullObjectBasePresenter<ProfileView> {
    private static final String TAG = ProfilePresenter.class.getSimpleName();
    User user2;
//    void changePassword(String currPass, String newPass, String confirmNewPass) {
//        final User user = App.getUser();
//        if (currPass.equals(user.getPassword())) {
//
//             if (newPass.equals(confirmNewPass)) {
//                getView().showProgress();
//                App.getInstance().getApiInterface().changePassword(Endpoints.UPDATEPASS,user.getUserId() + "", newPass).enqueue(new Callback<ResultResponse>() {
//                    @Override
//                    public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
//                        getView().stopProgress();
//                        if (response.isSuccessful()) {
//                            if (response.body().getResult().equals(Constants.SUCCESS)) {
//
//
//                                final Realm realm = Realm.getDefaultInstance();
//                                realm.executeTransactionAsync(new Realm.Transaction() {
//                                    @Override
//                                    public void execute(Realm realm) {
//                                        user2 = response.body().getUser();
//                                        realm.copyToRealmOrUpdate(user2);
//
//
//                                    }
//                                }, new Realm.Transaction.OnSuccess() {
//                                    @Override
//                                    public void onSuccess() {
//                                        realm.close();
//                                        getView().onPasswordChanged();
//                                    }
//                                }, new Realm.Transaction.OnError() {
//                                    @Override
//                                    public void onError(Throwable error) {
//                                        realm.close();
//                                        Log.e(TAG, "onError: Unable to save USER", error);
//                                        getView().showAlert("Error Saving API Response");
//                                    }
//                                });
//
//                            } else {
//                                getView().showAlert("Can't Conntect to the Server");
//                            }
//                        } else {
//                            getView().showAlert(response.message() != null ? response.message()
//                                    : "Unknown Error");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResultResponse> call, Throwable t) {
//                        getView().stopProgress();
//                        Log.e(TAG, "onFailure: Error calling login api", t);
//                        getView().stopProgress();
//
//                            getView().showAlert("Error Connecting to Server");
//                    }
//                });
//            } else {
//                getView().showAlert("New Password Mismatch");
//            }
//        } else {
//            getView().showAlert("Wrong Current Password!");
//        }
//    }



    public void changePassword(String token, String pass, String newpass,String confirmpass) {
        if (pass.equals("") || newpass.equals("") || confirmpass.equals("") ) {
            getView().showAlert("Fill-up all fields");
        } else if(!(newpass.equals(confirmpass)))
        {
            getView().showAlert("New Password Mismatch");

        } else{
            getView().showProgress();
            App.getInstance().getApiInterface().changePassword(Constants.APPJSON,Constants.BEARER+token, pass,newpass)
                    .enqueue(new Callback<ResultResponse>() {
                        @Override
                        public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                            getView().stopProgress();
                            if (response.body().isSuccess()) {
                                final Realm realm = Realm.getDefaultInstance();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
//                                        realm.copyToRealmOrUpdate(response.body().getUser());
                                        getView().onPasswordChanged();
                                    }
                                });
                            } else {
                                getView().showAlert(response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: Error calling login api", t);
                            getView().stopProgress();
                            getView().showAlert("Error Connecting to Server");
                        }
                    });
        }
    }

}
