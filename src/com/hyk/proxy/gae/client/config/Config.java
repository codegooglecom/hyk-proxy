/**
 * This file is part of the hyk-proxy project.
 * Copyright (c) 2010 Yin QiWen <yinqiwen@gmail.com>
 *
 * Description: Config.java 
 *
 * @author Administrator [ 2010-1-31 | ����10:41:52 ]
 *
 */
package com.hyk.proxy.gae.client.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class Config
{
	private static final String	CONFIG_FILE			= "hyk-proxy-client.properties";
	private static final String	APPID_CONFIG		= "remoteserver.appid";
	private static final String	XMPP_USER_CONFIG	= "localserver.xmpp.user";
	private static final String	XMPP_PASS_CONFIG	= "localserver.xmpp.passwd";
	private static final String	IS_HTTP_ENABLE		= "localserver.http.enable";
	private static final String	IS_XMPP_ENABLE		= "localserver.xmpp.enable";
	private static final String	Local_Server_Host	= "localserver.host";
	private static final String	Local_Server_Port	= "localserver.port";

	private List<String>		appids				= new LinkedList<String>();
	private List<XmppAccount>	accounts			= new LinkedList<XmppAccount>();

	public String getLocalServerHost()
	{
		return localServerHost;
	}

	public int getLocalServerPort()
	{
		return localServerPort;
	}

	private String	localServerHost	= "127.0.0.1";
	private int		localServerPort	= 48100;

	private boolean	isXmppEnable;
	private boolean	isHttpEnable;

	public List<String> getAppids()
	{
		return appids;
	}

	public List<XmppAccount> getAccounts()
	{
		return accounts;
	}

	public boolean isXmppEnable()
	{
		return isXmppEnable;
	}

	public boolean isHttpEnable()
	{
		return isHttpEnable;
	}

	public void loadConfig() throws IOException
	{
		Properties props = new Properties();
		props.load(Config.class.getResourceAsStream("/" + CONFIG_FILE));

		Iterator keys = props.keySet().iterator();
		while(keys.hasNext())
		{
			String key = (String)keys.next();
			String value = props.getProperty(key);
			if(key.startsWith(APPID_CONFIG))
			{
				appids.add(value);
			}
			else if(key.startsWith(XMPP_USER_CONFIG))
			{
				String name = value;
				String id = key.substring(XMPP_USER_CONFIG.length());
				String passwdkey = XMPP_PASS_CONFIG + id;
				String passwd = props.getProperty(passwdkey);
				if(null == passwd)
				{
					throw new IOException("Configuration:" + passwdkey + " is missing.");
				}
				accounts.add(new XmppAccount(name, passwd));
			}
			else if(key.equals(IS_HTTP_ENABLE))
			{
				isHttpEnable = Boolean.parseBoolean(value);
			}
			else if(key.equals(IS_XMPP_ENABLE))
			{
				isXmppEnable = Boolean.parseBoolean(value);
			}
			else if(key.equals(Local_Server_Host))
			{
				localServerHost = value;
			}
			else if(key.equals(Local_Server_Port))
			{
				localServerPort = Integer.parseInt(value);
			}
		}

	}
}