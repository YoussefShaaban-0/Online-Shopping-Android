package com.example.charts.productBuilder;

public class ProductBuilder implements ApstractProduct {

    private  String titel ;
    private  byte[] image ;
    private  String category ;
    private  int salary ;
    private  String description ;
    private  String size ;
    private  float sale ;
    private  String qr_code ;
    private int itemNumber ;


    @Override
    public ApstractProduct setTitel(String titel) {
        this.titel=titel;
        return this;
    }




    @Override
    public ApstractProduct setByte_image(byte[] image) {
        this.image=image;
        return this;
    }

    @Override
    public ApstractProduct setcategory(String category) {
        this.category=category;
        return this;
    }

    @Override
    public ApstractProduct setsalary(int salary) {
        this.salary=salary;
        return this;
    }

    @Override
    public ApstractProduct setdescription(String description) {
        this.description=description;
        return this;
    }

    @Override
    public ApstractProduct setsize(String size) {
        this.size=size;
        return this;
    }

    @Override
    public ApstractProduct setsale(float sal) {
        this.sale=sal;
        return this;
    }

    @Override
    public ApstractProduct setNumber(int number) {
        this.itemNumber=number;
        return this;
    }
    @Override
    public ApstractProduct setQr_code(String qr) {
        this.qr_code=qr;
        return this;
    }

    @Override
    public Product build() {
        return new Product(this.titel,this.image,this.category,this.salary,this.description,this.size,this.sale,this.qr_code,this.itemNumber);

    }
}
