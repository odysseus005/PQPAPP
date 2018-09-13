package pasigqueueportal.com.pqpapp.ui.main.pastAppointment;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.CurrentServing;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.model.response.AppointmentResponse;
import pasigqueueportal.com.pqpapp.model.response.CurrentServingResponse;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ConstantConditions")
public class PastAppointmentPresenter extends MvpBasePresenter<PastAppointmentView> {

    private Realm realm;

    public void onStart() {
        realm = Realm.getDefaultInstance();
     //   user = App.getUser();
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
    public void sendFeedback(String token,String employee_id,String queueid,String rating,String message)
    {
        getView().startLoading();
        App.getInstance().getApiInterface().sendFeedback( Constants.APPJSON,Constants.BEARER+token,employee_id,queueid,rating,message).enqueue(new Callback<ResultResponse>() {
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
                                getView().showReturn("Thank you for your feedback!");

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

    public String getStatus(String p)
    {
        String returnStats="";

        if (p.toLowerCase().contains("pe"))
                returnStats = "P";
        else if((p.toLowerCase().contains("no")))
                returnStats = "N";
        else if((p.toLowerCase().contains("su")))
            returnStats = "S";
        else if((p.toLowerCase().contains("ca")))
            returnStats = "C";
        else if((p.toLowerCase().contains("un")))
            returnStats = "U";
        else
            returnStats = "";



        return  returnStats;

    }

    public String searchTransactionType(String trans)
    {

        if(trans.toLowerCase().contains("assessment"))
            return "1";
        else if (trans.toLowerCase().contains("payment"))
            return "2";
        else
            return "0";
    }


    TaxType searchTaxType(String id){
        return realm.where(TaxType.class)
                .contains("taxTypeDesc", id)
                .or()
                .equalTo("taxTypeDesc",id)
                .findFirst();
    }

    Barangay searchBarangay(String id){
        return realm.where(Barangay.class)
                .contains("barangayName", id)
                .or()
                .equalTo("barangayName",id)
                .findFirst();
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


    public void onStop() {
        realm.close();
    }
}
