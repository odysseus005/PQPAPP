package pasigqueueportal.com.pqpapp.ui.location;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import pasigqueueportal.com.pqpapp.model.data.TaxCompany;


public interface MapView extends MvpView {



    void startLoading();

    void stopLoading();

    void showAlert(String s);

    void updateMap();
}
