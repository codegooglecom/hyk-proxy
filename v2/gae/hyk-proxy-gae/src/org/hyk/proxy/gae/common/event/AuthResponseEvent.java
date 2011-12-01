/**
 * 
 */
package org.hyk.proxy.gae.common.event;

import java.io.IOException;

import org.arch.buffer.Buffer;
import org.arch.buffer.BufferHelper;
import org.arch.event.Event;
import org.arch.event.EventType;
import org.arch.event.EventVersion;
import org.hyk.proxy.gae.common.GAEConstants;

/**
 * @author qiyingwang
 * 
 */
@EventType(GAEConstants.AUTH_REQUEST_EVENT_TYPE)
@EventVersion(1)
public class AuthResponseEvent extends Event
{
	public String appid;
	public String token;
	public String error;

	@Override
	protected boolean onDecode(Buffer buffer)
	{
		try
		{
			appid = BufferHelper.readVarString(buffer);
			token = BufferHelper.readVarString(buffer);
			error = BufferHelper.readVarString(buffer);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	protected boolean onEncode(Buffer buffer)
	{
		BufferHelper.writeVarString(buffer, appid);
		BufferHelper.writeVarString(buffer, token);
		BufferHelper.writeVarString(buffer, error);
		return true;
	}

}
