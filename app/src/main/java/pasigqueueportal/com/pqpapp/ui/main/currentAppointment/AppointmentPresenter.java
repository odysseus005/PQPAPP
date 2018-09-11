package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.IOException;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.CurrentServing;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.response.AppointmentResponse;
import pasigqueueportal.com.pqpapp.model.response.BarangayResponse;
import pasigqueueportal.com.pqpapp.model.response.CurrentServingResponse;
import pasigqueueportal.com.pqpapp.model.response.LoginResponse;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import pasigqueueportal.com.pqpapp.model.response.TaxTypeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ConstantConditions")
public class AppointmentPresenter extends MvpBasePresenter<AppointmentView> {

    private Realm realm;
    private Token token1;
    public void onStart(String token) {
        realm = Realm.getDefaultInstance();

        refresh(token);
       // getView().onFinishTokenRef();
    }

    public void loadAppointmentList(String token) {

        getView().startLoading();
        App.getInstance().getApiInterface().getUserAppointment( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, final Response<AppointmentResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(Appointment.class);
                            realm.copyToRealmOrUpdate(response.body().getAppointments());


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();
                            getView().setAppointmentList();

                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                            getView().showError("Error Saving API Response");
                        }
                    });

                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }



    public void loadNotif(String token)
    {

        getView().startLoading();
        App.getInstance().getApiInterface().getFCM( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                            getView().showError("Error Saving API Response");
                        }
                    });

                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();

             //   getView().showError("Error Connecting to Server");
            }
        });


    }


    public void loadBarangay(String token)
    {

            getView().startLoading();
            App.getInstance().getApiInterface().getBarangay( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<BarangayResponse>() {
                @Override
                public void onResponse(Call<BarangayResponse> call, final Response<BarangayResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {


                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.delete(Barangay.class);
                                realm.copyToRealmOrUpdate(response.body().getBarangay());


                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().loadBarangay();

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                getView().showError("Error Saving API Response");
                            }
                        });

                    } else {

                        getView().showError(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BarangayResponse> call, Throwable t) {
                    getView().stopLoading();
                    getView().stopLoading();
                    getView().showError("Error Connecting to Server");
                }
            });


    }

    public void sendFCMId( String token,String id)
    {
        getView().startLoading();
        App.getInstance().getApiInterface().sendFCM( Constants.APPJSON,Constants.BEARER+token,id).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //  realm.delete(Barangay.class);
                            //    realm.copyToRealmOrUpdate(response.body().getBarangay());


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();



                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                            getView(). showError("Error Saving API Response");
                        }
                    });

                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {

                getView(). stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }



    public void loadTaxType(String token)
    {

            getView().startLoading();
            App.getInstance().getApiInterface().getTaxType( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<TaxTypeResponse>() {
                @Override
                public void onResponse(Call<TaxTypeResponse> call, final Response<TaxTypeResponse> response) {
                    getView().stopLoading();
                    if (response.isSuccessful()) {


                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.delete(TaxType.class);
                                realm.copyToRealmOrUpdate(response.body().getTaxType());


                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().loadTaxType();

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                getView().showError("Error Saving API Response");
                            }
                        });

                    } else {

                        getView().showError(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<TaxTypeResponse> call, Throwable t) {
                    getView().stopLoading();
                    getView().stopLoading();
                    getView().showError("Error Connecting to Server");
                }
            });


    }


    public void assessmentAppointment(String trans_type,String tax_type,String trans_date,String baranagay, String token)
    {
        getView().startLoading();
        App.getInstance().getApiInterface().assessAppointment( Constants.APPJSON,Constants.BEARER+token,trans_type,tax_type,trans_date,baranagay).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {

                        final Realm realm = Realm.getDefaultInstance();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {


                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                realm.close();
                                getView().showReturn("Appointment Successful",response.body().getAppointment());

                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                realm.close();
                                getView().showError("Error Saving API Response");
                            }
                        });
                    }else
                        getView().showError(response.body().getMessage());
                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }


    public void paymentAppointment(String trans_type,String tax_type,String trans_date,String baranagay, String token,String id)
    {
        getView().startLoading();
        App.getInstance().getApiInterface().paymentAppointment( Constants.APPJSON,Constants.BEARER+token,trans_type,tax_type,trans_date,baranagay,id).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //  realm.delete(Barangay.class);
                            //    realm.copyToRealmOrUpdate(response.body().getBarangay());


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();
                            getView().showReturn("Appointment Successful",response.body().getAppointment());


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                            getView().showError("Error Saving API Response");
                        }
                    });

                } else {

                    try {
                        getView().showError(response.body().getMessage());
                    }
                    catch (Exception e)
                    {
                        getView().showError("Error Connecting to Server!");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }

    public void cancelAppointment( String token,String id)
    {
        getView().startLoading();
        App.getInstance().getApiInterface().cancelAppointment( Constants.APPJSON,Constants.BEARER+token,id).enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, final Response<ResultResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            //  realm.delete(Barangay.class);
                            //    realm.copyToRealmOrUpdate(response.body().getBarangay());


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();
                            getView().showReturnCancel("Cancelling Appointment Successful");


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                            getView().showError("Error Saving API Response");
                        }
                    });

                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
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
                            getView().onFinishTokenRef();


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();
                          //  Log.e(TAG, "onError: Unable to save USER", error);
                            getView().showError("Session Expired");
                            if(response.body().getMessage().contains("refresh token is invalid"))
                                getView().logOut();
                        }
                    });

                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                getView().stopLoading();
              //  Log.e(TAG, "onFailure: Error calling login api", t);
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });

    }


    public void currentServing( String token,String id)
    {
        getView().startLoading();
        App.getInstance().getApiInterface().currentServing( Constants.APPJSON,Constants.BEARER+token,id).enqueue(new Callback<CurrentServingResponse>() {
            @Override
            public void onResponse(Call<CurrentServingResponse> call, final Response<CurrentServingResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {

                   final CurrentServing cs = new CurrentServing();

                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                              realm.delete(CurrentServing.class);

//

                                realm.copyToRealmOrUpdate(response.body().getCurrServe());



                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();
                           getView().loadNowServing(response.body().getCurrServe().getCsQueueNo());


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            realm.close();


                            getView().loadNowServing("0");
                        }
                    });

                } else {

                    getView().showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CurrentServingResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }



    public String getTransactionType(int id)
    {

        if(id==1)
            return "Assessment and Payment";
        else
            return "Payment";
    }

    TaxType getTaxType(String id){
        return realm.where(TaxType.class)
                .equalTo("taxTypeId", id)
                .findFirst();
    }

    Barangay getBarangay(String id){
        return realm.where(Barangay.class)
                .equalTo("barangayId", id)
                .findFirst();
    }



    public void onStop() {
        realm.close();
    }
}
