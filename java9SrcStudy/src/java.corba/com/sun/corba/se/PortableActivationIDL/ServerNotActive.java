package com.sun.corba.se.PortableActivationIDL;


/**
* com/sun/corba/se/PortableActivationIDL/ServerNotActive.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from /scratch/HUDSON/workspace/9-2-build-linux-amd64-phase2/jdk9/6725/corba/src/java.corba/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Thursday, August 3, 2017 3:57:51 AM UTC
*/

public final class ServerNotActive extends org.omg.CORBA.UserException
{
  public String serverId = null;

  public ServerNotActive ()
  {
    super(ServerNotActiveHelper.id());
  } // ctor

  public ServerNotActive (String _serverId)
  {
    super(ServerNotActiveHelper.id());
    serverId = _serverId;
  } // ctor


  public ServerNotActive (String $reason, String _serverId)
  {
    super(ServerNotActiveHelper.id() + "  " + $reason);
    serverId = _serverId;
  } // ctor

} // class ServerNotActive