package com.weather.app.model;

import javax.persistence.*;

/**
 * Created by Mahmoud.Fathy on 4/8/2017.
 */
@Entity
@Table(name = "Users")
public class User {
    private String userName;
    private String email;
    @Column(name = "admin", columnDefinition = "CHAR(1) default 'N'")
    private String admin;
    private String password;
    private String mobileNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public User() {

    }

    public User(String userName, String email, String admin, String password, String mobileNumber) {
        this.userName = userName;
        this.email = email;
        this.admin = admin;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdmin() {
        return admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public boolean isAdmin() {
        return "Y".equals(getAdmin());
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", admin='" + admin + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (admin != null ? !admin.equals(user.admin) : user.admin != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return mobileNumber != null ? mobileNumber.equals(user.mobileNumber) : user.mobileNumber == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        return result;
    }
}
