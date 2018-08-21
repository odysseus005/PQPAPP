package pasigqueueportal.com.pqpapp.ui.main.currentAppointment;

import com.hannesdorfmann.mosby.mvp.MvpView;


import pasigqueueportal.com.pqpapp.model.data.Appointment;


public interface AppointmentView extends MvpView {




    void setAppointmentList();

    void setAppointment();

    void setAppointmentDate();

    void showAppointmentDetails(Appointment appoint);

    void loadTaxType();

    void loadBarangay();

    void layoutSwitch(int switcher);

    void stopRefresh();

    void onFinishTokenRef();

    void showError(String message);

    void showReturn(String message);

    void startLoading();

    void stopLoading();


    void closeDialog(String message);


}
