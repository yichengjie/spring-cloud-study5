package com.yicj.study.webflux.handler;

import com.yicj.study.webflux.entity.User;
import com.yicj.study.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Component
public class HelloHandler {

    @Autowired
    private UserService userService ;

    public Mono<ServerResponse> hello(ServerRequest request){
       return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
               .body(Mono.just("hello"), String.class);
    }

    public Mono<ServerResponse> world(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("world"), String.class) ;
    }

    public Mono<ServerResponse> times(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.interval(Duration.ofSeconds(1))
                        .map(it -> new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String idStr = request.pathVariable("id");
        Integer id = Integer.parseInt(idStr) ;
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userService.findById(id)), User.class) ;
    }

}
