package pasigqueueportal.com.pqpapp.ui.login;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.R;
import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.app.Endpoints;
import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.data.User;
import pasigqueueportal.com.pqpapp.model.response.LoginResponse;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {
    private int login_counter = 0;
    private static final String TAG = LoginPresenter.class.getSimpleName();
    Token token;
    User user;
    Token token1;

    public void login(final String email, final String password) {
        if (email.isEmpty() || email.equals("")) {
            getView().showAlert("Please enter Username");
        } else if (password.isEmpty() || password.equals("")) {
            getView().showAlert("Please enter Password");
        } else {
            getView().startLoading();
            App.getInstance().getApiInterface().login(Endpoints.LOGIN,email, password)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call,
                                               final Response<LoginResponse> response) {
                            getView().stopLoading();
                            if (response.isSuccessful()) {
                                try {
                                    if (response.body().isSuccess()) {
                                            final Realm realm = Realm.getDefaultInstance();
                                            realm.executeTransactionAsync(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm.delete(Token.class);
                                                    token = response.body().getToken();
                                                    realm.copyToRealmOrUpdate(token);


                                                }
                                            }, new Realm.Transaction.OnSuccess() {
                                                @Override
                                                public void onSuccess() {
                                                    realm.close();
                                                    getView().onLoginSuccess(token);
                                                }
                                            }, new Realm.Transaction.OnError() {
                                                @Override
                                                public void onError(Throwable error) {
                                                    realm.close();
                                                    Log.e(TAG, "onError: Unable to save USER", error);
                                                    getView().showAlert("Error Saving API Response");
                                                }
                                            });


                                    }else
                                    {
                                        getView().showAlert(response.body().getMessage());
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    getView().showAlert("Oops");
                                }
                            } else {
                                getView().showAlert("Error Connecting to Server");
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: Error calling login api", t);
                            getView().stopLoading();
                            getView().showAlert("Error Connecting to Server");
                        }
                    });
        }
    }




    public void getUser(String token) {
        getView().startLoading();
        App.getInstance().getApiInterface().getUser( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                user = response.body().getUser();
                                realm.copyToRealmOrUpdate(user);


                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();


                                getView().onLoginConfirm(user);

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                Log.e(TAG, "onError: Unable to save USER", error);
                                getView().showAlert("Error Saving API Response");
                            }
                        });

                } else {

                    getView().showAlert(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();
                Log.e(TAG, "onFailure: Error calling login api", t);
                getView().stopLoading();
                getView().showAlert("Error Connecting to Server");
            }
        });

    }


    public void refresh(String token) {
        getView().startLoading();
        App.getInstance().getApiInterface().refreshToken( Constants.APPJSON,token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {

                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(Token.class);
                            token1 = response.body().getToken();
                            realm.copyToRealmOrUpdate(token1);

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();


                            getView().onLoginSuccess2(token1);

                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                            Log.e(TAG, "onError: Unable to save USER", error);
                            getView().showAlert("Error Saving API Response");
                        }
                    });

                } else {

                    getView().showAlert(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                getView().stopLoading();
                Log.e(TAG, "onFailure: Error calling login api", t);
                getView().stopLoading();
                getView().showAlert("Error Connecting to Server");
            }
        });

    }


    public void firstLogin(String token, final User userSave,String code) {


        getView().startLoading();
        App.getInstance().getApiInterface().updateUserCode(Constants.APPJSON,Constants.BEARER+token,code).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {

                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                user = userSave;
                                user.setFirstlogin("2");
                                realm.copyToRealmOrUpdate(user);


                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();

                                getView().showAlert("Verification Successful!");
                                getView().onLoginConfirm(user);

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                Log.e(TAG, "onError: Unable to save USER", error);
                                getView().showAlert("Error Connecting to Server");
                            }
                        });
                    } else {
                        getView().showAlert(response.body().getMessage());
                    }
                } else {
                    getView().showAlert(response.message() != null ? response.message()
                            : "Unknown Error");
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();
                Log.e(TAG, "onFailure: Error calling login api", t);
                getView().stopLoading();
                getView().showAlert("Error Connecting to Server");
            }
        });

    }





}
