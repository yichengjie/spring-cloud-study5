1. registry类设计实现
    ```java
    @Getter
    public class GlobalServerInterceptorRegistry implements ApplicationContextAware {
        private final List<ServerInterceptor> serverInterceptors = Lists.newArrayList();
        private ApplicationContext applicationContext;
        @PostConstruct
        public void init() {
            // 获取所有的GlobalServerInterceptorConfigurerAdapter实现类
            Map<String, GlobalServerInterceptorConfigurerAdapter> map = applicationContext.getBeansOfType(GlobalServerInterceptorConfigurerAdapter.class);
            for (GlobalServerInterceptorConfigurerAdapter globalServerInterceptorConfigurerAdapter : map.values()) {
                // 调用adapter的addServerInterceptors, 将registry作为参数传入adapter，业务adapter调用registry的addServerInterceptors方法添加interceptor
                globalServerInterceptorConfigurerAdapter.addServerInterceptors(this);
            }
        }
        public GlobalServerInterceptorRegistry addServerInterceptors(ServerInterceptor interceptor) {
            serverInterceptors.add(interceptor);
            return this;
        }
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }
    ```
2. Adapter类设计
    ```java
    public abstract class GlobalServerInterceptorConfigurerAdapter {
        public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
    
        }
    }
    ```
3. 项目中业务adapter代码编写
    ```java
    @Configuration
    public class GlobalClientInterceptorConfiguration {
        @Bean
        public GlobalServerInterceptorConfigurerAdapter globalInterceptorConfigurerAdapter() {
            return new GlobalServerInterceptorConfigurerAdapter() {
                @Override
                public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
                    registry.addServerInterceptors(new LogGrpcInterceptor());
                }
            } ;
        }
    }
    public class LogGrpcInterceptor implements ServerInterceptor {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
            log.info("=======> method : {}", call.getMethodDescriptor().getFullMethodName());
            return next.startCall(call, headers);
        }
    }
    ```
    