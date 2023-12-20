package com.example.charts.user_Builder;

public class User_Builder implements Abstract_User_Builder {

    private String username;
    private String email;
    private String password;
    private String userType; // "user" or "admin"
    private String ssn;
    private String phone;
    private String birthday;

    private String gender;
    public Abstract_User_Builder set_username(String name)
    {
        this.username=name;
        return this;

    }
    public Abstract_User_Builder set_email(String email)
    {
        this.email=email;
        return this;
    }
    public Abstract_User_Builder set_password(String password)
    {
        this.password=password;
        return this;
    }
    public Abstract_User_Builder set_userType(String userType)
    {
        this.userType=userType;
        return this;
    }
    public Abstract_User_Builder set_ssn(String ssn)
    {
        this.ssn=ssn;
        return this;
    }
    public Abstract_User_Builder set_phone(String phone)
    {
        this.phone=phone;
        return this;
    }
    public Abstract_User_Builder set_gender(String gender)
    {
        this.gender=gender;
        return this;
    }
    public Abstract_User_Builder set_birthday(String birthday)
    {
        this.birthday=birthday;
        return this;
    }
    public User build_User()
    {
        return new User(username,email,password,userType,ssn,phone,birthday,gender);
    }
}
