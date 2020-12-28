package com.yicj.study.zuul.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BodyReaderHttpServletRequestWrapper extends
        HttpServletRequestWrapper {

    private byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        System.out.println("-------------------打印请求的头信息------------------------------");
        Enumeration<?> e = request.getHeaderNames()   ;
        while(e.hasMoreElements()){
            String name = (String) e.nextElement();
            String value = request.getHeader(name);
            // System.out.println(name+" = "+value);
        }
        body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }

    /**
     * 从请求的头部获取用户的身份识别id;
     * @param request
     * @return
     */
    public String getJsessionidFromHeader(HttpServletRequest request) {
        String jsessionid = null;//识别用户身份的id;
        Enumeration<?> e = request.getHeaderNames()   ;
        while(e.hasMoreElements()){
            String name = (String) e.nextElement();
            String value = request.getHeader(name);
            //cookie = JSESSIONID=B926F6024438D4C693A5E5881595160C; SESSION=458e80dc-e354-4af3-a501-74504a873e70
            if("cookie".equals(name)) {
                jsessionid = value.split(";")[0].split("=")[1];
            }
            System.out.println(name+"="+value);
        }
        // System.out.println("======jsessionid========>"+jsessionid);
        return jsessionid;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //从缓存的数据中读取数据
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            public int read() throws IOException {
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return super.getHeaderNames();
    }

    /**
     * content-type=text/plain;charset=UTF-8
     * 重写getHeaders方法，实现自定义Content-Type;
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        if ((null != name && name.equalsIgnoreCase("Content-Type"))) {
            return new Enumeration<String>() {
                private boolean hasGetted = false;

                @Override
                public String nextElement() {
                    if (hasGetted) {
                        throw new NoSuchElementException();
                    } else {
                        hasGetted = true;
                        return "application/json;charset=utf-8";
                    }
                }

                @Override
                public boolean hasMoreElements() {
                    return !hasGetted;
                }
            };
        }
        return super.getHeaders(name);
    }

    /**
     * 添加自定义信息到请求体;
     * @param customMsg：自定义的添加到请求体中的信息;
     */
    public void appendCustomMsgToReqBody(String customMsg) {
        String oldBodyString = HttpHelper.getBodyString(this);//oldBodyString一定是通过当前对象的输入流解析得来的，否则接收时会报EOFException；
        String appendMsg = HttpHelper.appendCustomMsgToReqBody(customMsg);
        String requestBodyAfterAppend = appendMsg + "," +oldBodyString;
        //this.body = HttpHelper.appendCustomMsgToReqBody(HttpHelper.appendCustomMsgToReqBody(customMsg)+(HttpHelper.getBodyString(this))).getBytes(Charset.forName("UTF-8"));
        //this.body = HttpHelper.appendCustomMsgToReqBody((HttpHelper.getBodyString(this))).getBytes(Charset.forName("UTF-8"));
        this.body = HttpHelper.appendCustomMsgToReqBody(requestBodyAfterAppend).getBytes(Charset.forName("UTF-8"));
    }
}



