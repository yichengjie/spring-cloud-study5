1. 添加依赖
    ```xml
    <!-- 注意千万不要写成: spring-cloud-netflix-eureka-client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```
2. 添加注解@EnableDiscoveryClient
    ```java
    @EnableDiscoveryClient
    @SpringBootApplication
    public class ClientAApplication {
        public static void main(String[] args) {
            SpringApplication.run(ClientAApplication.class, args) ;
        }
    }
    ```
3. 添加配置
    ```properties
    ##端口号
    server.port=8081
    ##服务名称
    spring.application.name=client-a
    ##注册中心地址
    eureka.client.service-url.defaultZone=http://localhost:2001/eureka
    ``` 
