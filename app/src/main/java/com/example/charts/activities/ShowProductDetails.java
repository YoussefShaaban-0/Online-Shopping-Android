package com.example.charts.activities;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

import com.example.charts.adapters.Comment_list;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

import java.util.List;

public class ShowProductDetails extends AppCompatActivity {

    ImageView imageinview,gotohome;
    TextView  catagoryinview,descreptioninview,salary,numberofitem,titel , sale;
    Button s,l,xl,xxl,m,addtochart,send_button,go_to_comments;
    String name="";
    String descreptiontocart="";
    byte []imagetocart;
    int price=0;
    float sales = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_details);

        imageinview=findViewById(R.id.proImage);
        sale = findViewById(R.id.productsaleSale);
        catagoryinview=findViewById(R.id.productcatagory);
        descreptioninview=findViewById(R.id.descriptionView);
        salary=findViewById(R.id.priceView);
        numberofitem=findViewById(R.id.avilabel);
        titel=findViewById(R.id.titelonview);
        s=findViewById(R.id.small);
        m=findViewById(R.id.medium);
        l=findViewById(R.id.large);
        xl=findViewById(R.id.xlarge);
        xxl=findViewById(R.id.xxlarge);
        gotohome=findViewById(R.id.arrow_back);
        addtochart=findViewById(R.id.addtochart);
        send_button=findViewById(R.id.Save);
        go_to_comments=findViewById(R.id.comments);
        RatingBar ratingBar=(RatingBar)findViewById(R.id.RatingUs);
        RatingBar ratingBar_result=(RatingBar)findViewById(R.id.RatingBar);
        EditText comment=findViewById(R.id.feedbackbox);


        Databasemanger databasemanger=Databasemanger.getInstance(this);

        Intent fromhome=getIntent();
        long id=fromhome.getLongExtra("id",-1);

        if(id!=-1) {
            Cursor cursor = databasemanger.fetchWithId(id);
            if (cursor != null && cursor.moveToFirst()) {

                name=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.titel));
                descreptiontocart=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.description));
                imagetocart=cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.image));
                price=Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.salary)));
                sales = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.sale));
                Rating rating=Rating.build_rate();
                rating.setProduct_title(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.titel)));
                byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.image));
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                imageinview.setImageBitmap(bitmap);
                titel.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.titel)));
                descreptioninview.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.description)));
                salary.setText("$"+cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.salary)));
                catagoryinview.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.category)));
                numberofitem.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.itemNumber))+"Avilabel on the stock");
                String qr_code=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.qr_code));
                sale.setText(" Sale %"+cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.sale)));

                Toast.makeText(getApplicationContext(), qr_code, Toast.LENGTH_SHORT).show();

                String size=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.size));
                int whiteColor = ResourcesCompat.getColor(getResources(), R.color.white, null);
                if(size.equals("s")){
                    s.setBackgroundResource(R.drawable.shape_button);
                    s.setTextColor(whiteColor);

                } else if (size.equals("m")) {
                    m.setBackgroundResource(R.drawable.shape_button);
                    m.setTextColor(whiteColor);
                }
                else if (size.equals("l")) {
                    l.setBackgroundResource(R.drawable.shape_button);
                    l.setTextColor(whiteColor);
                }
                else if (size.equals("xl")) {
                    xl.setBackgroundResource(R.drawable.shape_button);
                    xl.setTextColor(whiteColor);
                }
                else if (size.equals("xxl")) {
                    xxl.setBackgroundResource(R.drawable.shape_button);
                    xxl.setTextColor(whiteColor);
                }

                Toast.makeText(this, size, Toast.LENGTH_LONG).show();

                // Close the cursor when done with it
                cursor.close();

            }}

        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromhome.getComponent() != null) {
                    String callingActivityClassName = fromhome.getComponent().getClassName();
                    // Check if the class name does not match the Home activity
                    if (!callingActivityClassName.equals(Home.class.getName())) {
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                }
            }
        });

        addtochart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
                String loggedInUserEmail = sharedPreferences.getString(userininlogin,"no");
                Toast.makeText(getApplicationContext(), loggedInUserEmail, Toast.LENGTH_SHORT).show();

               databasemanger.addItemToCart(loggedInUserEmail,name,descreptiontocart,imagetocart, price,sales,1);
                startActivity(new Intent(getApplicationContext(), CartShopping.class));
            }
        });
        Rating rating=Rating.build_rate();
        float rat;
        rat=(float)databasemanger.Product_count_rating(rating.getProduct_title());
        go_to_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check if the class name does not match the Home activity

                openac(titel.getText().toString());
                //******************************************

            }
        });

        ratingBar_result.setRating(rat);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Rating rating=Rating.build_rate();
                int x= (int) ratingBar.getRating();

                boolean xx= databasemanger.checkrate(rating.getCustomer_email(), rating.getProduct_title());
                if(xx) {
                    if (databasemanger.updaterate(rating.getCustomer_email(), rating.getProduct_title(), x))
                        Toast.makeText(getApplicationContext(), "your rating is Updated ", Toast.LENGTH_SHORT).show();
                }
                else {


                    boolean insert = databasemanger.insert_rating(rating.getCustomer_email(), rating.getProduct_title(), x);
                    if (insert) {

                        Toast.makeText(getApplicationContext(), "Rating has been saved", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getApplicationContext(), "Rating not saved", Toast.LENGTH_SHORT).show();
                    }
                }
                float rat2=(float)databasemanger.Product_count_rating(rating.getProduct_title());
                ratingBar_result.setRating(rat2);

                String Comment=comment.getText().toString();
                if(Comment.length()==0)
                {
                    // comment.setText(s);
                }
                else
                {

                    if(databasemanger.insert_FEEDBACK(rating.getCustomer_email(), rating.getProduct_title(), Comment))
                        Toast.makeText(getApplicationContext(), "Comment added", Toast.LENGTH_SHORT).show();
                }
                List<Rating> all_comments=Rating.getRatings();
                all_comments=databasemanger.Display_feedbacks(rating.getProduct_title());

                //Intent intent = new Intent(ShowProductDetails.this, Comment_list.class);

                //startActivity(intent);

                //startActivity(new Intent(getApplicationContext(), ShowProductDetails.class));

            }  });
    }
    public void openac(String title)
    {

        Intent intent=new Intent(this, Comment_list.class);
        intent.putExtra("title",title);
        startActivity(intent);

    }
}