1. gateway与zuul性能对比: https://www.imooc.com/article/285068
2. 网关相关源码：{
    Gateway Handler Mapping 对应类为RoutePredicateHandlerMapping,
    Gateway Web Handler 对应类为FilteringWebHandler
} 
3. gateway路由谓词工厂：https://www.imooc.com/article/290804
4. 内置过滤器工厂：https://www.imooc.com/article/290816
5. 注意debug类NettyRoutingFilter查看filter细节
6. 自定义过滤器工厂方式1
    ```text
    6.1 继承AbstractGatewayFilterFactory。
    6.2 参考示例：RequestSizeGatewayFilterFactory
    6.3 配置方式: ...filters[n].name.args.maxSize= 50000
    ```
7. 自定义过滤器工厂方式2
    ```text
    7.1 继承AbstractNameValueGatewayFilterFactory
    7.2 参考示例：AddRequestHeaderGatewayFilterFactory
    7.3 配置形式：filters[n]=AddRequestHeader=S-Header,Bar
    ```
8. 内置全局过滤器: https://www.imooc.com/article/290821(重点)
9. 监控端点：https://www.imooc.com/article/290822
10. 排查总结：https://www.imooc.com/article/290824
11. 限流：https://www.imooc.com/article/290828