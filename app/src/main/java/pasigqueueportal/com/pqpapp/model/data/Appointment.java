package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * @author pocholomia
 * @since 12/4/2016
 */
public class Appointment extends RealmObject {




    @SerializedName("id")
    @PrimaryKey
    private String appointmentId;
    @SerializedName("transaction_type")
    private String appointmentTransType;
    @SerializedName("tax_type_id")
    private String appointmentTaxType;
    @SerializedName("barangay_id")
    private String appointmentBaranagay;
    @SerializedName("transaction_date")
    private String appointmentTransDate;
    @SerializedName("eta")
    private String appointmentTransTime;
    @SerializedName("status")
    private String appointmentTransStatus;
    @SerializedName("queue_no")
    private String appointmentTransQueue;

    public String getAppointmentTransDate() {
        return appointmentTransDate;
    }


    @SerializedName("trans_datetime")
    private Date appointmentTimestamp;


    public void setAppointmentTransDate(String appointmentTransDate) {
        this.appointmentTransDate = appointmentTransDate;
    }

    public Date getAppointmentTimestamp() {
        return appointmentTimestamp;
    }

    public void setAppointmentTimestamp(Date appointmentTimestamp) {
        this.appointmentTimestamp = appointmentTimestamp;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTransType() {
        return appointmentTransType;
    }

    public void setAppointmentTransType(String appointmentTransType) {
        this.appointmentTransType = appointmentTransType;
    }

    public String getAppointmentTaxType() {
        return appointmentTaxType;
    }

    public void setAppointmentTaxType(String appointmentTaxType) {
        this.appointmentTaxType = appointmentTaxType;
    }

    public String getAppointmentBaranagay() {
        return appointmentBaranagay;
    }

    public void setAppointmentBaranagay(String appointmentBaranagay) {
        this.appointmentBaranagay = appointmentBaranagay;
    }



    public String getAppointmentTransTime() {
        return appointmentTransTime;
    }

    public void setAppointmentTransTime(String appointmentTransTime) {
        this.appointmentTransTime = appointmentTransTime;
    }

    public String getAppointmentTransStatus() {
        return appointmentTransStatus;
    }

    public void setAppointmentTransStatus(String appointmentTransStatus) {
        this.appointmentTransStatus = appointmentTransStatus;
    }

    public String getAppointmentTransQueue() {
        return appointmentTransQueue;
    }

    public void setAppointmentTransQueue(String appointmentTransQueue) {
        this.appointmentTransQueue = appointmentTransQueue;
    }


    public Appointment() {
    }



}
