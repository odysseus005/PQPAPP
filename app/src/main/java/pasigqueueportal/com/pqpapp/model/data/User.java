package pasigqueueportal.com.pqpapp.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class User extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int userId;
    @SerializedName("username")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;



    @SerializedName("first_name")
    @Expose
    private String firstname;
    @SerializedName("last_name")
    @Expose
    private String lastname;



    @SerializedName("contact_no")
    @Expose
    private String contact;


    @SerializedName("birthday")
    @Expose
    private String birthday;



    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("status")
    @Expose
    private String firstlogin;


    @SerializedName("code")
    @Expose
    private String code;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstlogin() {
        return firstlogin;
    }

    public void setFirstlogin(String firstlogin) {
        this.firstlogin = firstlogin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getFullName() {
        return firstname + " " + lastname;
    }

}
