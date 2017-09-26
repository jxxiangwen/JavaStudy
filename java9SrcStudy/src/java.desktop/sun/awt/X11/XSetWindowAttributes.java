// This file is an automatically generated file, please do not edit this file, modify the WrapperGenerator.java file instead !

package sun.awt.X11;

import jdk.internal.misc.Unsafe;

import sun.util.logging.PlatformLogger;
public class XSetWindowAttributes extends XWrapperBase { 
	private Unsafe unsafe = XlibWrapper.unsafe; 
	private final boolean should_free_memory;
	public static int getSize() { return 112; }
	public int getDataSize() { return getSize(); }

	long pData;

	public long getPData() { return pData; }


	public XSetWindowAttributes(long addr) {
		log.finest("Creating");
		pData=addr;
		should_free_memory = false;
	}


	public XSetWindowAttributes() {
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
	public long get_background_pixmap() { log.finest("");return (Native.getLong(pData+0)); }
	public void set_background_pixmap(long v) { log.finest(""); Native.putLong(pData+0, v); }
	public long get_background_pixel() { log.finest("");return (Native.getLong(pData+8)); }
	public void set_background_pixel(long v) { log.finest(""); Native.putLong(pData+8, v); }
	public long get_border_pixmap() { log.finest("");return (Native.getLong(pData+16)); }
	public void set_border_pixmap(long v) { log.finest(""); Native.putLong(pData+16, v); }
	public long get_border_pixel() { log.finest("");return (Native.getLong(pData+24)); }
	public void set_border_pixel(long v) { log.finest(""); Native.putLong(pData+24, v); }
	public int get_bit_gravity() { log.finest("");return (Native.getInt(pData+32)); }
	public void set_bit_gravity(int v) { log.finest(""); Native.putInt(pData+32, v); }
	public int get_win_gravity() { log.finest("");return (Native.getInt(pData+36)); }
	public void set_win_gravity(int v) { log.finest(""); Native.putInt(pData+36, v); }
	public int get_backing_store() { log.finest("");return (Native.getInt(pData+40)); }
	public void set_backing_store(int v) { log.finest(""); Native.putInt(pData+40, v); }
	public long get_backing_planes() { log.finest("");return (Native.getLong(pData+48)); }
	public void set_backing_planes(long v) { log.finest(""); Native.putLong(pData+48, v); }
	public long get_backing_pixel() { log.finest("");return (Native.getLong(pData+56)); }
	public void set_backing_pixel(long v) { log.finest(""); Native.putLong(pData+56, v); }
	public boolean get_save_under() { log.finest("");return (Native.getBool(pData+64)); }
	public void set_save_under(boolean v) { log.finest(""); Native.putBool(pData+64, v); }
	public long get_event_mask() { log.finest("");return (Native.getLong(pData+72)); }
	public void set_event_mask(long v) { log.finest(""); Native.putLong(pData+72, v); }
	public long get_do_not_propagate_mask() { log.finest("");return (Native.getLong(pData+80)); }
	public void set_do_not_propagate_mask(long v) { log.finest(""); Native.putLong(pData+80, v); }
	public boolean get_override_redirect() { log.finest("");return (Native.getBool(pData+88)); }
	public void set_override_redirect(boolean v) { log.finest(""); Native.putBool(pData+88, v); }
	public long get_colormap() { log.finest("");return (Native.getLong(pData+96)); }
	public void set_colormap(long v) { log.finest(""); Native.putLong(pData+96, v); }
	public long get_cursor() { log.finest("");return (Native.getLong(pData+104)); }
	public void set_cursor(long v) { log.finest(""); Native.putLong(pData+104, v); }


	String getName() {
		return "XSetWindowAttributes"; 
	}


	String getFieldsAsString() {
		StringBuilder ret = new StringBuilder(600);

		ret.append("background_pixmap = ").append( get_background_pixmap() ).append(", ");
		ret.append("background_pixel = ").append( get_background_pixel() ).append(", ");
		ret.append("border_pixmap = ").append( get_border_pixmap() ).append(", ");
		ret.append("border_pixel = ").append( get_border_pixel() ).append(", ");
		ret.append("bit_gravity = ").append( get_bit_gravity() ).append(", ");
		ret.append("win_gravity = ").append( get_win_gravity() ).append(", ");
		ret.append("backing_store = ").append( get_backing_store() ).append(", ");
		ret.append("backing_planes = ").append( get_backing_planes() ).append(", ");
		ret.append("backing_pixel = ").append( get_backing_pixel() ).append(", ");
		ret.append("save_under = ").append( get_save_under() ).append(", ");
		ret.append("event_mask = ").append( get_event_mask() ).append(", ");
		ret.append("do_not_propagate_mask = ").append( get_do_not_propagate_mask() ).append(", ");
		ret.append("override_redirect = ").append( get_override_redirect() ).append(", ");
		ret.append("colormap = ").append( get_colormap() ).append(", ");
		ret.append("cursor = ").append( get_cursor() ).append(", ");
		return ret.toString();
	}


}



