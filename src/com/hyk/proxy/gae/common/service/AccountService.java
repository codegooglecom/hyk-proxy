/**
 * This file is part of the hyk-proxy project.
 * Copyright (c) 2010 Yin QiWen <yinqiwen@gmail.com>
 *
 * Description: AccountService.java 
 *
 * @author yinqiwen [ 2010-4-8 | 07:50:48 PM ]
 *
 */
package com.hyk.proxy.gae.common.service;

import java.util.List;

import com.hyk.proxy.gae.common.auth.Operation;
import com.hyk.rpc.core.annotation.Remote;

/**
 *
 */
@Remote
public interface AccountService
{
	public String modifyPassword(String user, String oldPass, String newPass);
	
	public String createGroup(String group);
	
	public String createUser(String user, String group, String passwd);
	
	public String deleteUser(String username);
	
	public String deleteGroup(String groupname);
	
	public List<String[]> getUsersInfo();
	
	public List<String[]> getGroupsInfo();
	
	public String operationOnGroupBlackList(String group, String host, Operation operation);
	
	public String operationOnUserBlackList(String user, String host, Operation operation);
	
	public String operationOnUserTraffic(String user, String host, int trafficRestriction);
}
