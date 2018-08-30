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


    @SerializedName("rating")
    @Expose
    private String feedbackRate;


    @SerializedName("message")
    @Expose
    private String feedbackMessage;



    @SerializedName("created_at")
    @Expose
    private String feedbackCreated;


    @SerializedName("employee")
    private AssignUser feedbackEmployee;



    public String getFeedbackCreated() {
        return feedbackCreated;
    }

    public void setFeedbackCreated(String feedbackCreated) {
        this.feedbackCreated = feedbackCreated;
    }

    public AssignUser getFeedbackEmployee() {
        return feedbackEmployee;
    }

    public void setFeedbackEmployee(AssignUser feedbackEmployee) {
        this.feedbackEmployee = feedbackEmployee;
    }

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
