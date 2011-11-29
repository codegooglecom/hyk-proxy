/**
 * 
 */
package org.hyk.proxy.gae.common;

import java.io.IOException;

import org.arch.buffer.Buffer;
import org.arch.compress.lzf.LZFDecoder;
import org.arch.compress.lzf.LZFEncoder;
import org.arch.compress.snappy.Snappy;
import org.arch.encrypt.SimpleEncrypt;
import org.arch.event.Event;
import org.arch.event.EventDispatcher;

/**
 * @author qiyingwang
 *
 */
public class GAEEventHelper
{
	
	public static Event parseEvent(Buffer buffer)
	{
		EventHeaderTags tags = new EventHeaderTags();
		if(EventHeaderTags.readHeaderTags(buffer, tags))
		{
			switch (tags.encrypter)
			{
				case SE1:
				{
					SimpleEncrypt se1 = new SimpleEncrypt();
					se1.decrypt(buffer);
					break;
				}
				default:
				{
					break;
				}
			}
			switch (tags.compressor)
	        {
				case SNAPPY:
				{	
					byte[] raw = buffer.getRawBuffer();
					int len = Snappy.getUncompressedLength(raw, buffer.getReadIndex());
					byte[] newbuf = new byte[len];
					int afterCompress = Snappy.uncompress(raw, buffer.getReadIndex(), buffer.readableBytes(), newbuf, 0);
					buffer = Buffer.wrapReadableContent(newbuf, 0, afterCompress);
					break;
				}
				case LZF:
				{
					byte[] raw = buffer.getRawBuffer();
					byte[] newbuf;
	                
					
					break;
				}
				default:
				{
					break;
				}
			}
			
		}
		return EventDispatcher.getSingletonInstance().parse(buffer);
	}
	
	public static Buffer encodeEvent(EventHeaderTags tags, Event event)
	{
		Buffer buf = new Buffer(256);
		tags.encode(buf);
		Buffer content = new Buffer(256);
		event.encode(content);
		switch (tags.compressor)
        {
			case SNAPPY:
			{	
				int len = Snappy.maxCompressedLength(content.readableBytes());
				byte[] newbuf = new byte[len];
				byte[] raw = content.getRawBuffer();
				int afterCompress = Snappy.compress(raw, content.getReadIndex(), content.readableBytes(), newbuf, 0);
				content = Buffer.wrapReadableContent(newbuf, 0, afterCompress);
				break;
			}
			case LZF:
			{
				byte[] raw = content.getRawBuffer();
				byte[] newbuf;
                try
                {
	                newbuf = LZFEncoder.encode(raw, content.readableBytes());
	                content = Buffer.wrapReadableContent(newbuf, 0, newbuf.length);
                }
                catch (IOException e)
                {
                	
	                return null;
                }
				
				break;
			}
			default:
			{
				break;
			}
		}
		switch (tags.encrypter)
		{
			case SE1:
			{
				SimpleEncrypt se1 = new SimpleEncrypt();
				se1.encrypt(content);
				break;
			}
			default:
			{
				break;
			}
		}
		
		buf.write(content, content.readableBytes());
		return buf;
	}
}
