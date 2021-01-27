package com.yicj.study.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * pring Cloud Gateway做为网关服务，通过gateway进行请求转发，在请求到达后端服务前我们可以通过filter进行一些预处理如：请求的合法性，商户验证等。
 * 如我们在请求体中添加商户ID（merId）和商户KEY（merkey），通过此来验证请求的合法性。但是如果我们请求内容太长如转为base64的文件存储请求。
 * 此时我们在filter获取body内容就会被截取(太长的 Body 会被截断)。目前网上也没有好的解决方式。
 */
//https://blog.csdn.net/wangchengaihuiming/article/details/90447034
@SpringBootApplication
public class GatewayApp3 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp3.class, args) ;
    }
}
