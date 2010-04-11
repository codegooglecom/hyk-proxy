/**
 * This file is part of the hyk-proxy project.
 * Copyright (c) 2010 Yin QiWen <yinqiwen@gmail.com>
 *
 * Description: AuthRuntimeException.java 
 *
 * @author yinqiwen [ 2010-4-10 | ����09:43:23 ]
 *
 */
package com.hyk.proxy.gae.common.service;

/**
 *
 */
public class AuthRuntimeException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1783551634828256787L;

	public AuthRuntimeException(String message)
	{
		super(message);
	}
	
	public AuthRuntimeException(String message, Throwable e)
	{
		super(message, e);
	}
}
