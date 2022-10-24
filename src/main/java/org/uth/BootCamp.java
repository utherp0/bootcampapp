package org.uth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.*;

@Path("/endpoints")
public class BootCamp 
{
  private boolean _ignoreState = false;
  private long _start = System.currentTimeMillis();

  @Path("health")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String health() 
  {
    if( !_ignoreState )
    {
      long elapsed = System.currentTimeMillis() - _start;

      long diff = Math.round( elapsed / 1000 );

      return "Elapsed " + diff + " seconds";
    }

    return "";
  }

  @Path("setIgnoreState")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String setIgnoreState(@QueryParam("state") boolean state )
  {
    _ignoreState = state;

    return "Ignore state set to " + state;
  }

  @Path("callLayers")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String callLayers()
  {
    // Requires an ENV variable for nextLayer (NEXTLAYER)
    String nextLayer = System.getEnv("NEXTLAYER");

    // (Log)
    System.out.println( "ENV found: " + nextLayer );

    try
    {
      InetAddress localAddress = InetAddress.getLocalHost();
      
      return localAddress.toString();
    }
    catch( Exception exc )
    {
      return "Unknown Host Exception";
    }
  }
}
