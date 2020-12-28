package com.yicj.study.zuul.wrapper;

import java.io.*;
import java.nio.charset.Charset;

import javax.servlet.ServletRequest;

public class HttpHelper {
    /**
     * 获取post请求中的Body
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            //读取流并将流写出去,避免数据流中断;
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line ;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inputStream, reader);
        }
        return sb.toString();
    }

    //添加自定义的信息到请求体中;
    public static String appendCustomMsgToReqBody(String newReqBodyStr) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        String newReqBody = null;
        try {
            //通过字符串构造输入流;
            inputStream = String2InputStream(newReqBodyStr);
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inputStream, reader);
        }
        //返回字符串;
        newReqBody = sb.toString();
        return newReqBody;

    }

    //将字符串转化为输入流;
    public static InputStream String2InputStream(String str) {
        ByteArrayInputStream stream = null;
        try {
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stream;
    }

    private static void close(Closeable ...closeables){
        if (closeables != null){
            for (Closeable closeable : closeables){
                try {
                    closeable.close();
                }catch (IOException e){
                    // ignore
                }
            }
        }
    }
}