package com.huatai.czx.huataiyijia_master.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatai.czx.huataiyijia_master.MainActivity;
import com.huatai.czx.huataiyijia_master.R;
import com.huatai.czx.huataiyijia_master.utils.CircleView;
import com.huatai.czx.huataiyijia_master.utils.DownLoadUtils;
import com.huatai.czx.huataiyijia_master.utils.WebViewJavascriptBridge;
import com.huatai.czx.huataiyijia_master.utils.ZipUtil;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.io.File;
import java.io.IOException;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView htjs1;
    private ImageView htjs2;
    private ImageView htjs3;
    private Button add;
    private Button delete;
    private Button update;
    private Button select;
    private WebViewJavascriptBridge bridge;
    private TextView neirong;
    private Button photoAlbum;
    private Button camera;
    private Button photoBook;
    private Button share;
    public static final int REQUEST_TAKE_PHOTO_CODE=1;
    // /storage/emulated/0/Pictures
    public final static String SAVED_IMAGE_PATH=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();//.getAbsolutePath()+"/pic";//+"/pic";

    String photoPath;
    private ImageView img;
    //调用系统相册-选择图片
    private static final int IMAGE = 2;
    private CircleView circleView;
    String url = "http://121.41.101.209//dandiansit/SubSystem/ZY_W_F_0_53_eanalysis_sit_0_53.zip";
    private SpeechSynthesizer mySynthesizer;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1 == 1){
                System.out.println("下载成功");
                String filename = Environment.getExternalStorageDirectory()+"/download/ZY_W_F_0_53_eanalysis_sit_0_53.zip";
                System.out.println("-----"+filename);
                File file = new File(filename);
                System.out.println("======"+file.getAbsolutePath());
                String dirName = Environment.getExternalStorageDirectory() + "/huatai_zip/";
                File f = new File(dirName);
                //不存在创建
                if (!f.exists()) {
                    f.mkdir();
                }
                ZipUtil.getInstance().unZip(f.getAbsolutePath(),file.getAbsolutePath());

            }
        }
    };
    private Button tiao;
    private Button speak;
    private EditText speaktext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_second);
        bridge = new WebViewJavascriptBridge(this);
        //语音初始化，在使用应用使用时需要初始化一次就好，如果没有这句会出现10111初始化失败
        SpeechUtility.createUtility(SecondActivity.this, "appid=5a960e78");
        //处理语音合成关键类
        mySynthesizer = SpeechSynthesizer.createSynthesizer(this, myInitListener);
        initView();
    }

    private void initView() {
        //工作台1
        htjs1 = (ImageView) findViewById(R.id.htjs1);
        //工作台2
        htjs2 = (ImageView) findViewById(R.id.htjs2);
        //工作台3
        htjs3 = (ImageView) findViewById(R.id.htjs3);
        //增加
        add = (Button) findViewById(R.id.add);
        //删除
        delete = (Button) findViewById(R.id.delete);
        //修改
        update = (Button) findViewById(R.id.update);
        //查询
        select = (Button) findViewById(R.id.select);
        //显示
        neirong = (TextView) findViewById(R.id.neirong);
        //相册
        photoAlbum = (Button) findViewById(R.id.photoAlbum);
        //相机
        camera = (Button) findViewById(R.id.camera);
        //通讯录
        photoBook = (Button) findViewById(R.id.photoBook);
        //分享
        share = (Button) findViewById(R.id.share);
        //显示图片
        img = (ImageView) findViewById(R.id.neirongimage);
        circleView = (CircleView) findViewById(R.id.circleview1);
        tiao = (Button) findViewById(R.id.tiao);
        speak = (Button) findViewById(R.id.speak);
        speaktext = (EditText) findViewById(R.id.speaktext);
        speak.setOnClickListener(this);
        tiao.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        select.setOnClickListener(this);

        htjs1.setOnClickListener(this);
        htjs2.setOnClickListener(this);
        htjs3.setOnClickListener(this);

        photoAlbum.setOnClickListener(this);
        photoBook.setOnClickListener(this);
        camera.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                bridge.addRealmDataMessage();
                break;
            case R.id.delete:
                bridge.removeRealmDataMessage();
                break;
            case R.id.update:
                bridge.alterRealmDataMessageCondition();
                break;
            case R.id.select:
                String data = bridge.relationSearchMsgAscendAscendKeyBlockFN();
                neirong.setText(data);
                break;
            case R.id.htjs1:
                Toast.makeText(this, "进入子系统1", Toast.LENGTH_SHORT).show();
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
                //进度条从0到100
                circleView.setVisibility(View.VISIBLE);
                ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
                animator.setDuration(4000);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float current = (float) animation.getAnimatedValue();
                        circleView.setmCurrent((int) current);
                        if((int)current == 100){
                            Toast.makeText(SecondActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                            circleView.setVisibility(View.GONE);
                        }
                    }
                });
                animator.start();
                break;
            case R.id.htjs2:
                Toast.makeText(this, "进入子系统2", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(SecondActivity.this,jsActivity.class);
                startActivity(intent2);
                break;
            case R.id.htjs3:
                Toast.makeText(this, "进入子系统3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.camera:
                //获取SD卡安装状态
                String state= Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)){

                    //设置图片保存路径
                    photoPath=SAVED_IMAGE_PATH+"/"+System.currentTimeMillis()+".png";

                    File imageDir=new File(photoPath);
                    if(!imageDir.exists()){
                        try {
                            //根据一个 文件地址生成一个新的文件用来存照片
                            imageDir.createNewFile();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    takePhotoByMethod1();
                    //takePhotoByMethod2();
                }else {
                    Toast.makeText(SecondActivity.this,"SD卡未插入",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.photoAlbum:
//                Toast.makeText(this, "敬请期待。。。", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, IMAGE);
                break;
            case R.id.photoBook://通讯录
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK,uri);
                startActivityForResult(intent,0);
                break;
            case R.id.share:
                Toast.makeText(this, "敬请期待。。。", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tiao:
                Intent intent3 = new Intent(SecondActivity.this,jsActivity.class);
                startActivity(intent3);
                break;
            case R.id.speak:
                //设置发音人
                mySynthesizer.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
                //设置音调
                mySynthesizer.setParameter(SpeechConstant.PITCH,"50");
                //设置音量
                mySynthesizer.setParameter(SpeechConstant.VOLUME,"50");
                int code = mySynthesizer.startSpeaking(speaktext.getText().toString(), mTtsListener);
                Log.i("mySynthesiezer start code:", code+"");
                break;
        }
    }
    //回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_TAKE_PHOTO_CODE&&resultCode==Activity.RESULT_OK){
            File photoFile=new File(photoPath);
            if (photoFile.exists()){
                //通过图片地址将图片加载到bitmap里面
                Bitmap bm= BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                //将拍摄的照片显示到界面上
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(bm);
            }else {
                Toast.makeText(SecondActivity.this,"图片文件不存在",Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }else if (resultCode == Activity.RESULT_OK) {//调取通讯录选定联系人回调
            if (requestCode == 0) {
                Uri uri=data.getData();
                String[] contacts=getPhoneContacts(uri);
                Log.d("============", "姓名:"+contacts[0]+" "+"手机号:"+contacts[1]);
                neirong.setText("姓名:"+contacts[0]+" "+"手机号:"+contacts[1]);
            }
        }
    }
    //回调通讯录联系人的信息
    private String[] getPhoneContacts(Uri uri){
        String[] contact=new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0]=cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if(phone != null){
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        }
        else{
            return null;
        }
        return contact;
    }
    /*
    * 调用系统摄像头拍照
    * */
    private void takePhotoByMethod1(){
        //实例化intent,指向摄像头
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //根据路径实例化图片文件
        File photoFile=new File(photoPath);
        //设置拍照后图片保存到文件中
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
        //启动拍照activity并获取返回数据
        startActivityForResult(intent,REQUEST_TAKE_PHOTO_CODE);
    }

    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        img.setImageBitmap(bm);
    }

    //语音*******************************************************************8
    private InitListener myInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("mySynthesiezer:", "InitListener init() code = " + code);
        }
    };

    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
        }
        @Override
        public void onSpeakPaused() {
        }
        @Override
        public void onSpeakResumed() {
        }
        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
        }
        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if(error!=null)
            {
                Log.d("mySynthesiezer complete code:", error.getErrorCode()+"");
            }
            else
            {
                Log.d("mySynthesiezer complete code:", "0");
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
}
