package com.example.charts.others;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.charts.R;
import com.example.charts.activities.Register;

public class SliderOpenning extends AppCompatActivity {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;

    ImageView nextbtn;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_openning);



        nextbtn = findViewById(R.id.next);




        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) < 2)
                    mSLideViewPager.setCurrentItem(getitem(1),true);
                else {

                    Intent i = new Intent(getApplicationContext(), Register.class);
                    startActivity(i);
                    finish();

                }

            }
        });



        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setUpindicator(int position){

        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);

        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }



    }