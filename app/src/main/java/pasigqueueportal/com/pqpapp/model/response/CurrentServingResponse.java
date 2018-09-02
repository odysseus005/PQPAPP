package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import pasigqueueportal.com.pqpapp.model.data.CurrentServing;


public class CurrentServingResponse extends  BasicResponse {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public CurrentServing getCurrServe() {
        return currServe;
    }

    public void setCurrServe(CurrentServing currServe) {
        this.currServe = currServe;
    }

    @SerializedName("curr_serving")
    private CurrentServing currServe;






}
