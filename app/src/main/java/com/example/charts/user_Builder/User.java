package com.example.charts.user_Builder;

public class User {
    private String username;
    private String email;
    private String password;
    private String userType; // "user" or "admin"
    private String ssn;
    private String phone;
    private String birthday;
    private String gender;

    public User(String username, String email, String password, String userType, String ssn, String phone,String birthday, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.ssn = ssn;
        this.phone = phone;
        this.birthday=birthday;
        this.gender = gender;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getSsn() {
        return ssn;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }
}