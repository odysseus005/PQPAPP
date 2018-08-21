package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import pasigqueueportal.com.pqpapp.model.data.Barangay;


public class BarangayResponse extends  BasicResponse {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }

    public List<Barangay> getBarangay() {
        return barangay;
    }

    public void setBarangay(List<Barangay> barangay) {
        this.barangay = barangay;
    }

    @SerializedName("barangay")
    private List<Barangay> barangay;






}
