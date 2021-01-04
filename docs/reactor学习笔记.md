1. reactor与java8 stream区别
    ```text
    1.1 形似神不似
    1.2 reactor: push模式，服务端推送数据给客户端
    1.3 stream: pull模式，客户端主动向服务端请求数据
    ```
2. publishOn与subscribeOn对比总结
    ```text
    2.1 publishOn: 它将上游信号传给下游，同时改变后续的操作符的执行所在线程，
        直到下一个publishOn出现在这个链上
    2.2 subscribeOn: 作用于向上的订阅链，无论处于操作链的什么位置，
        它都会影响到源头的线程执行环境，但是不会影响到后续的publishOn
    ```