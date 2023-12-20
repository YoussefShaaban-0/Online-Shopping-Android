package com.example.charts.productBuilder;

public class Product {

    private  String titel ;
    private  byte[] image ;
    private  String category ;
    private  int salary ;
    private  String description ;
    private  String size ;
    private  float sale;
    private  String qr_code ;
    private int itemNumber ;

    public Product(String titel, byte[] image, String category, int salary, String description, String size, float sale, String qr_code, int itemNumber) {
        this.titel = titel;
        this.image = image;
        this.category = category;
        this.salary = salary;
        this.description = description;
        this.size = size;
        this.sale = sale;
        this.qr_code = qr_code;
        this.itemNumber = itemNumber;
    }

    public String getTitel() {
        return titel;
    }

    public byte[] getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public int getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public String getQr_code() {
        return qr_code;
    }

    public float getSale() {
        return sale;
    }
}
