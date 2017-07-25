package org.omg.PortableServer;


/**
* org/omg/PortableServer/ImplicitActivationPolicyValue.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/org/omg/PortableServer/poa.idl
* Wednesday, March 15, 2017 1:24:19 AM PDT
*/


/**
	 * ImplicitActivationPolicyValue has the following
	 * semantics.
	 * IMPLICIT_ACTIVATION to indicate implicit activation
	 * of servants.  This requires SYSTEM_ID and RETAIN 
	 * policies to be set.
	 * NO_IMPLICIT_ACTIVATION to indicate no implicit 
	 * servant activation.
	 */
public class ImplicitActivationPolicyValue implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static ImplicitActivationPolicyValue[] __array = new ImplicitActivationPolicyValue [__size];

  public static final int _IMPLICIT_ACTIVATION = 0;
  public static final ImplicitActivationPolicyValue IMPLICIT_ACTIVATION = new ImplicitActivationPolicyValue(_IMPLICIT_ACTIVATION);
  public static final int _NO_IMPLICIT_ACTIVATION = 1;
  public static final ImplicitActivationPolicyValue NO_IMPLICIT_ACTIVATION = new ImplicitActivationPolicyValue(_NO_IMPLICIT_ACTIVATION);

  public int value ()
  {
    return __value;
  }

  public static ImplicitActivationPolicyValue from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected ImplicitActivationPolicyValue (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class ImplicitActivationPolicyValue
