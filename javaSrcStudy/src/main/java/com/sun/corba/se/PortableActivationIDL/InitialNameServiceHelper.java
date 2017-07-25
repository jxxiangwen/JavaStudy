package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/InitialNameServiceHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Wednesday, March 15, 2017 1:24:18 AM PDT
*/


/** Interface used to support binding references in the bootstrap name
    * service.
    */
abstract public class InitialNameServiceHelper
{
  private static String  _id = "IDL:PortableActivationIDL/InitialNameService:1.0";

  public static void insert (org.omg.CORBA.Any a, InitialNameService that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static InitialNameService extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (InitialNameServiceHelper.id (), "InitialNameService");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static InitialNameService read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_InitialNameServiceStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, InitialNameService value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static InitialNameService narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof InitialNameService)
      return (InitialNameService)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _InitialNameServiceStub stub = new _InitialNameServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static InitialNameService unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof InitialNameService)
      return (InitialNameService)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _InitialNameServiceStub stub = new _InitialNameServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
