1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    ```
2. 启动类添加注释@EnableFeignClients
    ```java
    @EnableFeignClients
    @SpringBootApplication
    public class OpenFeignApplication {
        public static void main(String[] args) {
            SpringApplication.run(OpenFeignApplication.class, args) ;
        }
    }
    ```
3. 编写feign接口
    ```java
    @FeignClient(name = "github-client", url = "https://api.github.com", configuration = HelloFeignServiceConfig.class)
    public interface HelloFeignService {
        @RequestMapping(value = "/search/repositories", method = RequestMethod.GET)
        String searchRepo(@RequestParam("q") String queryStr) ;
    }
    // 注意HelloFeignServiceConfig放在spring扫描包路径外，否则此配置将作为全局feign生效
    @Configuration
    public class HelloFeignServiceConfig {
       @Bean
       public Logger.Level feignLoggerLevel() {
          return Logger.Level.FULL;
       }
    }
    ```
4. 编写业务方法使用feign接口
    ```java
    @Controller
    public class HelloFeignController {
        @Autowired
        private HelloFeignService helloFeignService ;
        @GetMapping("/search/github")
        public String searchGithubRepoByStr(@RequestParam("str") String queryStr){
            return helloFeignService.searchRepo(queryStr);
        }
    }
    ```
