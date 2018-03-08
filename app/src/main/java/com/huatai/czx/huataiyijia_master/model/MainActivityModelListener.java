package com.huatai.czx.huataiyijia_master.model;

import com.huatai.czx.huataiyijia_master.bean.TestBean;

/**
 * Created by czx on 2018/2/12.
 */

public interface MainActivityModelListener {
    void callBackSuccess(TestBean bean);
    void callBackFailed(int code);
}
