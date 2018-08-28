package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Window extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int paymentId;
    @SerializedName("description")
    @Expose
    private String paymentDesc;
    @SerializedName("assigned_id")
    @Expose
    private String paymentAssignId;
    @SerializedName("barangay_group_id")
    @Expose
    private String paymentBarangayGroupId;



    @SerializedName("assigned_user")
    private AssignUser assignedUser;




    public AssignUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AssignUser assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getPaymentAssignId() {
        return paymentAssignId;
    }

    public void setPaymentAssignId(String paymentAssignId) {
        this.paymentAssignId = paymentAssignId;
    }

    public String getPaymentBarangayGroupId() {
        return paymentBarangayGroupId;
    }

    public void setPaymentBarangayGroupId(String paymentBarangayGroupId) {
        this.paymentBarangayGroupId = paymentBarangayGroupId;
    }

}
