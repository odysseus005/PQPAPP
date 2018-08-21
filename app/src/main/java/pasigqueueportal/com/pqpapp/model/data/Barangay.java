package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Barangay extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String barangayId;


    @SerializedName("name")
    @Expose
    private String barangayName;


    public String getBarangayId() {
        return barangayId;
    }

    public void setBarangayId(String barangayId) {
        this.barangayId = barangayId;
    }

    public String getBarangayName() {
        return barangayName;
    }

    public void setBarangayName(String barangayName) {
        this.barangayName = barangayName;
    }


}
