package com.sevenluo.test.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @description:
 * @outhor: qige
 * @create: 2020-07-20 7:18
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(2);

        arrayBlockingQueue.offer(1);
        arrayBlockingQueue.put("2");
        System.out.println("满了");
        arrayBlockingQueue.put(3);
        for (Object i : arrayBlockingQueue) {
            System.out.println(i);
        }
    }
}
