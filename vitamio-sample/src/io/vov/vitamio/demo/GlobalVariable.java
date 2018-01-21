package io.vov.vitamio.demo;

import android.app.Application;

/**
 * Created by User on 2017/9/28.
 */

public class GlobalVariable extends Application {

    private long Age;         //User 年紀
    private String Test ;

    //修改 變數値
    public void setAge(long age){
        this.Age = age;
    }
    //取得 變數值
    public long getAge(){
        return Age;
    }
    //修改 變數値
    public void setTest(String test){
        this.Test = test;
    }
    //取得 變數值
    public String getTest(){
        return Test;
    }


}
