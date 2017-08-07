package netroxtech.com.appraasr.Models;

/**
 * Created by mac on 6/19/2017.
 */

public class CreateAccount {

    private int id;
    private String  firstName=null;
    private String lastName=null;
    private String  email=null;
    private String  userName=null;
    private String password=null;
    private String  facebookApiKey=null;
    private String userType=null;
    private String profileImage = null;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookApiKey() {
        return facebookApiKey;
    }

    public void setFacebookApiKey(String facebookApiKey) {
        this.facebookApiKey = facebookApiKey;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
