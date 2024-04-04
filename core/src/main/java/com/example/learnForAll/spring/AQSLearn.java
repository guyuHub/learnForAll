package com.example.learnForAll.spring;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @author guyu
 * @desc
 * @date 2024/3/30-03:39
 **/
@Component
@Aspect
public class AQSLearn {

    private ReentrantLock lock = new ReentrantLock();

    public void reentrantLockTest() {
        Condition conditionOne = lock.newCondition(); //(2)
        Condition conditionTwo = lock.newCondition(); //(2)

        Thread one = new Thread(() -> {
            System.out.println("one before lock");
            lock.lock();
            System.out.println("one after lock");
            try {
                System.out.println("one before awit");
                conditionOne.await();
                System.out.println("one after awit");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("one before signal");
            conditionTwo.signal();
            lock.unlock();
            System.out.println(" one end");

        });
        one.start();

        Thread two = new Thread(() -> {
            System.out.println("two before lock");
            lock.lock();
            System.out.println("two after lock");
            System.out.println("two before signal");
            conditionOne.signal();
            try {
                System.out.println("two before await");
                conditionTwo.await();
                System.out.println("two after await");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.unlock();
            System.out.println("two end");

        });
        two.start();
    }


    public static void main(String[] args) throws InterruptedException {
        AQSLearn aqsLearn = new AQSLearn();
        aqsLearn. reentrantLockTest();
        Thread.currentThread().sleep(5000);

    }
}
