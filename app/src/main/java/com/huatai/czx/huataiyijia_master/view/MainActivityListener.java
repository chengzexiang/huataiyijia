package com.huatai.czx.huataiyijia_master.view;

import com.huatai.czx.huataiyijia_master.bean.TestBean;

/**
 * Created by czx on 2018/2/12.
 */

public interface MainActivityListener {
    void callBackSuccess(TestBean bean);
    void callBackFailed(int code);
}
