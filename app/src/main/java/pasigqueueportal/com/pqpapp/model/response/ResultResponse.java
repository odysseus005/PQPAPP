package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import pasigqueueportal.com.pqpapp.app.Constants;
import pasigqueueportal.com.pqpapp.model.data.User;


public class ResultResponse extends  BasicResponse {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }




    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }





}
