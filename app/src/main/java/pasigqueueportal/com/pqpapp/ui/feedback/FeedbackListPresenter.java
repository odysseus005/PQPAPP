package pasigqueueportal.com.pqpapp.ui.feedback;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.File;
import java.io.IOException;

import io.realm.Realm;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pasigqueueportal.com.pqpapp.app.App;
import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.data.Feedback;
import pasigqueueportal.com.pqpapp.model.response.FeedbackResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ConstantConditions")
public class FeedbackListPresenter extends MvpBasePresenter<FeedbackListView> {

    public void loadFeedbackList(String token) {

        getView().startLoading();
        App.getInstance().getApiInterface().getFeedback( Constants.APPJSON,Constants.BEARER+token).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(Call<FeedbackResponse> call, final Response<FeedbackResponse> response) {
                getView().stopLoading();
                if (response.isSuccessful()) {


                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.delete(Feedback.class);
                            realm.copyToRealmOrUpdate(response.body().getFeedback());


                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            realm.close();
                            getView().setFeedbackList();

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
            public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                getView().stopLoading();
                getView().stopLoading();
                getView().showError("Error Connecting to Server");
            }
        });


    }

}
