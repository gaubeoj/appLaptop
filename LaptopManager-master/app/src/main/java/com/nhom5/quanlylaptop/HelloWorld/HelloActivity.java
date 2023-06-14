package com.nhom5.quanlylaptop.HelloWorld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhom5.quanlylaptop.Activity.PickRole_Activity;
import com.nhom5.quanlylaptop.PagerAdapter.Hello_PagerAdapter;
import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

import me.relex.circleindicator.CircleIndicator;

public class HelloActivity extends AppCompatActivity {

    private TextView skip;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    Context context = this;
    AppCompatButton button_Next, button_Prev;
    String TAG = "HelloActivity_____";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_activity);
        initUI();

        button_Prev.setVisibility(View.GONE);
        Hello_PagerAdapter helloPagerAdapter = new Hello_PagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(helloPagerAdapter);
        circleIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    button_Prev.setVisibility(View.GONE);
                } else {
                    button_Prev.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initUI() {
        skip = findViewById(R.id.textView_Skip);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
        button_Next = findViewById(R.id.button_Next);
        button_Prev = findViewById(R.id.button_Prev);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Check_FirstTime", MODE_PRIVATE);
                if (pref != null){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("check", "true");
                    editor.apply();
                }
                Intent intent = new Intent(HelloActivity.this, PickRole_Activity.class);
                startActivity(intent);
            }
        });

        button_Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() > 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
        });

        button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    SharedPreferences pref = getSharedPreferences("Check_FirstTime", MODE_PRIVATE);
                    if (pref != null){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("check", "true");
                        editor.apply();
                    }
                    Intent intent = new Intent(HelloActivity.this, PickRole_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }
}