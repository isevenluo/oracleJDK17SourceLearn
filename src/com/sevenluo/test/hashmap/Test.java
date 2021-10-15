package com.sevenluo.test.hashmap;

import java.util.HashMap;

/**
 * @description:
 * @outhor: luoxiaohei
 * @create: 2020-04-21 23:10
 */
public class Test {
    static final HashMap<String, String> map = new HashMap<String, String>(2);

//    public static void main(String[] args) throws InterruptedException {
//
//        for (int i = 0;i<10000;i++) {
//            System.out.println(i);
//            test();
//
//        }
//
//
//    }
//    public static void test() throws InterruptedException {
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100000; i++) {
//                    int finalI1 = i;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            map.put(String.valueOf(finalI1), "");
//                        }
//                    }, "ftf" + i).start();
//                }
//            }
//        }, "ftf");
//        t.start();
//        t.join();
//    }
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static int numberOfLeadingZeros(int i) {
        // HD, Figure 5-6
        if (i == 0)
            return 32;
        int n = 1;
        if (i >>> 16 == 0) { n += 16; i <<= 16; }
        if (i >>> 24 == 0) { n +=  8; i <<=  8; }
        if (i >>> 28 == 0) { n +=  4; i <<=  4; }
        if (i >>> 30 == 0) { n +=  2; i <<=  2; }
        n -= i >>> 31;
        return n;
    }

    public static void main(String[] args) {
        int i = tableSizeFor(9);
        System.out.println(i);
        System.out.println(MAXIMUM_CAPACITY);
        System.out.println(numberOfLeadingZeros(16));
        System.out.println(Integer.numberOfLeadingZeros(16) | (1 << (16 - 1)));
        int i1 = Integer.numberOfLeadingZeros(32) | (1 << (16 - 1));
        System.out.println(i1 << 16);

    }




}
