package com.example.yu.small02;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity{


    ViewPager viewPager;
    RadioGroup group;
    private List<Fragment> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        viewPager=findViewById(R.id.viewpager);
        group=findViewById(R.id.group);
        list.add(new FragmentNews());
        list.add(new FragmentQr());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio01:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio02:
                        viewPager.setCurrentItem(1);
                        break;
                    default:break;
                }
            }
        });
    }
}
