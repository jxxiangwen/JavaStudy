package com.sun.corba.se.spi.activation;

/**
* com/sun/corba/se/spi/activation/ServerNotRegisteredHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /scratch/HUDSON/workspace/9-2-build-linux-amd64-phase2/jdk9/6725/corba/src/java.corba/share/classes/com/sun/corba/se/spi/activation/activation.idl
* Thursday, August 3, 2017 3:57:51 AM UTC
*/

public final class ServerNotRegisteredHolder implements org.omg.CORBA.portable.Streamable
{
  public com.sun.corba.se.spi.activation.ServerNotRegistered value = null;

  public ServerNotRegisteredHolder ()
  {
  }

  public ServerNotRegisteredHolder (com.sun.corba.se.spi.activation.ServerNotRegistered initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.sun.corba.se.spi.activation.ServerNotRegisteredHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.sun.corba.se.spi.activation.ServerNotRegisteredHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.sun.corba.se.spi.activation.ServerNotRegisteredHelper.type ();
  }

}
