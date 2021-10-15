package com.sevenluo.test.concurrent.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description:
 * @outhor: qige
 * @create: 2020-10-17 13:36
 */
public class CycleBarrierTest {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2,new A());

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();

        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(3);
    }

    public static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(2);
        }
    }

}
