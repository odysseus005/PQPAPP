package pasigqueueportal.com.pqpapp.ui.login;


import com.hannesdorfmann.mosby.mvp.MvpView;

import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.data.User;


public interface LoginView extends MvpView {

    void onLoginButtonClicked();


    void onRegisterButtonClicked();

    void showAlert(String message);

    void setEditTextValue(String username, String password);

    void startLoading();

    void stopLoading();

    void onLoginSuccess(Token token);

    void onLoginConfirm(User user);

    void onForgotPasswordButtonClicked();


}
