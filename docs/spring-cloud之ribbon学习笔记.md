#### ribbon基础功能使用
1. 添加依赖
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       </dependency>
   </dependencies>
   ```
2. 启动类添加@EnableDiscoveryClient注解
    ```java
    @EnableDiscoveryClient
    @SpringBootApplication
    public class RibbonLoadbalancerApp {
        public static void main(String[] args) {
            SpringApplication.run(RibbonLoadbalancerApp.class, args) ;
        }
    }
    ```
3. RestTemplate添加@LoadBalanced注解
    ```java
    @Configuration
    public class MyConfig {
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate(){
            return new RestTemplate() ;
        }
    }
    ```
4. 业务调用代码编写
    ```java
    @RestController
    public class HelloController {
        @Autowired
        private RestTemplate restTemplate ;
     
        @GetMapping("/hello")
        public String hello(@RequestParam("name") String name){
            String url = "http://CLIENT-A/hello2?name=" + name ;
            return restTemplate.getForObject(url, String.class) ;
        }
    }
    ```
5. ribbon懒加载
    ```properties
    # ribbon懒加载
    ribbon.eager-load.enabled=true
    ribbon.eager-load.clients=client-a, client-b
    ```

#### 全局负载均衡策略配置
1. 直接将负载均衡规则注入到spring容器
    ````java
    @Configuration
    public class RibbonRuleConfig {
        // 全局配置负载均衡策略
        @Bean
        public IRule ribbonRule(){
            return new RandomRule() ;
        }
    }
    ````
#### 单个服务的负载均衡策略配置（注解版）
1. 编写配置类，并避免spring容器自动扫描
    ```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface AvoidScan {
    }
 
    @Configuration
    public class RibbonRuleConfig {
        // 全局配置负载均衡策略
        @Bean
        public IRule ribbonRule(){
            return new RandomRule() ;
        }
    }
    ```
2. 启动类添加@RibbonClients配置，以及避免扫描配置
    ```text
    // 不自动扫描AvoidScan注解的类
    @RibbonClients({
        @RibbonClient(name = "client-a", configuration = RibbonRuleConfig.class)
    })
    @ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AvoidScan.class})})
    ```
#### 单个服务的负载均衡策略配置（properties配置）
1. 添加application.properties配置
    ```properties
    # 单个服务的负载均衡策略配置
    # <client-a>.ribbon.*
    client-a.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
    ```
#### 杂项
1. HandlerInterceptor是最常规的，其拦截的http请求是来自于客户端浏览器之类的，是最常见的http请求拦截器；
2. ClientHttpRequestInterceptor是对RestTemplate的请求进行拦截的，在项目中直接使用restTemplate.getForObject的时候，会对这种请求进行拦截，经常被称为：RestTempalte拦截器或者Ribbon拦截器；
3. RequestInterceptor常被称为是Feign拦截器，由于Feign调用底层实际上还是http调用，因此也是一个http拦截器，在项目中使用Feign调用的时候，可以使用此拦截器；


   