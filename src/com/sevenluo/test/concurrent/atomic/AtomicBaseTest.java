package com.sevenluo.test.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 原子更新基本类型
 * @outhor: qige
 * @create: 2020-08-29 14:29
 */
public class AtomicBaseTest {

    @Test
    public void testAtomicIntegerTest() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int andIncrement = atomicInteger.getAndIncrement();
        System.out.println(andIncrement);
        System.out.println(atomicInteger.get());
    }

    private static int m = 0;



    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(2);
        AtomicInteger i = new AtomicInteger(0);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 5000; j++) {
                    i.incrementAndGet();
                }
                cdl.countDown();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 5000; j++) {
                    i.incrementAndGet();
                }
                cdl.countDown();
            }
        });
        t1.start();
        t2.start();

        cdl.await();
        System.out.println("result=" + i.get());
    }

}
