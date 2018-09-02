package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class CurrentServing extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String csId;
    @SerializedName("window_id")
    @Expose
    private String csWindowId;


    @SerializedName("queue_no")
    @Expose
    private String csQueueNo;


    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public String getCsWindowId() {
        return csWindowId;
    }

    public void setCsWindowId(String csWindowId) {
        this.csWindowId = csWindowId;
    }

    public String getCsQueueNo() {
        return csQueueNo;
    }

    public void setCsQueueNo(String csQueueNo) {
        this.csQueueNo = csQueueNo;
    }

}
