package com.example.charts.user_Builder;

import com.example.charts.user_Builder.User;
import com.example.charts.user_Builder.User_Builder;

public class User_factory {

    public User create_user(String username, String email, String password, String userType, String ssn, String phone, String birthday, String gender)
    {
        if(userType.equals("admin")) {
            return new User_Builder()
                    .set_email(email)
                    .set_gender(gender)
                    .set_userType(userType)
                    .set_password(password)
                    .set_ssn(ssn)
                    .set_username(username)
                    .set_phone(phone)
                    .set_birthday(birthday)
                    .build_User();
        } else if(userType.equals("Customer")) {
            return new User_Builder()
                    .set_email(email)
                    .set_gender(gender)
                    .set_userType(userType)
                    .set_password(password)
                    .set_ssn(ssn)
                    .set_username(username)
                    .set_phone(phone)
                    .set_birthday(birthday)
                    .build_User();
        }
        else
            return null;

    }
}
