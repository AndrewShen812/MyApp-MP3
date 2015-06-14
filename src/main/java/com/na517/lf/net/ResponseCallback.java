package com.na517.lf.net;

/**
 * 项目名称：LianfengApp
 * 类描述：ResponseCallback 网络操作回调接口
 * 创建人：lianfeng
 * 创建时间：2015/6/3 17:41
 * 修改人：lianfeng
 * 修改时间：2015/6/3 17:41
 * 修改备注：
 * 版本：V1.0
 */
public interface ResponseCallback {

    void onSuccess(String result);

    void onError(String result);

    void onStartLoading();
}
