package com.stevenw.demo.util;

import org.jivesoftware.smack.AbstractXMPPConnection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;


/**
 * @author stevenw
 * @date 2019/5/23
 */
public class SendMsg {


    private static XMPPTCPConnectionConfiguration config;
    private static AbstractXMPPConnection conn;

    static {
        try {
            config = XMPPTCPConnectionConfiguration.builder()
                    .setXmppDomain("localhost")
                    .setHost(ParamUtil.SERVERHOST)
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
            conn = new XMPPTCPConnection(config);
            conn.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login(String username,String password){
        try {
            if(conn!=null){
                System.err.println(conn.isConnected());
                if(!conn.isConnected()){
                    conn.connect();
                }
                conn.login(username, password);
                System.out.println("user "+username+" login successfully.");
            }
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static void sendMessage(String username,String password,String msg,String userTo){
        //先登录
        if(2 == OpenfireUtils.sessionStatus(username)){
            login(username,password);
        }
        //再构建聊天室
        ChatManager cm = ChatManager.getInstanceFor(conn);
        cm.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                System.out.println("message from : "+from+" : "+message.getBody());
            }
        });
        try {
            EntityBareJid jid = JidCreate.entityBareFrom(userTo +"@" + ParamUtil.SERVERHOST);
            Chat chat = cm.chatWith(jid);
            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("I am online");
            conn.sendStanza(presence);
            Message message = new Message();
            message.setBody(msg);
            chat.send(message);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static AbstractXMPPConnection getConn() {
        return conn;
    }
}
