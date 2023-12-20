package com.example.charts.activities;

import java.util.ArrayList;
import java.util.List;

public class Rating {
    private float rate;
    private float total_rate_product;
    private String Customer_email;
    private String product_title;
    private static Rating rating;
    private static List<Rating> ratings;
    private  String feedback;
    private Rating()
    {}
    public static Rating build_rate()
    {
        if(rating==null)
            rating=new Rating();
        return rating;
    }
    public static List<Rating> getRatings()
    {
        if(ratings==null)
            ratings=new ArrayList<>();
        return ratings;
    }

    public Rating(String customer_email, String product_title, String feedback) {
        Customer_email = customer_email;
        this.product_title = product_title;
        this.feedback = feedback;
    }

    public Rating( String customer_email,float rate, String feedback) {
        this.rate = rate;
        Customer_email = customer_email;
        this.feedback = feedback;
    }

    public  String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public float getRate() {
        return rate;
    }

    public float getTotal_rate_product() {
        return total_rate_product;
    }

    public String getCustomer_email() {
        return Customer_email;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setTotal_rate_product(float total_rate_product) {
        this.total_rate_product = total_rate_product;
    }

    public void setCustomer_email(String customer) {
        Customer_email = customer;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }
}
