package com.huatai.czx.huataiyijia_master.utils;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by czx on 2018/2/23.
 */

public class DownLoadUtils {
    private static DownLoadUtils downloadUtil;
    private final OkHttpClient okHttpClient;
    public static DownLoadUtils get() {
        if (downloadUtil == null) {
            downloadUtil = new DownLoadUtils();
        }
        return downloadUtil;
    }
    private DownLoadUtils() {
        okHttpClient = new OkHttpClient();
    }
    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener){
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath,getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf))!= -1){
                        fos.write(buf,0,len);
                        sum +=len;
                        int progress = (int)(sum*1.0f/total*100);
                        //下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    //下载完成
                    listener.onDownloadSuccess();
                }catch (Exception e){
                    listener.onDownloadFailed();
                }finally {
                    try {
                        if(is != null){
                            is.close();
                        }
                    }catch (IOException e){
                        System.out.println("错误");
                    }
                    try {
                        if(fos != null){
                            fos.close();
                        }
                    }catch (IOException e){
                        System.out.println("错误");
                    }
                }
            }
        });
    }
    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        File downloadFile = new File(Environment.getExternalStorageDirectory(),saveDir);
        if(!downloadFile.mkdirs()){
            downloadFile.createNewFile();
        }
        String savepath = downloadFile.getAbsolutePath();
        return savepath;
    }
    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
    public interface OnDownloadListener{
        /**
         * 下载成功
         */
        void onDownloadSuccess();
        /**
         * 下载进度
         */
        void onDownloading(int progress);
         /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}
