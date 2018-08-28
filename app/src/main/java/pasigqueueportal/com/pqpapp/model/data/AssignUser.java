package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class AssignUser extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int assignId;
    @SerializedName("username")
    @Expose
    private String assignUsername;
    @SerializedName("email")
    @Expose
    private String assignEmail;
    @SerializedName("first_name")
    @Expose
    private String assignFirstname;
    @SerializedName("last_name")
    @Expose
    private String assignLastname;
    @SerializedName("image_link")
    @Expose
    private String assignImage;


    public int getAssignId() {
        return assignId;
    }

    public void setAssignId(int assignId) {
        this.assignId = assignId;
    }

    public String getAssignUsername() {
        return assignUsername;
    }

    public void setAssignUsername(String assignUsername) {
        this.assignUsername = assignUsername;
    }

    public String getAssignEmail() {
        return assignEmail;
    }

    public void setAssignEmail(String assignEmail) {
        this.assignEmail = assignEmail;
    }

    public String getAssignFirstname() {
        return assignFirstname;
    }

    public void setAssignFirstname(String assignFirstname) {
        this.assignFirstname = assignFirstname;
    }

    public String getAssignLastname() {
        return assignLastname;
    }

    public void setAssignLastname(String assignLastname) {
        this.assignLastname = assignLastname;
    }

    public String getAssignImage() {
        return assignImage;
    }

    public void setAssignImage(String assignImage) {
        this.assignImage = assignImage;
    }
    public String getFullName() {
        return assignFirstname + " " + assignLastname;
    }
}
