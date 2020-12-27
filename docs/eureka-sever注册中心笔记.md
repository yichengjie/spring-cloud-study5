1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    ```
2. 添加注解@EnableEurekaServer
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
    ```properties
    server.port=2001
    spring.application.name=eureka-ceneter
    logging.level.root=info
    eureka.instance.hostname=eureka-center
    # 每个服务都有一个地址，用于集群之间的通信，默认为 localhost:8761/eureka
    eureka.client.service-url.defaultZone=http://127.0.0.1:2001/eureka
    # 是否将自己注册到eureka服务中心，
    eureka.client.register-with-eureka=false
    # 是否从eureka中获取注册信息，因为自己是注册中心，不会从该应用中检索注册信息
    eureka.client.fetch-registry=false
    ```
4. 服务启动后访问http://127.0.0.1:2001即可

