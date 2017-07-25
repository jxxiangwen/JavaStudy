package org.omg.DynamicAny;


/**
* org/omg/DynamicAny/DynFixedHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-i586/jdk8u152/9280.nc/corba/src/share/classes/org/omg/DynamicAny/DynamicAny.idl
* Wednesday, June 21, 2017 1:30:50 AM PDT
*/


/**
    * DynFixed objects support the manipulation of IDL fixed values.
    * Because IDL does not have a generic type that can represent fixed types with arbitrary
    * number of digits and arbitrary scale, the operations use the IDL string type.
    */
abstract public class DynFixedHelper
{
  private static String  _id = "IDL:omg.org/DynamicAny/DynFixed:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.DynamicAny.DynFixed that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.DynamicAny.DynFixed extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.DynamicAny.DynFixedHelper.id (), "DynFixed");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.DynamicAny.DynFixed read (org.omg.CORBA.portable.InputStream istream)
  {
      throw new org.omg.CORBA.MARSHAL ();
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.DynamicAny.DynFixed value)
  {
      throw new org.omg.CORBA.MARSHAL ();
  }

  public static org.omg.DynamicAny.DynFixed narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.DynamicAny.DynFixed)
      return (org.omg.DynamicAny.DynFixed)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.DynamicAny._DynFixedStub stub = new org.omg.DynamicAny._DynFixedStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.omg.DynamicAny.DynFixed unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.DynamicAny.DynFixed)
      return (org.omg.DynamicAny.DynFixed)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.DynamicAny._DynFixedStub stub = new org.omg.DynamicAny._DynFixedStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
