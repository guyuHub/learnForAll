package com.example.learnForAll.reactive;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.ListUtils;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Demo3 {
    /**
     * 回压示例
     *
     * @param args
     */
    public static void main(String[] args) {
        List<String> source = Lists.newArrayList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "o", "p", "q", "r", "s", "d");
        Flux<String> flux = Flux.fromIterable(source);
        flux
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {

                        request(1);
                    }

                    @Override
                    protected void hookOnNext(String value) {
                        System.out.println("==================" + value);
                        if (value.equals("e") || value.equals("K")) {
                            request(2);
                        } else if (value.equals("p")) {
                            request(0);
                        } else {
                            request(1);
                        }
                    }
                });

    }

}
