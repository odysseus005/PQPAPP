package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import pasigqueueportal.com.pqpapp.model.data.Appointment;
import pasigqueueportal.com.pqpapp.model.data.Slot;


public class SlotResponse extends  BasicResponse {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    @SerializedName("limit")
    private String limit;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public List<Slot> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Slot> appointments) {
        this.appointments = appointments;
    }

    @SerializedName("counts")
    private List<Slot> appointments;






}
