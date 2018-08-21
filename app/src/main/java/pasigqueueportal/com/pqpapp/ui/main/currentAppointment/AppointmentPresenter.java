package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.IOException;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.model.response.AppointmentResponse;
import pasigqueueportal.com.pqpapp.model.response.BarangayResponse;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import pasigqueueportal.com.pqpapp.model.response.TaxTypeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ConstantConditions")
public class AppointmentPresenter extends MvpBasePresenter<AppointmentView> {

    private Realm realm;
    public void onStart() {
        realm = Realm.getDefaultInstance();


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
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }







    public void onStop() {
        realm.close();
    }
}
