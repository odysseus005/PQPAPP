package pasigqueueportal.com.pqpapp.ui.forgot;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;


import org.json.JSONObject;

import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.response.ResultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPresenter extends MvpNullObjectBasePresenter<ForgotView> {
    private String TAG = ForgotPresenter.class.getSimpleName();

    public void submitEmail(String s) {
      getView().startLoading("Loading...");
        App.getInstance().getApiInterface().forgot(Constants.APPJSON ,s)
                .enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        getView().stopLoading();
                        if (response.isSuccessful()) {
                            if (response.body().isSuccess()) {
                                //getView().showAlert(response.body().getMessage());
                                getView().onEmailExist();


                            }else
                            {
                                getView().showAlert(response.body().getMessage());
                            }
                        } else {

                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                getView().showAlert(jObjError.getString("message"));
                            } catch (Exception e) {
                                getView().showAlert("Error Connecting to Server");
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: Error calling register api", t);
                        getView().stopLoading();
                        getView().showAlert("Can't Connect to Server");
                    }
                });
    }




}
