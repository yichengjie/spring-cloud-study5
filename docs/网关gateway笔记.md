#### 网关基础配置
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```
2. 添加配置
    ```properties
    server.port=8080
    spring.application.name=gateway-server
    # 配置路由规则
    # 路由规则1
    spring.cloud.gateway.routes[0].id=baidu
    spring.cloud.gateway.routes[0].uri=https://www.baidu.com/
    spring.cloud.gateway.routes[0].predicates[0]=Path=/baidu/**
    spring.cloud.gateway.routes[0].filters[0]=StripPrefix=1
    # 路由规则2
    spring.cloud.gateway.routes[1].id=jd
    spring.cloud.gateway.routes[1].uri=https://www.jd.com/
    spring.cloud.gateway.routes[1].predicates[0]=Path=/jd/**
    spring.cloud.gateway.routes[1].filters[0]=StripPrefix=1
    # 配置日志
    logging.level.org.springframework.cloud.gateway=trace
    logging.level.org.springframework.http.server.reactive=debug
    logging.level.org.springframework.web.reactive=debug
    logging.level.reactor.ipc.netty=debug
    # 配置actuator监控
    # 注意在properties中这里不能使用'*'，否则无法访问，yml中使用'*'
    management.endpoints.web.exposure.include=*
    management.endpoint.health.show-details=always
    ```
3. 启动服务查看路由规则
```txt
    http://localhost:8080/actuator/gateway/routes
```
#### 服务发现的路由规则
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```
2. 添加配置
    ```properties
    # 服务发现配置
    # 是否与服务发现组件结合，通过serviceId转发到具体的服务实例
    spring.cloud.gateway.discovery.locator.enabled=true
    # 服务名通过小写访问（默认大写）
    spring.cloud.gateway.discovery.locator.lower-case-service-id=true
    # 注册中心地址
    eureka.client.service-url.defaultZone=http://localhost:8686/eureka
    # 路由规则3
    spring.cloud.gateway.routes[2].id=client-a
    spring.cloud.gateway.routes[2].uri=lb://client-a
    spring.cloud.gateway.routes[2].predicates[0]=Path=/client-a/**
    spring.cloud.gateway.routes[2].filters[0]=RewritePath=/client-a/(?<segment>.*),/$\\{segment}
    ```
#### 自定义Gateway Filter
1. 编写自定义filter业务实现
    ```java
    @Slf4j
    public class CustomGatewayFilter implements GatewayFilter {
        private static final String COUNT_START_TIME = "COUNT_START_TIME" ;
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis()) ;
            return chain.filter(exchange).then(
               Mono.fromRunnable(()->{
                   Long startTime = exchange.getAttribute(COUNT_START_TIME);
                   long endTime = (System.currentTimeMillis() - startTime) ;
                   log.info("=====> " + exchange.getRequest().getURI().getRawPath() +": " + endTime +"ms");
               })
            );
        }
    }
    ```
2. 将自定义filter加入到路由规则中 （Gateway不能直接URL重写）
    ```java
    @Configuration
    public class MyConfig {
        @Bean
        public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
            return builder.routes()
                .route(r -> r.path("/customer/**")
                        .filters(f -> f.stripPrefix(1).filter(new CustomGatewayFilter()))
                       // 注意这里写http://localhost:8081与写http://localhost:8081/hello效果一样
                        .uri("http://localhost:8081/hello")
                        .order(0)
                        .id("customer_filter_router")
                )
                .build();
        }
    }
    ```
 #### 自定义Global Filter
 1. 编写自定义Filter业务实现
     ```java
    @Component
    public class AuthSignatureFilter implements GlobalFilter, Ordered {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String token = exchange.getRequest().getQueryParams().getFirst("authToken") ;
            if (token == null || token.isEmpty()){
                // 当请求不携带token或则token为空时，直接设置请求状态码为401，返回
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED) ;
                return exchange.getResponse().setComplete() ;
            }
            return chain.filter(exchange);
        }
    
        @Override
        public int getOrder() {
            return -400;
        }
    }
    ```
2. Gateway Filter与Global Filter的区别
    > 从作用范围来看，Global Filter会被应用到所有的路由上，而Gateway Filter仅应用到单个路由或则一个分组路由上
#### 权重路由

