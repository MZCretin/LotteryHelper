package com.cretin.www.httpurlconnectionutil;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.cretin.www.httpurlconnectionutil.callback.HttpCallbackBytesListener;
import com.cretin.www.httpurlconnectionutil.callback.HttpCallbackModelListener;
import com.cretin.www.httpurlconnectionutil.callback.HttpCallbackStringListener;
import com.cretin.www.httpurlconnectionutil.response.ResponseCall;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  Created by cretin on 2017/3/22.
 *  HttpURLConnection 网络请求工具类
 *
 *  数据的请求都是基于HttpURLConnection的 请求成功与失败的回调都是在主线程
 *  可以直接更新UI
 */
public class HttpUtils {
    static ExecutorService threadPool = Executors.newCachedThreadPool();
    static Gson gson = new Gson();

    /**
     * GET方法 返回数据会解析成字符串String
     * @param context 上下文
     * @param urlString 请求的url
     * @param listener 回调监听
     */
    public static void doGet(final Context context, final String urlString,
                             final HttpCallbackStringListener listener) {
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    // 根据URL地址创建URL对象
                    url = new URL(urlString);
                    // 获取HttpURLConnection对象
                    httpURLConnection = ( HttpURLConnection ) url.openConnection();
                    // 设置请求方式，默认为GET
                    httpURLConnection.setRequestMethod("GET");
                    // 设置连接超时
                    httpURLConnection.setConnectTimeout(5000);
                    // 设置读取超时
                    httpURLConnection.setReadTimeout(8000);
                    // 响应码为200表示成功，否则失败。
                    if ( httpURLConnection.getResponseCode() == 200 ) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        //最好在将字节流转换为字符流的时候 进行转码
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ( (line = bf.readLine()) != null ) {
                            buffer.append(line);
                        }
                        bf.close();
                        is.close();
                        new ResponseCall(context, listener).doSuccess(buffer.toString());
                    } else {
                        new ResponseCall(context, listener).doFail(
                                new NetworkErrorException("response err code:" +
                                        httpURLConnection.getResponseCode()));
                    }
                } catch ( MalformedURLException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } catch ( IOException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } finally {
                    if ( httpURLConnection != null ) {
                        // 释放资源
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }

    /**
     * GET方法 返回数据会解析成byte[]数组
     * @param context 上下文
     * @param urlString 请求的url
     * @param listener 回调监听
     */
    public static void doGet(final Context context, final String urlString,
                             final HttpCallbackBytesListener listener) {
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    // 根据URL地址创建URL对象
                    url = new URL(urlString);
                    // 获取HttpURLConnection对象
                    httpURLConnection = ( HttpURLConnection ) url.openConnection();
                    // 设置请求方式，默认为GET
                    httpURLConnection.setRequestMethod("GET");
                    // 设置连接超时
                    httpURLConnection.setConnectTimeout(5000);
                    // 设置读取超时
                    httpURLConnection.setReadTimeout(8000);
                    // 响应码为200表示成功，否则失败。
                    if ( httpURLConnection.getResponseCode() != 200 ) {
                        new ResponseCall(context, listener).doFail(
                                new NetworkErrorException("response err code:" +
                                        httpURLConnection.getResponseCode()));
                    } else {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        // 读取输入流中的数据
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int len = -1;
                        while ( (len = bis.read(bytes)) != -1 ) {
                            baos.write(bytes, 0, len);
                        }
                        bis.close();
                        is.close();
                        // 响应的数据
                        new ResponseCall(context, listener).doSuccess(baos.toByteArray());
                    }
                } catch ( MalformedURLException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } catch ( IOException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } finally {
                    if ( httpURLConnection != null ) {
                        // 释放资源
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }

    /**
     * GET方法 返回数据会解析成cls对象
     * @param context 上下文
     * @param urlString 请求路径
     * @param listener 回调监听
     * @param cls 返回的对象
     * @param <T> 监听的泛型
     */
    public static <T> void doGet(final Context context,
                                 final String urlString,
                                 final HttpCallbackModelListener listener, final Class<T> cls) {
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    // 根据URL地址创建URL对象
                    url = new URL(urlString);
                    // 获取HttpURLConnection对象
                    httpURLConnection = ( HttpURLConnection ) url.openConnection();
                    // 设置请求方式，默认为GET
                    httpURLConnection.setRequestMethod("GET");
                    // 设置连接超时
                    httpURLConnection.setConnectTimeout(5000);
                    // 设置读取超时
                    httpURLConnection.setReadTimeout(8000);
                    // 响应码为200表示成功，否则失败。
                    if ( httpURLConnection.getResponseCode() == 200 ) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        //最好在将字节流转换为字符流的时候 进行转码
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ( (line = bf.readLine()) != null ) {
                            buffer.append(line);
                        }
                        bf.close();
                        is.close();
                        new ResponseCall(context, listener).doSuccess(gson.fromJson(buffer.toString(), cls));
                    } else {
                        if ( listener != null ) {
                            // 回调onError()方法
                            new ResponseCall(context, listener).doFail(
                                    new NetworkErrorException("response err code:" +
                                            httpURLConnection.getResponseCode()));
                        }
                    }
                } catch ( MalformedURLException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } catch ( IOException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } finally {
                    if ( httpURLConnection != null ) {
                        // 释放资源
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }


    /**
     * GET方法 返回数据会解析成字符串 String
     * @param context 上下文
     * @param urlString 请求的路径
     * @param listener  回调监听
     * @param params 参数列表
     */
    public static void doPost(final Context context,
                              final String urlString, final HttpCallbackStringListener listener,
                              final Map<String, Object> params) {
        final StringBuffer out = new StringBuffer();
        // 组织请求参数
        for (String key : params.keySet()) {
            if(out.length()!=0){
                out.append("&");
            }
            out.append(key).append("=").append(params.get(key));
        }
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(urlString);
                    httpURLConnection = ( HttpURLConnection ) url.openConnection();
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Content-Length", String
                            .valueOf(out.length()));
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);

                    // 设置运行输入
                    httpURLConnection.setDoInput(true);
                    // 设置运行输出
                    httpURLConnection.setDoOutput(true);

                    PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                    // 发送请求参数
                    printWriter.write(out.toString());
                    // flush输出流的缓冲
                    printWriter.flush();
                    printWriter.close();

                    if ( httpURLConnection.getResponseCode() == 200 ) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        //最好在将字节流转换为字符流的时候 进行转码
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ( (line = bf.readLine()) != null ) {
                            buffer.append(line);
                        }
                        bf.close();
                        is.close();
                        new ResponseCall(context, listener).doSuccess(buffer.toString());
                    } else {
                        new ResponseCall(context, listener).doFail(
                                new NetworkErrorException("response err code:" +
                                        httpURLConnection.getResponseCode()));
                    }
                } catch ( MalformedURLException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } catch ( IOException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } finally {
                    if ( httpURLConnection != null ) {
                        // 最后记得关闭连接
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }


    /**
     * GET方法 返回数据会解析成Byte[]数组
     * @param context 上下文
     * @param urlString 请求的路径
     * @param listener  回调监听
     * @param params 参数列表
     */
    public static void doPost(final Context context,
                              final String urlString, final HttpCallbackBytesListener listener,
                              final Map<String, Object> params) {
        final StringBuffer out = new StringBuffer();
        // 组织请求参数
        for (String key : params.keySet()) {
            if(out.length()!=0){
                out.append("&");
            }
            out.append(key).append("=").append(params.get(key));
        }
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(urlString);
                    httpURLConnection = ( HttpURLConnection ) url.openConnection();
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Content-Length", String
                            .valueOf(out.length()));
                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);

                    // 设置运行输入
                    httpURLConnection.setDoInput(true);
                    // 设置运行输出
                    httpURLConnection.setDoOutput(true);

                    PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                    // 发送请求参数
                    printWriter.write(out.toString());
                    // flush输出流的缓冲
                    printWriter.flush();
                    printWriter.close();

                    if ( httpURLConnection.getResponseCode() == 200 ) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        // 读取输入流中的数据
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int len = -1;
                        while ( (len = bis.read(bytes)) != -1 ) {
                            baos.write(bytes, 0, len);
                        }
                        bis.close();
                        is.close();
                        // 响应的数据
                        new ResponseCall(context, listener).doSuccess(baos.toByteArray());
                    } else {
                        new ResponseCall(context, listener).doFail(
                                new NetworkErrorException("response err code:" +
                                        httpURLConnection.getResponseCode()));
                    }
                } catch ( MalformedURLException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } catch ( IOException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } finally {
                    if ( httpURLConnection != null ) {
                        // 最后记得关闭连接
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }


    /**
     * /**
     * GET方法 返回数据会解析成cls对象
     * @param context 上下文
     * @param urlString 请求的路径
     * @param listener  回调监听
     * @param params 参数列表
     * @param cls 对象
     * @param <T> 监听泛型
     */
    public static <T> void doPost(final Context context,
                                  final String urlString, final HttpCallbackModelListener listener,
                                  final Map<String, Object> params, final Class<T> cls) {
        final StringBuffer paramsStr = new StringBuffer();
        // 组织请求参数
        Iterator it = params.entrySet().iterator();
        while ( it.hasNext() ) {
            Map.Entry element = ( Map.Entry ) it.next();
            paramsStr.append(element.getKey());
            paramsStr.append("=");
            paramsStr.append(element.getValue());
            paramsStr.append("&");
        }
        if ( paramsStr.length() > 0 ) {
            paramsStr.deleteCharAt(paramsStr.length() - 1);
        }
        // 因为网络请求是耗时操作，所以需要另外开启一个线程来执行该任务。
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(urlString);
                    httpURLConnection = ( HttpURLConnection ) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");

                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);

                    // 设置运行输入
                    httpURLConnection.setDoInput(true);
                    // 设置运行输出
                    httpURLConnection.setDoOutput(true);

                    PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                    // 发送请求参数
                    printWriter.write(paramsStr.toString());
                    // flush输出流的缓冲
                    printWriter.flush();
                    printWriter.close();

                    if ( httpURLConnection.getResponseCode() == 200 ) {
                        // 获取网络的输入流
                        InputStream is = httpURLConnection.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        //最好在将字节流转换为字符流的时候 进行转码
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ( (line = bf.readLine()) != null ) {
                            buffer.append(line);
                        }
                        bf.close();
                        is.close();
                        new ResponseCall(context, listener).doSuccess(gson.fromJson(buffer.toString(), cls));
                    } else {
                        new ResponseCall(context, listener).doFail(
                                new NetworkErrorException("response err code:" +
                                        httpURLConnection.getResponseCode()));
                    }
                } catch ( MalformedURLException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } catch ( IOException e ) {
                    if ( listener != null ) {
                        // 回调onError()方法
                        new ResponseCall(context, listener).doFail(e);
                    }
                } finally {
                    if ( httpURLConnection != null ) {
                        // 最后记得关闭连接
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }
}
