package com.yicj.study.feign.handler;

import com.yicj.study.feign.service.HelloFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class HelloFeignHandler {

    @Autowired
    private HelloFeignService helloFeignService ;

    public Mono<ServerResponse> searchGithubRepoByStr(ServerRequest request){
        Optional<String> optional = request.queryParam("str");
        String retContent = helloFeignService.searchRepo(optional.get());
        return ServerResponse.ok().body(Mono.just(retContent), String.class) ;
    }
}
