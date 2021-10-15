package com.sevenluo.test.concurrent.util;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @outhor: qige
 * @create: 2020-10-17 15:30
 */
public class ExchangerTest {


    private static final Exchanger<String> exgr = new Exchanger<String>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "银行流水A";
                    // A录入银行流水数据
                    System.out.println("A开始执行了");
                    String exchange = exgr.exchange(A);
                    System.out.println("A得到的数据="+exchange);
                } catch (InterruptedException e) {
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "银行流水B";

                    // B录入银行流水数据
                    String A = exgr.exchange(B);
                    System.out.println("A和B数据是否一致：" + A.equals(B) + "，A录入的是："
                            + A + "，B录入是：" + B);
                } catch (InterruptedException e) {
                }
            }
        });
        threadPool.shutdown();
    }

}
