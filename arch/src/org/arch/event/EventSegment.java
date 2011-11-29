/**
 * 
 */
package org.arch.event;

import org.arch.buffer.Buffer;
import org.arch.buffer.BufferHelper;

/**
 * @author qiyingwang
 *
 */
@EventType(Event.RESERVED_SEGMENT_EVENT_TYPE)
@EventVersion(1)
public class EventSegment extends Event
{
	public int sequence;
	public int total;
	public Buffer content;
	
	@Override
    public boolean onDecode(Buffer buffer)
    {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public boolean onEncode(Buffer buffer)
    {
	    BufferHelper.writeVarInt(buffer, sequence);
	    BufferHelper.writeVarInt(buffer, total);
	    BufferHelper.writeVarInt(buffer, content.readableBytes());
	    buffer.write(content, content.readableBytes());
	    return true;
    }

	
}
