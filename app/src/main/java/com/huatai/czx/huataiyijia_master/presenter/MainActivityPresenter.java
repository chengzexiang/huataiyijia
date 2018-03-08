package com.huatai.czx.huataiyijia_master.presenter;

import com.huatai.czx.huataiyijia_master.bean.TestBean;
import com.huatai.czx.huataiyijia_master.model.MainActivityModel;
import com.huatai.czx.huataiyijia_master.model.MainActivityModelListener;
import com.huatai.czx.huataiyijia_master.view.MainActivityListener;

/**
 * Created by czx on 2018/2/12.
 */

public class MainActivityPresenter {
    private MainActivityListener listener;
    private MainActivityModel model;

    public MainActivityPresenter(MainActivityListener listener) {
        this.listener = listener;
        this.model = new MainActivityModel();
    }
    public void getTestData(){
        model.getTestData(new MainActivityModelListener() {
            @Override
            public void callBackSuccess(TestBean bean) {
                listener.callBackSuccess(bean);
            }

            @Override
            public void callBackFailed(int code) {
                listener.callBackFailed(code);
            }
        });
    }

}
