package com.cretin.www.httpurlconnectionutil.callback;


/**
 * HttpURLConnection网络请求返回监听器
 */
public interface  HttpCallbackModelListener<T> {
    // 网络请求成功
    void onFinish(T response);

    // 网络请求失败
    void onError(Exception e);
}
