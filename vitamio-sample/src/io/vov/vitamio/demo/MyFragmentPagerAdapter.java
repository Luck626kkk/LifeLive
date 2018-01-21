package io.vov.vitamio.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        String A;


        private String[] mTitles = new String[]{"首頁", "分類","直播", "訂單", "會員"};

        public MyFragmentPagerAdapter(FragmentManager fm, String email) {
            super(fm);
            A=email;
        }

        @Override
        public Fragment getItem(int position) {
           if (position == 1) {
                return new F_Search();
            } else if (position == 2) {
                return new F_Create();
            } else if (position == 3) {
                return new F_Shopping();
            } else if (position == 4) {
                return new F_Member();
            }
            return new F_Index();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        //ViewPager與TabLayout綁定後，這裡獲取到PageTitle就是Tab的Text
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

