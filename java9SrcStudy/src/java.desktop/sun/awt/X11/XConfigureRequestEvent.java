// This file is an automatically generated file, please do not edit this file, modify the WrapperGenerator.java file instead !

package sun.awt.X11;

import jdk.internal.misc.Unsafe;

import sun.util.logging.PlatformLogger;
public class XConfigureRequestEvent extends XWrapperBase { 
	private Unsafe unsafe = XlibWrapper.unsafe; 
	private final boolean should_free_memory;
	public static int getSize() { return 96; }
	public int getDataSize() { return getSize(); }

	long pData;

	public long getPData() { return pData; }


	public XConfigureRequestEvent(long addr) {
		log.finest("Creating");
		pData=addr;
		should_free_memory = false;
	}


	public XConfigureRequestEvent() {
		log.finest("Creating");
		pData = unsafe.allocateMemory(getSize());
		should_free_memory = true;
	}


	public void dispose() {
		log.finest("Disposing");
		if (should_free_memory) {
			log.finest("freeing memory");
			unsafe.freeMemory(pData); 
	}
		}
	public int get_type() { log.finest("");return (Native.getInt(pData+0)); }
	public void set_type(int v) { log.finest(""); Native.putInt(pData+0, v); }
	public long get_serial() { log.finest("");return (Native.getLong(pData+8)); }
	public void set_serial(long v) { log.finest(""); Native.putLong(pData+8, v); }
	public boolean get_send_event() { log.finest("");return (Native.getBool(pData+16)); }
	public void set_send_event(boolean v) { log.finest(""); Native.putBool(pData+16, v); }
	public long get_display() { log.finest("");return (Native.getLong(pData+24)); }
	public void set_display(long v) { log.finest(""); Native.putLong(pData+24, v); }
	public long get_parent() { log.finest("");return (Native.getLong(pData+32)); }
	public void set_parent(long v) { log.finest(""); Native.putLong(pData+32, v); }
	public long get_window() { log.finest("");return (Native.getLong(pData+40)); }
	public void set_window(long v) { log.finest(""); Native.putLong(pData+40, v); }
	public int get_x() { log.finest("");return (Native.getInt(pData+48)); }
	public void set_x(int v) { log.finest(""); Native.putInt(pData+48, v); }
	public int get_y() { log.finest("");return (Native.getInt(pData+52)); }
	public void set_y(int v) { log.finest(""); Native.putInt(pData+52, v); }
	public int get_width() { log.finest("");return (Native.getInt(pData+56)); }
	public void set_width(int v) { log.finest(""); Native.putInt(pData+56, v); }
	public int get_height() { log.finest("");return (Native.getInt(pData+60)); }
	public void set_height(int v) { log.finest(""); Native.putInt(pData+60, v); }
	public int get_border_width() { log.finest("");return (Native.getInt(pData+64)); }
	public void set_border_width(int v) { log.finest(""); Native.putInt(pData+64, v); }
	public long get_above() { log.finest("");return (Native.getLong(pData+72)); }
	public void set_above(long v) { log.finest(""); Native.putLong(pData+72, v); }
	public int get_detail() { log.finest("");return (Native.getInt(pData+80)); }
	public void set_detail(int v) { log.finest(""); Native.putInt(pData+80, v); }
	public long get_value_mask() { log.finest("");return (Native.getLong(pData+88)); }
	public void set_value_mask(long v) { log.finest(""); Native.putLong(pData+88, v); }


	String getName() {
		return "XConfigureRequestEvent"; 
	}


	String getFieldsAsString() {
		StringBuilder ret = new StringBuilder(560);

		ret.append("type = ").append( XlibWrapper.eventToString[get_type()] ).append(", ");
		ret.append("serial = ").append( get_serial() ).append(", ");
		ret.append("send_event = ").append( get_send_event() ).append(", ");
		ret.append("display = ").append( get_display() ).append(", ");
		ret.append("parent = ").append( get_parent() ).append(", ");
		ret.append("window = " ).append( getWindow(get_window()) ).append(", ");
		ret.append("x = ").append( get_x() ).append(", ");
		ret.append("y = ").append( get_y() ).append(", ");
		ret.append("width = ").append( get_width() ).append(", ");
		ret.append("height = ").append( get_height() ).append(", ");
		ret.append("border_width = ").append( get_border_width() ).append(", ");
		ret.append("above = ").append( get_above() ).append(", ");
		ret.append("detail = ").append( get_detail() ).append(", ");
		ret.append("value_mask = ").append( get_value_mask() ).append(", ");
		return ret.toString();
	}


}



