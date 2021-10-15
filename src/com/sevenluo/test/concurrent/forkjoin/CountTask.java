package com.sevenluo.test.concurrent.forkjoin;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;


/**
 * @description:
 * @outhor: qige
 * @create: 2020-07-26 10:51
 */
public class CountTask extends RecursiveTask<Integer> {
    // 定义每个任务计算多少个数
    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 任务不需要拆分，则直接计算
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i<= end; i++){
                sum+=i;
            }
        } else {
            int middle = (start+end)/2;
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle+1,end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待子任务执行完获取结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            boolean completedAbnormally = leftTask.isCompletedNormally();
            System.out.println(Thread.currentThread().getName() + ":" + completedAbnormally);
            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建一个任务
        CountTask countTask = new CountTask(1,100);
        // 执行任务
        Future<Integer> result = forkJoinPool.submit(countTask);
//        Thread.currentThread().sleep(1000);
        System.out.println("主线程结果：马上出来了" );

        System.out.println("主线程结果：" + result.get());

    }
}
