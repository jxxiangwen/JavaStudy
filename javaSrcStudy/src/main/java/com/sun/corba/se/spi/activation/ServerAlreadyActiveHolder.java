package com.sun.corba.se.spi.activation;

/**
* com/sun/corba/se/spi/activation/ServerAlreadyActiveHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Wednesday, March 15, 2017 1:24:18 AM PDT
*/

public final class ServerAlreadyActiveHolder implements org.omg.CORBA.portable.Streamable
{
  public ServerAlreadyActive value = null;

  public ServerAlreadyActiveHolder ()
  {
  }

  public ServerAlreadyActiveHolder (ServerAlreadyActive initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ServerAlreadyActiveHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ServerAlreadyActiveHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ServerAlreadyActiveHelper.type ();
  }

}
