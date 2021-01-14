#### 网关基础配置
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>
    ```
2. 添加注解@EnableZuulProxy
    ```java
    @EnableZuulProxy
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
    # 添加路由配置
    ##zuul路由转发 service-b转发到service
    zuul.routes.service-b.path=/service-b/**
    zuul.routes.service-b.url=http://www.baidu.com
   
    ```
#### 服务发现的路由规则、
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```
2. 添加注解@EnableDiscoveryClient
    ```txt
    启动类添加注解@EnableDiscoveryClient即可
    ```
3. 添加配置
    ```properties
    ##注册中心地址
    eureka.client.service-url.defaultZone=http://127.0.0.1:8686/eureka
    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true
    
    ##zuul路由转发 service-a转发到service_ribbon
    zuul.routes.service-a.path=/client-a/**
    zuul.routes.service-a.service-id=CLIENT-A
    ```
#### 自定义Filter
1. 编写filter业务实现，并添加@Component注解即可
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
#### 饥饿加载
1. 添加配置
    ```properties
    # 饥饿加载
    zuul.ribbon.eager-load.enabled=true
    ```
#### okhttp替换httpclient
1. 添加依赖
    ```xml
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
    </dependency>
    ```
2. 添加配置
    ```properties
    # okhttp 替换httpclient
    ribbon.http.client.enabled=false
    ribbon.okhttp.enabled=true
    ```
#### 其他
1. 执行流程
    ```txt
    //tips1: ServletWrappingController继承ServletWrappingController类
    1.1 SimpleControllerHandlerAdapter.handle() --> ZuulController.handleRequest() --> 
        ServletWrappingController.handleRequestInternal() --> ZuulServlet.service() --> preRoute -->route() --> postRoute()
    
    1.2 preRoute --> ZuulRunner.preRoute() --> FilterProcessor.preRoute() --> runFilters("pre") 
        5.2.1 FilterProcessor.runFilters --> FilterLoader.getInstance().getFiltersByType(sType) --> FilterProcessor.processZuulFilter
    
    1.3 postRoute --> SendResponseFilter
    FormBodyWrapperFilter 注意看一下
    ```