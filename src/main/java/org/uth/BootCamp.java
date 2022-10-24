package org.uth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/endpoints")
public class BootCamp 
{
  private boolean _ignoreState = false;

  @Path("health")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String health() 
  {
    if( !_ignoreState )
    {
      return "Health Placeholder";
    }

    return "";
  }

  @Path("setIgnoreState")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String setIgnoreState(@QueryParam("state") boolean state )
  {
    _ignoreState = state;

    return "setIgnoreState Placeholder " + state;
  }

  @Path("callLayers")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String callLayers()
  {
    return "callLayers Placeholder";
  }
}
