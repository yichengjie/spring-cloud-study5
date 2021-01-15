#### 入门示例
1. 添加依赖
    ```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    ```
2. 添加注解@EnableHystrix
    ```java
    @EnableHystrix
    @SpringBootApplication
    public class HystrixApplication  {
        public static void main(String[] args) {
            SpringApplication.run(HystrixApplication.class, args) ;
        }
    }
    ```
3. 编写业务并使用@HystrixCommand配置方法的降级处理
    ```java
    @Service
    public class UserServiceImpl implements UserService {
        @Override
        @HystrixCommand(fallbackMethod = "defaultUser")
        public String getUser(String username){
            if (username.equalsIgnoreCase("spring")){
                return "this is real user" ;
            }else {
                throw new RuntimeException() ;
            }
        }
        // 出错调用该方法返回预设友好错误
        public String defaultUser(String username){
            return "The user does not exist in this system" ;
        }
    }
    ```
4. 编写controller测试service代码
    ```java
    @RestController
    public class UserController {
        @Autowired
        private UserService userService ;
        @GetMapping("/getUser")
        public String getUser(String username){
            return userService.getUser(username) ;
        }
    }
    ```
#### feign中使用断路器
1. 添加依赖
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
    </dependencies>
    ```
2. 添加@EnableFeignClients,@EnableDiscoveryClient注解
    ```java
    @EnableHystrix
    @EnableFeignClients
    @SpringBootApplication
    public class HystrixApplication  {
        public static void main(String[] args) {
            SpringApplication.run(HystrixApplication.class, args) ;
        }
    }
    ```
3. 打开feign断路配置
    ```properties
    # feign中hystrix 默认关闭
    feign.hystrix.enabled=true
    ```
4. 编写feign接口类
    ```java
    @Primary
    @FeignClient(name = "client-a", fallback = UserFeignServiceFallback.class)
    public interface UserFeignService {
        @RequestMapping(value = "/getUser", method = RequestMethod.GET)
        String getUser(@RequestParam("username") String username) ;
    }
    ```
5. 编写feign接口降级类
    ```java
    @Component
    public class UserFeignServiceFallback implements UserFeignService {
        @Override
        public String getUser(String username) {
            return "The user does not exist in this system, please confirm username";
        }
    }
    ```
6. 编写业务方法调用feign接口
    ```java
    @RestController
    public class UserFeignController {
        @Autowired
        private UserFeignService userFeignService ;
        @GetMapping("/getFeignUser")
        public String getUser(String username){
            return userFeignService.getUser(username) ;
        }
    }
    ```


