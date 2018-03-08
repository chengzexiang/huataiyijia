package com.huatai.czx.huataiyijia_master;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;

/**
 * Application
 * Created by czx on 2018/2/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
