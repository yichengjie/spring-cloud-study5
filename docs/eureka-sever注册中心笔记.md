1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    ```
2. 编写启动类
    ```java
    @SpringBootApplication
    @EnableEurekaServer
    public class EurekaServerApplication {
        public static void main(String[] args) {
            SpringApplication.run(EurekaServerApplication.class, args);
        }
    }
    ```
3. 编写配置文件
    ```yml
    server:
      port: 2001
    eureka:
      instance:
        hostname: eureka-center
      client:
        service-url:
          defaultZone: http://127.0.0.1:2001/eureka
        register-with-eureka: false # 是否将自己注册到eureka服务中心，
        fetch-registry: false # 是否从eureka中获取注册信息，因为自己是注册中心，不会从该应用中检索注册信息
    spring:
      application:
        name: eureka-ceneter
    ```
4. 服务启动后访问http://127.0.0.1:2001即可

