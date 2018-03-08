package com.huatai.czx.huataiyijia_master.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.webkit.JavascriptInterface;

import com.huatai.czx.huataiyijia_master.bean.TestBean;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by czx on 2018/2/12.
 */

@SuppressLint("JavascriptInterface")
public class WebViewJavascriptBridge {
    private Context context;

    public WebViewJavascriptBridge(Context context) {
        this.context = context;
    }


    // /storage/emulated/0/pic
    public final static String SAVED_IMAGE_PATH1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic";//+"/pic";
    // /storage/emulated/0/Pictures
    public final static String SAVED_IMAGE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();//.getAbsolutePath()+"/pic";//+"/pic";
    String show;
    int i;
    //增加
    @JavascriptInterface
    public void addRealmDataMessage() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        i = i + 1;
        TestBean user = realm.createObject(TestBean.class); // Create a new object
        user.name = "程泽翔";
        user.age = 24 + i;
        user.sex = "男";
        realm.commitTransaction();
    }
    //删除
    @JavascriptInterface
    public void removeRealmDataMessage() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<TestBean> guests = realm.where(TestBean.class).findAll();
        for (TestBean guest : guests) {
                guest.deleteFromRealm();
        }
        realm.commitTransaction();
    }
    //修改
    @JavascriptInterface
    public void alterRealmDataMessageCondition() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<TestBean> guests = realm.where(TestBean.class).findAll();
        for (TestBean guest : guests) {
            guest.age = 666;
        }
        realm.commitTransaction();
    }
    //查询
    @JavascriptInterface
    public String relationSearchMsgAscendAscendKeyBlockFN() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<TestBean> guests = realm.where(TestBean.class).findAll();
        realm.commitTransaction();
        show = "";
        for (TestBean guest : guests) {
            show = show + "/\n" + guest.name + "**" + guest.age + "**" + guest.sex;
        }
        System.out.println(show);
//        MainActivity.this.runOnUiThread(new Runnable(){
//            @Override
//            public void run() {
//                webView.loadUrl("javascript:show('"+ show +"')");
//            }
//        });
        return show;
    }
    //语音播放接口
    @JavascriptInterface
        public void open_TextToVoiceMsg(){
            //Todo..
        }
        //视频播放接口
        @JavascriptInterface
        public void open_player(){
            //Todo..
    }
    //相册接口
    @JavascriptInterface
    public void open_PhotoAlbum(){
        //Todo..
    }
    //相机接口
    @JavascriptInterface
    public void open_photograph(){
        //Todo..
    }
    //调用通信录接口
    @JavascriptInterface
    public void open_telephoneBook(){
        //Todo..
    }
    //分享接口
    @JavascriptInterface
    public void JG_ShareLinkUrlTitleTextImageUrl(){
        //Todo..
    }
    //活脸识别
    @JavascriptInterface
    public void open_MegLive(){
        //Todo..
    }
    //OCR证件接口
    @JavascriptInterface
    public void open_MGIDCard(){
        //Todo..
    }
    //CA签字接口
    @JavascriptInterface
    public void open_BjcaSignUserNameIdNumWONo(){
        //Todo..
    }
    //调取播放器
    public void open_video(){
        //todo..

    }



}
