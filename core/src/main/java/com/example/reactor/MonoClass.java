package com.example.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
public class MonoClass {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> ids = ifhrIds();

        Flux<String> combinations =
                ids.flatMap(id -> {
                    Mono<String> nameTask = ifhrName(id);
                    Mono<Integer> statTask = ifhrStat(id);

                    return nameTask.zipWith(statTask,
                            (name, stat) -> "Name " + name + " has stats " + stat);
                });

        Mono<List<String>> result = combinations.collectList();
        long start = System.currentTimeMillis();
        //阻塞主线程获取结果
        List<String> results = result.block();
        log.info("time: {}ms", System.currentTimeMillis() - start);
        log.info("results: {}", results);
    }

    @SneakyThrows
    private static Flux<String> ifhrIds() throws InterruptedException {
        //Thread.sleep(3000);
        return Flux.just("1", "2", "3");
    }

    @SneakyThrows
    private static Mono<String> ifhrName(String id) {
        String name = "";
        if (id.equals("1")) {
            name = "Joe";
        } else if (id.equals("2")) {
            name = "Jane";
        } else if (id.equals("3")) {
            name = "Jack";
        } else if (id.equals("4")) {
            name = "Tom";
        } else {
            name = "Jenny";
        }
        return Mono.just("Name" + name);
    }

    @SneakyThrows
    private static Mono<Integer> ifhrStat(String id) {
        return Mono.just(Integer.parseInt(id) + 100);
    }
}
