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
#### GET 传递对象参数问题（post添加@RequestBody直接传递对象即可）\
1. 方式一: feign接口中接收Map<String,Object> 参数，我们在controller中手动转map
    ```text
    @RequestMapping(value = "/feignGet/addUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> addUser(@RequestParam Map<String, Object> user) ;
    ```
2. 方式二： 添加@SpringQueryMap注解 （open feign 2.1之后版本可用）
    ```text
    @RequestMapping(value = "/feignGet/addUser",
                method = RequestMethod.GET,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE
        )
        ResponseEntity<byte[]> addUser3(@SpringQueryMap User user) ;
    ```
3. 方式三： 自定义RequestInterceptor （open feign支持支持对象传递的，但是得是Map形式，而且不能为空）
    ```java
    @Component
    public class FeignGetInterceptor implements RequestInterceptor {
        @Autowired
        private ObjectMapper objectMapper ;
        @Override
        public void apply(RequestTemplate template) {
            if ("GET".equalsIgnoreCase(template.method()) && template.requestBody() != null){
                Request.Body body = template.requestBody();
                if (body.length() ==0){
                    return;
                }
                try {
                    JsonNode jsonNode = objectMapper.readTree(body.asBytes());
                    // 这里必须设置为空，否则报错
                    template.body(Request.Body.empty()) ;
                    Map<String, Collection<String>> queries = buildQuery(jsonNode);
                    template.queries(queries) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 暂时仅支持平铺得对象属性传递，不支持对象嵌套对象以及数组传递
        private Map<String, Collection<String>> buildQuery(JsonNode jsonNode) {
            Map<String, Collection<String>> queries = new HashMap<>() ;
            Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
            while (iterator.hasNext()){
                Map.Entry<String, JsonNode> next = iterator.next();
                String name = next.getKey();
                JsonNode value = next.getValue();
                queries.put(name, Collections.singletonList(value.asText())) ;
            }
            return queries ;
        }
    }
    ```
#### 调用传递token
1. 自定义RequestInterceptor
    ```java
    @Component
    public class FeignTokenInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            HttpServletRequest request = ((ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes()).getRequest();
            if (request == null){
                return;
            }
            // 将token对应值往下传递
            String xtoken = request.getHeader("x-token");
            if (xtoken != null){
                template.header("x-token", xtoken) ;
            }
        }
    }
    ```
