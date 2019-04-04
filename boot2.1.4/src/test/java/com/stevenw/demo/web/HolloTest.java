package com.stevenw.demo.web;

import com.stevenw.demo.thread.TestVolatile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author stevenw
 * @date 2019/3/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HolloTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext mac;

    @Autowired
    private HelloController helloController;
    @Before
    public void setUp() throws Exception{
        //直接new会导致controller中的service无法注入，应该通过spring注入生成HelloController
        //mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
        mvc = MockMvcBuilders.standaloneSetup(helloController).build();
//        mvc = MockMvcBuilders.webAppContextSetup(mac); 或者使用此方法从web应用上下文获取
    }
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andDo(print()); //打印返回值
//                .andExpect(status().isOk()).andExpect(content().string(equalTo("hello world"))); 比较返回值;

    }
    @Test
    public void testThread() throws Exception{
        TestVolatile testVolatile = new TestVolatile();
        testVolatile.doAdd();

    }
}
