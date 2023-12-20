package com.example.charts.user_Builder;

public interface Abstract_User_Builder {
    Abstract_User_Builder set_username(String name);
    Abstract_User_Builder set_email(String email);
    Abstract_User_Builder set_password(String password);
    Abstract_User_Builder set_userType(String userType);
    Abstract_User_Builder set_ssn(String ssn);
    Abstract_User_Builder set_phone(String phone);
    Abstract_User_Builder set_gender(String gender);
    Abstract_User_Builder set_birthday(String birthday);
    User build_User();

}
