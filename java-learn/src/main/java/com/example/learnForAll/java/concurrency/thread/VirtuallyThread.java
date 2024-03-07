package com.example.learnForAll.java.concurrency.thread;

import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class VirtuallyThread {
    public static void main(String[] args) throws InterruptedException {
        Thread platformThread = Thread.ofPlatform().name("platform_001").unstarted(() -> {
            System.out.println(Thread.currentThread());
        });
        platformThread.start();

        Thread virtual001 = Thread.ofVirtual().name("Virtual_001").unstarted(() -> {
            System.out.println(Thread.currentThread());
        });
        virtual001.start();
        virtual001.join();
    }
}
