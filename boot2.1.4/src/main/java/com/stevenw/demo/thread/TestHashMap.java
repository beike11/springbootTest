package com.stevenw.demo.thread;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

/**
 * @author stevenw
 * @date 2019/4/9
 */
@Service
public class TestHashMap {
    private final Map<Integer,String> hashMap =  new HashMap<>();

    public void testHashPut(){
        for (int i = 0; i < 10000; i++) {

        }
    }
}
