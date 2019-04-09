package com.stevenw.demo.thread;

import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.service.UserInfoService;
import com.stevenw.demo.service.impl.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试volatile和java原子性操作
 * @author stevenw
 * @date 2019/4/1
 */
@Service
public class TestVolatile {
    static CountDownLatch countDownLatch = new CountDownLatch(10000);
    @Autowired
    private UserInfoService userInfoService;

    public static volatile int num = 0;
    //原子操作类
    public static AtomicInteger num1 = new AtomicInteger(0);
    public void doAdd() throws InterruptedException{
        for (int i = 0; i < 30; i++) {

            new Thread(){
                @Override
                public void run(){
                    for (int j = 0; j < 1000; j++) {
                        //原子操作
                        num1.incrementAndGet();
                    }
                    countDownLatch.countDown();
                }
            }.start();
        }
        countDownLatch.await();
        System.err.println(num1);
    }

    public void addUser() throws InterruptedException{
        for (int i = 0; i < 10000; i++) {
            new Thread(){
                @Override
                public void run() {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setAccount("a");
                    int random1  = (int)(Math.random()*11);
                    int random2  = (int)((Math.random()+1)*1000);
                    userInfo.setName("a"+random1);
                    userInfo.setPwd("pwd"+random2);
                    userInfoService.addUser(userInfo);
//                    System.err.println(userInfo.getAccount());
                    countDownLatch.countDown();
                }
            }.start();

        }
        countDownLatch.await();
    }


}
