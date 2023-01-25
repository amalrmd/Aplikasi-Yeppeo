package com.example.iyeppeo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {
Context context;
LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }
    public int[] slide_image = {
            R.drawable.ob1,
            R.drawable.ob2
    };
    public String[] slide_heading ={
            "Skincare Routine",
            "Review"
    };
    public String[] slide_desc ={
            "Membantumu melakukan rutinitas skincare secara teratur.",
            "Tuliskan pendapatmu mengenai skincare yang kamu kenakan :)"
    };
    @Override
    public int getCount() {

        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return view == (RelativeLayout) o;

    }

    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView SlideImgView = (ImageView) view.findViewById(R.id.yep_remo);
        TextView TextViewHeading = (TextView) view.findViewById(R.id.skincare_ro);
        TextView TextViewDesc = (TextView) view.findViewById(R.id.membantumu_);

        SlideImgView.setImageResource(slide_image[position]);
        TextViewHeading.setText(slide_heading[position]);
        TextViewDesc.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        container.removeView((RelativeLayout)object);
    }
}
