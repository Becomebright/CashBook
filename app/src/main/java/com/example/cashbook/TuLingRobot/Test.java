package com.example.cashbook.TuLingRobot;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by dsz62 on 2017/7/12.
 */

public class Test extends AndroidTestCase {
    public void test(){
        GetDataUtils dataUtils=new GetDataUtils();
        Message message=dataUtils.getInfo("你好");
        Message message1=dataUtils.getInfo("你是谁");
        Message message2=dataUtils.getInfo("你知道JAVA是什么吗");
        Message message3=dataUtils.getInfo("下雨了，天好冷");
        Log.i("兔子",message.getMsg());
        Log.i("兔子",message1.getMsg());
        Log.i("兔子",message2.getMsg());
        Log.i("兔子",message3.getMsg());

    }
}
