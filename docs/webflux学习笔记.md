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
   }   
   2.2 将所有RouterFunction组装为DifferentComposedRouterFunction并一个接一个连起来
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
1. 遍历handlerMappings并调用handle方法{
    1.1 RouterFunctionMapping.handle => getHandlerInternal(){
        ServerRequest request = ServerRequest.create(exchange, this.messageReaders);
    }，
    1.2 调用routerFunction:SameComposedRouterFunction.route(request);
}
    