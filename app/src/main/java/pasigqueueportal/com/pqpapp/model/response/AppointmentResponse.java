package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import pasigqueueportal.com.pqpapp.model.data.Appointment;


public class AppointmentResponse extends  BasicResponse {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @SerializedName("queues")
    private List<Appointment> appointments;






}
