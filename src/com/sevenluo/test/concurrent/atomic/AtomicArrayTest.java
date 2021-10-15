package com.sevenluo.test.concurrent.atomic;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @description: 原子操作数组示例
 * @outhor: qige
 * @create: 2020-08-29 11:58
 */
public class AtomicArrayTest {

    @Test
    public void testAtomicIntegerArray() {
        int[] value = new int[]{1,2};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(value);
        int andSet = atomicIntegerArray.getAndSet(0, 3);
        // 以原子方式设置为newValue的值，并返回旧值
        Assert.assertEquals(4,atomicIntegerArray.addAndGet(0,1));
        Assert.assertEquals(1,andSet);
        Assert.assertEquals(4,atomicIntegerArray.get(0));
        // AtomicIntegerArray会将传入的数组复制一份，不会影响原数组
        Assert.assertEquals(1,value[0]);

    }
}
