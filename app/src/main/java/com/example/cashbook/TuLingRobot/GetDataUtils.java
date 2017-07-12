package com.example.cashbook.TuLingRobot;

import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

/**
 *
 * 获取信息帮助类 传入用户输入的字符，给出相对应的信息
 *
 */
public class GetDataUtils {

    private static final String API_KEY = "10297684d1874ef1adbee766c0b15d2d";// 申请的API_KEY值
    private static final String URL = "http://www.tuling123.com/openapi/api";// 接口请求地址

    public String getChat(String msg) {//这个方法是获取服务端返回回来的Json数据，msg为用户输入的信息
        String result = "";// 存放服务器返回信息的变量
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            // 进行资源请求
            java.net.URL url = new java.net.URL(getMsgUrl(msg));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();// 打开资源连接

            // HttpURLConnection参数设定
            httpURLConnection.setReadTimeout(5 * 1000);
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setRequestMethod("GET");

            inputStream = httpURLConnection.getInputStream();// 获取一个输入流接收服务端返回的信息
            int len = -1;
            byte[] bs = new byte[124];// 用来接收输入流的字节数组
            outputStream = new ByteArrayOutputStream();// 用一个输出流来输出刚获取的输入流所得到的信息

            while ((len = inputStream.read(bs)) != -1) {// 从输入流中读取一定数量的字节，并将其存储在缓冲区数组
                // bs 中
                outputStream.write(bs, 0, len);// 往输入流写入
            }
            outputStream.flush();// 清除缓冲区
            result = new String(outputStream.toByteArray());// 转换成字符串
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭相关资源
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("tuzi", "result:" + result);//打印测试日志
        return result;
    }

    private String getMsgUrl(String msg) throws UnsupportedEncodingException {
        String path = "";
        String info = URLEncoder.encode(msg, "UTF-8");// 转换url编码
        path = URL + "?key=" + API_KEY + "&info=" + msg;
        return path;
    }

    public Message getInfo(String msg){
        Message message=new Message();
        Gson gson=new Gson();
        try {
            Result result=gson.fromJson(getChat(msg), Result.class);//获取到服务器返回的json并转换为Result对象，Result对象可能不存在，会出现异常
            message.setMsg(result.getText());//message可能为空，需要捕获异常
        } catch (Exception e) {
            //可能服务器没有返回正常数据，也就存在着空白内容，需要捕获异常
            message.setMsg("服务器繁忙，请稍后再试");
        }
        message.setTime(new Date());
        message.setType(Message.Type.INCOME);
        return message;
    }

}

