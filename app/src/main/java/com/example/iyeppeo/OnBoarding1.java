package com.example.iyeppeo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class OnBoarding1 extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SlideAdapter slideAdapter;
    private TextView[] dots;
    private Button button;
    private int mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding1);

        viewPager = (ViewPager) findViewById(R.id.slideviewpager);
        linearLayout = (LinearLayout) findViewById(R.id.linedots);
        button = (Button) findViewById(R.id.small_butto);
        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);

        addDostIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);    }

    public void addDostIndicator(int position){
        dots = new TextView[2];
        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.btnpink, getApplicationContext().getTheme()));
            linearLayout.addView(dots[i]);

        }
        if (dots.length > 0 ){
            dots[position].setTextColor(getColor(R.color.bgpink));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
        addDostIndicator(i);
        mCurrentPage = i;
        if(i  == 0){
            button.setEnabled(true);

            }else {
                button.setEnabled(true);
                button.setText("Continue");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OnBoarding1.this,Login.class);
                    startActivity(intent);

                }
            });
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}