package com.example.charts.others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.charts.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int images[] = {

            R.drawable.slideshow1,
            R.drawable.slideshow2,
            R.drawable.slideshow3


    };

    int headings[] = {

            R.string.titl_slide1,
            R.string.titl_slide2,
            R.string.titl_slide3

    };

    int description[] = {

            R.string.des_slideshow,
            R.string.des_slideshow,
            R.string.des_slideshow

    };

    public ViewPagerAdapter(Context context){

        this.context = context;

    }

    @Override
    public int getCount() {
        return  headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.titleImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.texttitle);
        TextView slideDesciption = (TextView) view.findViewById(R.id.textdeccription);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDesciption.setText(description[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }
}
