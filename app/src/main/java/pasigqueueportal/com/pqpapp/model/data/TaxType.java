package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class TaxType extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String taxTypeId;
    @SerializedName("description")
    @Expose
    private String taxTypeDesc;
    @SerializedName("status")
    @Expose
    private String taxTypeStatus;

    public String getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(String taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public String getTaxTypeDesc() {
        return taxTypeDesc;
    }

    public void setTaxTypeDesc(String taxTypeDesc) {
        this.taxTypeDesc = taxTypeDesc;
    }

    public String getTaxTypeStatus() {
        return taxTypeStatus;
    }

    public void setTaxTypeStatus(String taxTypeStatus) {
        this.taxTypeStatus = taxTypeStatus;
    }

}
