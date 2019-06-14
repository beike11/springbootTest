package com.stevenw.demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stevenw.demo.annotation.JSON;
import com.stevenw.demo.dao.UserInfo;
import com.stevenw.demo.service.UserInfoService;
import com.stevenw.demo.thread.TestVolatile;
import com.stevenw.demo.util.*;
import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author stevenw
 * @date 2019/3/22
 */
@RestController
public class HelloController {
    public static ExecutorService es = Executors.newFixedThreadPool(5);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private TestVolatile testVolatile;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;
    private static AtomicInteger num = new AtomicInteger(0);
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
        String sql = "select * from code_running_flag";
        List <String>a = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Nullable
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String value = resultSet.getObject("id").toString();
                return value;
            }
        });
        System.err.println(a.get(0));
       /* connection.setAutoCommit(false);
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
            preparedStatement.addBatch();*/
        int[] count= new int[2];
//        connection.commit();
        return count;
    }

    @GetMapping("/sendMsg")
    public boolean sendOpenfireMsg() throws JsonProcessingException{
        Msg msg = new Msg();
        msg.setEntryUser("盛军");
        msg.setFormName("稟議書");
        msg.setFormNo(num.incrementAndGet());
        msg.setEnventName("这是件名");
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(msg);
        SendMsg.sendMessage("sendUser","sbpcd2019",message,"035870");
        SendMsg.sendMessage("sendUser","sbpcd2019",message,"010281");
        return  true;
    }


    @GetMapping("/login")
    public boolean loginOpenFire(String userId) throws Exception{

            OpenfireUtils.sessionStatus(userId);
            SendMsg.login("sendUser","sbpcd2019");


        return  true;
    }

    @GetMapping("/getMsg")
    public boolean getOpenfireMsg(){
        Msg msg = new Msg();
        AbstractXMPPConnection connection =  SendMsg.getConn();

        try {
            connection.connect();
            connection.login("035870","035870");
            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Gone fishing");
            // Send the packet (assume we have an XMPPConnection instance called "con").
            connection.sendStanza(presence);
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  true;
    }

    public void addListenler(XMPPTCPConnection connection){
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                System.out.println(entityBareJid);
                System.out.println(message.getBody());
            }
        });
    }
}
