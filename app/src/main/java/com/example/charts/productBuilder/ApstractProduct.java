package com.example.charts.productBuilder;

public interface ApstractProduct {

    ApstractProduct setTitel(String titel);
    ApstractProduct setByte_image(byte [] image);
    ApstractProduct setcategory(String category);
    ApstractProduct setdescription(String description);
    ApstractProduct setsalary(int salary);
    ApstractProduct setsize(String size);
    ApstractProduct setsale(float sale);
    ApstractProduct setQr_code(String qr);
    ApstractProduct setNumber(int number);
    Product build();

}
