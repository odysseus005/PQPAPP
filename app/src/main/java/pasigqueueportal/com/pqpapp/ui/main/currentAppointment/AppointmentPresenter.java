package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.IOException;

import io.realm.Realm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ConstantConditions")
public class AppointmentPresenter extends MvpBasePresenter<AppointmentView> {

    private Realm realm;

    public void onStart() {
        realm = Realm.getDefaultInstance();
     //   user = App.getUser();
    }

    public void loadAppointmentList(String userID) {

//        getView().startLoading();
//        App.getInstance().getApiInterface().getAppointmentList(Endpoints.GET_APPOINTMENT,String.valueOf(userID))
//                .enqueue(new Callback<AppointmentListResponse>() {
//                    @Override
//                    public void onResponse(Call<AppointmentListResponse> call, final Response<AppointmentListResponse> response) {
//                        if (isViewAttached()) {
//                            getView().stopRefresh();
//                        }
//                        getView().stopLoading();
//                        if (response.isSuccessful()) {
//                            final Realm realm = Realm.getDefaultInstance();
//                            realm.executeTransactionAsync(new Realm.Transaction() {
//                                @Override
//                                public void execute(Realm realm) {
//                                    realm.delete(Appointment.class);
//                                    realm.copyToRealmOrUpdate(response.body().getData());
//
//                                }
//                            }, new Realm.Transaction.OnSuccess() {
//                                @Override
//                                public void onSuccess() {
//                                    realm.close();
//                                    getView().setAppointmentList();
//
//                                }
//                            }, new Realm.Transaction.OnError() {
//                                @Override
//                                public void onError(Throwable error) {
//                                    realm.close();
//                                    error.printStackTrace();
//                                    if (isViewAttached())
//                                        getView().showError(error.getLocalizedMessage());
//                                }
//                            });
//                        } else {
//                            if (isViewAttached())
//                                getView().showError(response.errorBody().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<AppointmentListResponse> call, Throwable t) {
//                        t.printStackTrace();
//                        getView().stopLoading();
//                        if (isViewAttached()) {
//                            getView().stopRefresh();
//                            getView().showError(t.getLocalizedMessage());
//                        }
//                    }
//                });
    }



    public void reserveSched(
                             String userID,
                            String garID,
                             String schedID,
                             String dealerID,
                             String advisorID,
                             String serviceID,
                             String pmsID,
                             String date,
                             String remark) {


//            getView().startLoading();
//            App.getInstance().getApiInterface().registerReservation(Endpoints.RESERVE_TIMESLOT,userID,garID,schedID,dealerID,advisorID,serviceID,pmsID,date,remark)
//                    .enqueue(new Callback<ResultResponse>() {
//                        @Override
//                        public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
//                            getView().stopLoading();
//                            if (response.isSuccessful()) {
//                                switch (response.body().getResult()) {
//                                    case Constants.SUCCESS:
//                                        getView().showReturn("Reservation Successful!");
//
//                                        break;
//                                    case Constants.EMAIL_EXIST:
//                                        getView().showError("Car already exists");
//                                        break;
//                                    default:
//                                        getView().showError("Can't Connect to Server");
//                                        break;
//                                }
//                            } else {
//                                try {
//                                    String errorBody = response.errorBody().string();
//                                    getView().showError(errorBody);
//                                } catch (IOException e) {
//                                    //Log.e(TAG, "onResponse: Error parsing error body as string", e);
//                                    getView().showError(response.message() != null ?
//                                            response.message() : "Unknown Exception");
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResultResponse> call, Throwable t) {
//                            //Log.e(TAG, "onFailure: Error calling register api", t);
//                            getView().stopLoading();
//                            getView().showError("Error Connecting to Server");
//                        }
//                    });


    }


    public void cancelReservation(String id) {

//
//            getView().startLoading();
//            App.getInstance().getApiInterface().cancelReservation(Endpoints.CANCEL_APPOINTMENT,id)
//                    .enqueue(new Callback<ResultResponse>() {
//                        @Override
//                        public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
//                            getView().stopLoading();
//                            if (response.isSuccessful()) {
//                                switch (response.body().getResult()) {
//                                    case Constants.SUCCESS:
//                                        getView().closeDialog("Appointment Cancelled!");
//
//                                        break;
//                                    default:
//                                        getView().showError("Can't Connect to Server");
//                                        break;
//                                }
//                            } else {
//                                try {
//                                    String errorBody = response.errorBody().string();
//                                    getView().showError(errorBody);
//                                } catch (IOException e) {
//                                    //Log.e(TAG, "onResponse: Error parsing error body as string", e);
//                                    getView().showError(response.message() != null ?
//                                            response.message() : "Unknown Exception");
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResultResponse> call, Throwable t) {
//                            //Log.e(TAG, "onFailure: Error calling register api", t);
//                            getView().stopLoading();
//                            getView().showError("Error Connecting to Server");
//                        }
//                    });
        }





    public void onStop() {
        realm.close();
    }
}
