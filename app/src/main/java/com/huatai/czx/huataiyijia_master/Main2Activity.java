package com.huatai.czx.huataiyijia_master;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.huatai.czx.huataiyijia_master.utils.CircleView;
import com.huatai.czx.huataiyijia_master.utils.DownLoadUtils;
import com.huatai.czx.huataiyijia_master.utils.ZipUtil;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

    private CircleView circleView;
    private ValueAnimator animator;
    private Button inter;
    String url = "http://121.41.101.209//dandiansit/SubSystem/ZY_W_F_0_53_eanalysis_sit_0_53.zip";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1 == 1){
                Toast.makeText(Main2Activity.this, "加载完成----", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Main2Activity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String filename = Environment.getExternalStorageDirectory()+"/download/ZY_W_F_0_53_eanalysis_sit_0_53.zip";

        File file = new File(filename);
        System.out.println("======"+file.getAbsolutePath());
        String dirName = Environment.getExternalStorageDirectory() + "/huatai_zip/";
        File f = new File(dirName);
        //不存在创建
        if (!f.exists()) {
            f.mkdir();
        }
        ZipUtil.getInstance().unZip(f.getAbsolutePath(),file.getAbsolutePath());
        initView();
        //进度条从0到100
        animator = ValueAnimator.ofFloat(0, 100);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                circleView.setmCurrent((int) current);
            }
        });

        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        animator.start();
            }
        });
        circleView.setOnLoadingCompleteListener(new CircleView.OnLoadingCompleteListener() {
            @Override
            public void complete() {
                Toast.makeText(Main2Activity.this, "加载完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        circleView = (CircleView) findViewById(R.id.circle_view);
        inter = (Button) findViewById(R.id.inter);
        inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DownLoadUtils.get().download(url, "download", new DownLoadUtils.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {
                                Message message = new Message();
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onDownloading(int progress) {
                                System.out.println("-----------------"+progress);
                        }

                            @Override
                            public void onDownloadFailed() {
                                Message message = new Message();
                                message.arg1 = 2;
                                handler.sendMessage(message);
                            }
                        });
                    }
                }).start();

            }
        });
    }
}
