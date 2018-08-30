package pasigqueueportal.com.pqpapp.ui.main.pastAppointment;

import com.hannesdorfmann.mosby.mvp.MvpView;

import pasigqueueportal.com.pqpapp.model.data.Appointment;


public interface PastAppointmentView extends MvpView {




    void setAppointmentList();


    void viewDetails(Appointment appoint);

    void stopRefresh();

    void showError(String message);

    void showReturn(String message);

    void startLoading();

    void stopLoading();



}
