package org.omg.PortableServer;


/**
* org/omg/PortableServer/RequestProcessingPolicyValue.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /HUDSON/workspace/8-2-build-linux-amd64/jdk8u131/8869/corba/src/share/classes/org/omg/PortableServer/poa.idl
* Wednesday, March 15, 2017 1:24:19 AM PDT
*/


/**
	 * The RequestProcessingPolicyValue can have the following
	 * values.  USE_ACTIVE_OBJECT_MAP_ONLY - If the Object Id 
	 * is not found in the Active Object Map, 
	 * an OBJECT_NOT_EXIST exception is returned to the 
	 * client. The RETAIN policy is also required.
	 * USE_DEFAULT_SERVANT - If the Object Id is not found in 
	 * the Active Object Map or the NON_RETAIN policy is 
	 * present, and a default servant has been registered 
	 * with the POA using the set_servant operation, 
	 * the request is dispatched to the default servant. 
	 * USE_SERVANT_MANAGER - If the Object Id is not found 
	 * in the Active Object Map or the NON_RETAIN policy 
	 * is present, and a servant manager has been registered 
	 * with the POA using the set_servant_manager operation, 
	 * the servant manager is given the opportunity to 
	 * locate a servant or raise an exception. 
	 */
public class RequestProcessingPolicyValue implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static RequestProcessingPolicyValue[] __array = new RequestProcessingPolicyValue [__size];

  public static final int _USE_ACTIVE_OBJECT_MAP_ONLY = 0;
  public static final RequestProcessingPolicyValue USE_ACTIVE_OBJECT_MAP_ONLY = new RequestProcessingPolicyValue(_USE_ACTIVE_OBJECT_MAP_ONLY);
  public static final int _USE_DEFAULT_SERVANT = 1;
  public static final RequestProcessingPolicyValue USE_DEFAULT_SERVANT = new RequestProcessingPolicyValue(_USE_DEFAULT_SERVANT);
  public static final int _USE_SERVANT_MANAGER = 2;
  public static final RequestProcessingPolicyValue USE_SERVANT_MANAGER = new RequestProcessingPolicyValue(_USE_SERVANT_MANAGER);

  public int value ()
  {
    return __value;
  }

  public static RequestProcessingPolicyValue from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected RequestProcessingPolicyValue (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class RequestProcessingPolicyValue
