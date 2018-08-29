package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Feedback extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String feedbackId;


    @SerializedName("rate")
    @Expose
    private String feedbackRate;


    @SerializedName("message")
    @Expose
    private String feedbackMessage;


    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(String feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

}
