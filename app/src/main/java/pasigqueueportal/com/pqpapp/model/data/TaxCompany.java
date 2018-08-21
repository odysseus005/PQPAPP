package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class TaxCompany extends RealmObject {

    @PrimaryKey
    @SerializedName("company_id")
    @Expose
    private int companyId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_address")
    @Expose
    private String companyAddress;
    @SerializedName("company_lat")
    @Expose
    private String companyLat;
    @SerializedName("company_long")
    @Expose
    private String companyLong;
    @SerializedName("company_contact_number")
    @Expose
    private String companyContact;
    @SerializedName("company_opening")
    @Expose
    private String companyOpening;
    @SerializedName("company_closing")
    @Expose
    private String companyClosing;
    @SerializedName("profile_pic")
    @Expose
    private String companyImage;

    private double distance;


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public int getDealerId() {
        return companyId;
    }

    public void setDealerId(int companyId) {
        this.companyId = companyId;
    }

    public String getDealerName() {
        return companyName;
    }

    public void setDealerName(String companyName) {
        this.companyName = companyName;
    }

    public String getDealerAddress() {
        return companyAddress;
    }

    public void setDealerAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getDealerLat() {
        return companyLat;
    }

    public void setDealerLat(String companyLat) {
        this.companyLat = companyLat;
    }

    public String getDealerLong() {
        return companyLong;
    }

    public void setDealerLong(String companyLong) {
        this.companyLong = companyLong;
    }

    public String getDealerContact() {
        return companyContact;
    }

    public void setDealerContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getDealerOpening() {
        return companyOpening;
    }

    public void setDealerOpening(String companyOpening) {
        this.companyOpening = companyOpening;
    }

    public String getDealerClosing() {
        return companyClosing;
    }

    public void setDealerClosing(String companyClosing) {
        this.companyClosing = companyClosing;
    }

    public String getDealerImage() {
        return companyImage;
    }

    public void setDealerImage(String companyImage) {
        this.companyImage = companyImage;
    }

}
