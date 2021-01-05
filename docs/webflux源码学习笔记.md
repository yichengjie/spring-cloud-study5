#### 系统初始化
1. DispatcherHandler.setApplicationContext => initStrategies(){
    1.1 获取系统中的HandlerMapping {
        RequestMappingHandlerMapping,
        RouterFunctionMapping,
        SimpleUrlHandlerMapping
    }
    1.2 获取系统中的HandlerAdapter{
        RequestMappingHandlerAdapter,
        HandlerFunctionAdapter,
        SimpleHandlerAdapter
    }
    1.3 获取系统中的HandlerResultHandler{
        ResponseEntityResultHandler，
        ResponseBodyResultHandler,
        ViewResolutionResultHandler,
        ServerResponseResultHandler
    }
}
2. RouterFunctionMapping.afterPropertiesSet => initRouterFunctions(){
   2.1 获取系统中所有的RouterFunction{
       SortedRouterFunctionsContainer container = new SortedRouterFunctionsContainer();
       obtainApplicationContext().getAutowireCapableBeanFactory().autowireBean(container);
       return CollectionUtils.isEmpty(container.routerFunctions) ? Collections.emptyList() :
       				container.routerFunctions;
       				
       // 自定义的routeFunction为SameComposedRouterFunction
   }   
   2.2 其中obtainApplicationContext().getAutowireCapableBeanFactory().autowireBean(container);
       触发自定RouterConfig初始化，并组装为SameComposedRouterFunction实例
   2.3 将RouterFunction连接成一个SameComposedRouterFunction链（一个接一个）
}
#### 接收请求并将请求转发到DispatcherHandler
1. ChannelOperations.applyHandler 
   => ReactorHttpHandlerAdapter.apply(request,response){
      ServerHttpRequest  adaptedRequest = new ReactorServerHttpRequest(request, bufferFactory);
   	  ServerHttpResponse adaptedResponse = new ReactorServerHttpResponse(response, bufferFactory);
   }
   
   => HttpWebHandlerAdapter.handle(adaptedRequest, adaptedResponse){
       ServerWebExchange exchange = createExchange(request, response);
   }
   
   => DispatcherHandler.handle.(exchange)
#### DispatcherHandler处理请求
1. 调用HandlerMappings获取Handler
    => invokeHandler(exchange, handler) 
    => handleResult(exchange, result)
2. invokeHandler(exchange, handler){
    2.1 调用HandlerAdapter.handle(exchange, handler)
    2.2 RouterFunctionMapping.handle => getHandlerInternal(){
        ServerRequest request = ServerRequest.create(exchange, this.messageReaders);
    }
    2.3 调用routerFunction:SameComposedRouterFunction.route(request);
}
3. handleResult(exchange, result){
    3.1 遍历系统中所有HandlerResultHandler并调用supports(result)，刷选出合适的HandlerResultHandler
    3.1 执行HandlerResultHandler.handleResult(exchange, result)将结果输出response
}
    