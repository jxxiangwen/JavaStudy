package com.sun.corba.se.PortableActivationIDL.LocatorPackage;

/**
* com/sun/corba/se/PortableActivationIDL/LocatorPackage/ServerLocationPerTypeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Wednesday, March 15, 2017 1:24:18 AM PDT
*/

public final class ServerLocationPerTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public ServerLocationPerType value = null;

  public ServerLocationPerTypeHolder ()
  {
  }

  public ServerLocationPerTypeHolder (ServerLocationPerType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ServerLocationPerTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ServerLocationPerTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ServerLocationPerTypeHelper.type ();
  }

}
