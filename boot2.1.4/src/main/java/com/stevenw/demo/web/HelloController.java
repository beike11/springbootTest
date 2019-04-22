package com.stevenw.demo.web;

import com.stevenw.demo.annotation.JSON;
import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.service.UserInfoService;
import com.stevenw.demo.thread.TestVolatile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author stevenw
 * @date 2019/3/22
 */
@RestController
public class HelloController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    TestVolatile testVolatile;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;
    /**
     * 测试动态jackson
     * @return
     */
    @GetMapping("/hello")
    @JSON(type = UserInfo.class,filter = "pwd,name")
    public List<UserInfo> index(){
        System.err.println(userInfoService);
        List<UserInfo> users = userInfoService.getAllUser();
        System.err.println(users.size());
        return users;
    }
    @GetMapping("/addUser")
    public String addAll() throws InterruptedException{
        testVolatile.addUser();
        return "success";
    }
    @GetMapping("/jdbcTemplateTest")
    public List<String> jdbcTemplateTest(){

        String sql = "select * from user_info";
        List <String>a = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Nullable
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String value = resultSet.getObject("entry_time").toString();
                return value;
            }
        });
        String inser = "insert into user_info(account,pwd,entry_time) values(?,?,now()),(?,?,now())";
        jdbcTemplate.update(inser,new Object[]{null,"wdwd","2","wdwadaw"});
        return a;
    }

    @GetMapping("/jdbcTest")
    public int[] jdbcTest() throws SQLException {

        String inser = "insert into user_info(account,pwd,entry_time) values(?,?,?),(?,?,?)";
         Connection connection =  dataSource.getConnection();
        connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(inser);
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String,Object> t1 = new HashMap();
            Map<String,Object> t2 = new HashMap();
            t1.put("acount","11");
            t1.put("pwd",null);
            t1.put("entry_time",new Date());
            t2.put("acount","11");
            t2.put("pwd",null);
            t2.put("entry_time",new Date());
            String [] fields = new String []{"acount","pwd","entry_time"};
            list.add(t1);
            list.add(t2);
        for (int i = 1; i <=list.size() ; i++) {
            for (int j = 1; j <=fields.length ; j++) {
               int temp = fields.length*(i-1)+j;
                System.err.println(temp);
                preparedStatement.setObject(temp,list.get(i-1).get(fields[j-1]));
            }
        }
            preparedStatement.addBatch();
        int[] count= preparedStatement.executeBatch();
        connection.commit();
        return count;
    }
}
