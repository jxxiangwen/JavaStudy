package org.omg.DynamicAny;


/**
* org/omg/DynamicAny/DynUnionHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/org/omg/DynamicAny/DynamicAny.idl
* Wednesday, March 15, 2017 1:24:18 AM PDT
*/


/**
    * DynUnion objects support the manipulation of IDL unions.
    * A union can have only two valid current positions:
    * <UL>
    * <LI>zero, which denotes the discriminator
    * <LI>one, which denotes the active member
    * </UL>
    * The component_count value for a union depends on the current discriminator:
    * it is 2 for a union whose discriminator indicates a named member, and 1 otherwise.
    */
abstract public class DynUnionHelper
{
  private static String  _id = "IDL:omg.org/DynamicAny/DynUnion:1.0";

  public static void insert (org.omg.CORBA.Any a, DynUnion that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static DynUnion extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (DynUnionHelper.id (), "DynUnion");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static DynUnion read (org.omg.CORBA.portable.InputStream istream)
  {
      throw new org.omg.CORBA.MARSHAL ();
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, DynUnion value)
  {
      throw new org.omg.CORBA.MARSHAL ();
  }

  public static DynUnion narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof DynUnion)
      return (DynUnion)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _DynUnionStub stub = new _DynUnionStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static DynUnion unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof DynUnion)
      return (DynUnion)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _DynUnionStub stub = new _DynUnionStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
