package com.huatai.czx.huataiyijia_master.model;

import com.google.gson.Gson;
import com.huatai.czx.huataiyijia_master.bean.TestBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by czx on 2018/2/12.
 */

public class MainActivityModel {
    public void getTestData(final MainActivityModelListener listener){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://apis.juhe.cn/cook/query?key=06d2fee782226f52923c0c223ae37b8a&menu=%E8%A5%BF%E7%BA%A2%E6%9F%BF&rn=10&pn=3").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.callBackFailed(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                TestBean bean = gson.fromJson(result,TestBean.class);
                listener.callBackSuccess(bean);
            }
        });

    }
}
