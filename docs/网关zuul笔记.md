1. 添加依赖
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
    ```
2. 添加注解@EnableZuulProxy
    ```java
    @EnableZuulProxy
    @EnableDiscoveryClient
    @SpringBootApplication
    public class SpringCloudZuulApplication {
        public static void main(String[] args) {
            SpringApplication.run(SpringCloudZuulApplication.class, args);
        }
    }
    ```
3. 添加配置
    ```properties
    ##端口号
    server.port=8080
    ##服务名称
    spring.application.name=service-zuul
    # 路由配置
    zuul.routes.service-a.path=/client-a/**
    zuul.routes.service-a.service-id=CLIENT-A
    ##zuul路由转发 service-b转发到service
    zuul.routes.service-b.path=/service-b/**
    zuul.routes.service-b.url=http://www.baidu.com
    ##注册中心地址
    eureka.client.service-url.defaultZone=http://127.0.0.1:8686/eureka
    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true
    ##zuul路由转发 service-a转发到service_ribbon
    ```
4. 自定义filter
    ```java
    @Slf4j
    @Component
    public class HelloFilter extends ZuulFilter {
        @Override
        public String filterType() {
            return "pre";
        }
        @Override
        public int filterOrder() {
            return 0;
        }
        @Override
        public boolean shouldFilter() {
            return true;
        }
        @Override
        public Object run() {
            RequestContext context = RequestContext.getCurrentContext() ;
            HttpServletRequest request = context.getRequest();
            HttpServletResponse response = context.getResponse();
            log.info("===> HelloFilter.run()");
            return null;
        }
    }
    ```