package io.vov.vitamio.demo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout.Tab live;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getSupportActionBar().hide();//隱藏掉整個ActionBar
        setContentView(R.layout.activity_main2);

        //--------------------(先砍)
        Bundle bundle = this.getIntent().getExtras();
        email = bundle.getString("key_show_String");




        //初始化視圖
        initViews();
    }

    private void initViews() {

        //使用適配器將ViewPager與Fragment綁定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), email);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //將TabLayout與ViewPager綁定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        live=mTabLayout.getTabAt(2);
        three = mTabLayout.getTabAt(3);
        four = mTabLayout.getTabAt(4);

        //設置Tab的圖標，假如不需要則把下面的代碼刪去
        one.setIcon(R.drawable.logo);
        two.setIcon(R.drawable.classicon);
        three.setIcon(R.drawable.createicon);
        four.setIcon(R.drawable.shopicon);
        live.setIcon(R.drawable.membericon);

    }
}