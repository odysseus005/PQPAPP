package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Slot extends RealmObject {

    @PrimaryKey
    @SerializedName("transaction_date")
    @Expose
    private String slotDate;

    public String getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(String slotDate) {
        this.slotDate = slotDate;
    }

    public String getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(String slotCount) {
        this.slotCount = slotCount;
    }

    @SerializedName("count")
    @Expose
    private String slotCount;

}
