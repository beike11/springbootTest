package com.stevenw.demo.thread;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author stevenw
 * @date 2019/4/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadTest {
    @Autowired
    TestVolatile testVolatile;

    @Test
    public void testThread() throws Exception{

//        testVolatile.doAdd();
        testVolatile.addUser();
    }

}
