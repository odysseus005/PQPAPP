package pasigqueueportal.com.pqpapp.ui.main.pastAppointment;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import io.realm.Realm;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Barangay;
import pasigqueueportal.com.pqpapp.model.data.TaxType;
import pasigqueueportal.com.pqpapp.model.response.AppointmentResponse;
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

//            getView().startLoading();
//            App.getInstance().getApiInterface().getUserAppointment( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<AppointmentResponse>() {
//                @Override
//                public void onResponse(Call<AppointmentResponse> call, final Response<AppointmentResponse> response) {
//                    getView().stopLoading();
//                    if (response.isSuccessful()) {
//
//
//                        final Realm realm = Realm.getDefaultInstance();
//                        realm.executeTransactionAsync(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                realm.delete(Appointment.class);
//                                realm.copyToRealmOrUpdate(response.body().getAppointments());
//
//
//                            }
//                        }, new Realm.Transaction.OnSuccess() {
//                            @Override
//                            public void onSuccess() {
//                                realm.close();
//                                getView().setAppointmentList();
//
//                            }
//                        }, new Realm.Transaction.OnError() {
//                            @Override
//                            public void onError(Throwable error) {
//                                realm.close();
//                                getView().showError("Error Saving API Response");
//                            }
//                        });
//
//                    } else {
//
//                        getView().showError(response.body().getMessage());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<AppointmentResponse> call, Throwable t) {
//                    getView().stopLoading();
//                    getView().stopLoading();
//                    getView().showError("Error Connecting to Server");
//                }
//            });


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
