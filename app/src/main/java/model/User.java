package model;

/**
 * Created by mohamed on 11/20/17.
 */

public class User {
    private String name;
    private String password;
    private String phone;
    private String isStaff;



    public User(){
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.isStaff="False";
    }
    public String getisStaff() {
        return isStaff;
    }

    public void setisStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
