package org.omg.CosNaming;

/**
* org/omg/CosNaming/NameComponentHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/org/omg/CosNaming/nameservice.idl
* Wednesday, March 15, 2017 1:24:18 AM PDT
*/

public final class NameComponentHolder implements org.omg.CORBA.portable.Streamable
{
  public NameComponent value = null;

  public NameComponentHolder ()
  {
  }

  public NameComponentHolder (NameComponent initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = NameComponentHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    NameComponentHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return NameComponentHelper.type ();
  }

}
