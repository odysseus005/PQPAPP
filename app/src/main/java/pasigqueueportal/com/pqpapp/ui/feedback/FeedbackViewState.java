package pasigqueueportal.com.pqpapp.ui.feedback;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState;


class FeedbackViewState implements RestorableViewState<FeedbackListView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<FeedbackListView> restoreInstanceState(Bundle in) {
        return this;
    }

    @Override
    public void apply(FeedbackListView view, boolean retained) {

    }
}
