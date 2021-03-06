package pasigqueueportal.com.pqpapp.ui.register;

import com.hannesdorfmann.mosby.mvp.MvpView;


public interface RegisterView extends MvpView {

    void onSubmit();


    void showAlert(String message);


    void startLoading();

    void stopLoading();

    void onRegistrationSuccess();


    void onBirthdayClicked();


}
