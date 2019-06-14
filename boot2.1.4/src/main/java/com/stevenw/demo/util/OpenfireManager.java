package com.stevenw.demo.util;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * openfire管理
 */
public class OpenfireManager {

	public static final String SERVER_HOST = "47.106.70.29";

	public static final String USERNAME = "admin";
	public static final String PWD = "admin";
	public static final int SERVER_PORT = 5222;

	private ChatManager chatManager;

	private static OpenfireManager openfireManager = null;

	private OpenfireManager() {
		AbstractXMPPConnection connection = SendMsg.getConn();
		try {
			connection.connect();
			connection.login(USERNAME, PWD);
			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus("I am online");
			connection.sendStanza(presence);
			chatManager = ChatManager.getInstanceFor(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized OpenfireManager getInstance() {
		if (openfireManager == null) {
			openfireManager = new OpenfireManager();
		}
		return openfireManager;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

}
