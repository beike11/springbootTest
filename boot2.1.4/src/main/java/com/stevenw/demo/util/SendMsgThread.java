package com.stevenw.demo.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendMsgThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(SendMsgThread.class);

	private ChatManager chatManager;
	
	private Msg msg;
	
	public SendMsgThread(ChatManager chatManager, Msg msg){
		this.chatManager=chatManager;
		this.msg=msg;
	}
	@Override
	public void run(){

	}
}
