package com.stevenw.demo.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 测试volatile和java原子性操作
 * @author stevenw
 * @date 2019/4/1
 */
public class TestVolatile {
    static CountDownLatch countDownLatch = new CountDownLatch(30);
    public static int num = 0;
    public void doAdd() throws InterruptedException{
        for (int i = 0; i < 30; i++) {

            new Thread(){
                @Override
                public void run(){
                    for (int j = 0; j < 1000; j++) {
                        num++;
                        countDownLatch.countDown();
                    }
                }
            }.start();
        }
        countDownLatch.await();
        System.err.println(num);
    }
}
