package pasigqueueportal.com.pqpapp.ui.feedback;

import com.hannesdorfmann.mosby.mvp.MvpView;




public interface FeedbackListView extends MvpView {




    void startLoading();

    void stopLoading();

    void setFeedbackList();

    void stopRefresh();

    void showError(String message);


    void onRefresh();




}
