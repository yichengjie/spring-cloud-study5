package com.yicj.study.webflux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Component
public class HelloHandler {

    public Mono<ServerResponse> hello(ServerRequest request){
       return ok().contentType(MediaType.TEXT_PLAIN)
               .body(Mono.just("hello"), String.class);
    }

    public Mono<ServerResponse> world(ServerRequest request){
        return ok().contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("world"), String.class) ;
    }

    public Mono<ServerResponse> times(ServerRequest request){
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.interval(Duration.ofSeconds(1))
                        .map(it -> new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);
    }

}
