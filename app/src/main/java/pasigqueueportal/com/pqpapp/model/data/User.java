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
    @SerializedName("email_address")
    @Expose
    private String email;

    @SerializedName("assword")
    @Expose
    private String password;

    @SerializedName("name")
    @Expose
    private String name;



    @SerializedName("contact")
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


    @SerializedName("client_code")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String fullName;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFullName() {
        return firstname+" "+lastname;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCivil_status() {
        return civil_status;
    }

    public void setCivil_status(String civil_status) {
        this.civil_status = civil_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image+email+".jpg";
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


}
