package com.huatai.czx.huataiyijia_master.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.huatai.czx.huataiyijia_master.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class jsActivity extends AppCompatActivity {
    private WebView webviewjs;
    private JCVideoPlayerStandard videopayer;
    private Button startvideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);

        initView();
        //允许js加载到webview
        webviewjs.getSettings().setJavaScriptEnabled(true);
        //布局适配手机
        webviewjs.getSettings().setLoadWithOverviewMode(true);
        webviewjs.loadUrl("/mnt/shared/Other/index.html");
//        webviewjs.loadUrl("http://www.baidu.com");
        webviewjs.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        startvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(jsActivity.this,ReactActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        webviewjs = (WebView) findViewById(R.id.webviewjs);
        videopayer = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        startvideo = (Button) findViewById(R.id.startvideo);
        videopayer.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
//                videopayer.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
