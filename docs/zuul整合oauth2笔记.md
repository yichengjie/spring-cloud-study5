#### 认证服务器开发
1. 添加依赖
    ```xml
    <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
    <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```
2. 编写配信息
    ```properties
    server.port=7777
    spring.application.name=auth-server
    # web基路径
    server.servlet.context-path=/uua
    ##注册中心地址
    eureka.client.service-url.defaultZone=http://127.0.0.1:8686/eureka
    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true
    eureka.instance.prefer-ip-address=true
    ```
3. 启动类编写
   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class AuthServerApplication {
       public static void main(String[] args) {
           SpringApplication.run(AuthServerApplication.class, args) ;
       }
   }
   ```
3. Web安全配置编写
   ```java
   @Configuration
   @EnableWebSecurity
   public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {
       @Bean
       @Override
       public AuthenticationManager authenticationManagerBean() throws Exception {
           return super.authenticationManagerBean();
       }
       @Override
       protected void configure(AuthenticationManagerBuilder auth) throws Exception {
           auth.inMemoryAuthentication()
                   .withUser("guest").password("guest").authorities("ROLE_GUEST").and()
                   .withUser("admin").password("admin").authorities("ROLE_GUEST","ROLE_ADMIN") ;
       }
       @Bean
       public static PasswordEncoder passwordEncoder(){
           return NoOpPasswordEncoder.getInstance() ;
       }
   }
   ```
4. 认证服务器配置编写
   ```java
   @Configuration
   @EnableAuthorizationServer
   public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {
       @Autowired
       private AuthenticationManager authenticationManager ;
       @Override
       public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
           // 配置资源服务器用户名称密码
           clients.inMemory()
                   .withClient("zuul_server")
                   .secret("secret")
                   .scopes("read","write").autoApprove(true)
                   .resourceIds("RESOURCE_SERVER")
                   .authorizedGrantTypes("implicit","refresh_token","password","authorization_code") ;
       }
       @Override
       public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
           endpoints.tokenStore(jwtTokenStore())
               .tokenEnhancer(jwtAccessTokenConverter())
               .authenticationManager(authenticationManager) ;
       }
       @Bean
       public TokenStore jwtTokenStore(){
           return new JwtTokenStore(jwtAccessTokenConverter()) ;
       }
       @Bean
       public JwtAccessTokenConverter jwtAccessTokenConverter(){
           JwtAccessTokenConverter converter = new JwtAccessTokenConverter() ;
           converter.setSigningKey("springcloud123");
           return converter ;
       }
   }
   ```
5. 验证认证服务器,获取token
    ```properties
    5.1 请求地址http://localhost:7777/uua/oauth/token发送post请求
    5.2 header添加 Authorization  Basic 客户端用户名密码（zuul_server：secret）
    5.3 form表单添加登录用户信息username, password, grant_type,scope
    ```
#### 资源服务器开发
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
    ```
2. 配置编写
    ```properties
    server.port=8082
    spring.application.name=resource-server
    ##注册中心地址
    eureka.client.service-url.defaultZone=http://127.0.0.1:8686/eureka
    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true
    eureka.instance.prefer-ip-address=true
    ```
3. 启动类编写
    ```java
    @EnableDiscoveryClient
    @SpringBootApplication
    public class ResourceServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(ResourceServerApplication.class, args) ;
        }
    }
    ```
4. 资源服务器配置 
    ```java
    @Configuration
    @EnableResourceServer
    public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/test").hasRole("ADMIN")
                .antMatchers("/**").authenticated()
                .and().csrf().disable() ;
        }
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("RESOURCE_SERVER").tokenStore(jwtTokenStore()) ;
        }
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter()) ;
        }
        @Bean
        protected JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey("springcloud123");
            return converter ;
        }
    }
    ```
5. Rest服务编写
    ```java
    @Slf4j
    @RestController
    public class HomeController {
        @RequestMapping("/test")
        public String test(HttpServletRequest request){
            Enumeration<String> headerNames = request.getHeaderNames();
            log.info("---------------------header-----------------------");
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                log.info("====> {} : {}", headerName, request.getHeader(headerName));
            }
            log.info("---------------------header-----------------------");
            return "hello world !" ;
        }
        @GetMapping("/hello")
        public String hello(){
            return "hello world" ;
        }
    }
    ```
5. 验证资源服务器,获取资源
    ```txt
    5.1 访问 http://localhost:8082/test
    5.2 header添加Authorization bearer token-value
    ```
#### Zuul网关整合Oauth2
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
    ```
2. 填加oauth2相关配置
    ```properties
    # 令牌端点
    security.oauth2.client.access-token-uri=http://localhost:7777/uua/oauth/token 
    # 授权端点
    security.oauth2.client.user-authorization-uri=http://localhost:7777/uua/oauth/authorize
    # 客户端id
    security.oauth2.client.client-id=zuul_server
    security.oauth2.client.client-secret=secret
    # 使用对称加密方式，默认使用HS256
    security.oauth2.resource.jwt.key-value=springcloud123
    # ZuulProperties中sensitiveHeaders默认值"Cookie", "Set-Cookie", "Authorization"
    # 这里不手动设置的话zuul访问resource将不带Authorization
    zuul.sensitive-headers=Cookie,Set-Cookie
    # 资源服务器路由配置
    zuul.routes.resource-server.path=/resource/**
    zuul.routes.resource-server.service-id=resource-server
    ```
3. 添加@EnableOAuth2Sso注解，并编写部分Web安全设置
    ```java
    @Configuration
    @EnableOAuth2Sso
    public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/login","/resource/**").permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable() ;
        }
    }
    ```