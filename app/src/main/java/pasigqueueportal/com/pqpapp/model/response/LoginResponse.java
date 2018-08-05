package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import pasigqueueportal.com.pqpapp.model.data.Token;
import pasigqueueportal.com.pqpapp.model.data.User;


public class LoginResponse extends BasicResponse {


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @SerializedName("token")
    private Token token;


}
