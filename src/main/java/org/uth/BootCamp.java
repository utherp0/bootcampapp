package org.uth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/endpoints")
public class BootCamp 
{
  @Path("health")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String health() 
  {
    return "Health Placeholder";
  }

  @Path("setIgnoreState")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String setIgnoreState(@QueryParam("state") boolean state )
  {
    return "setIgnoreState Placeholder";
  }

  @Path("callLayers")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String callLayers()
  {
    return "callLayers Placeholder";
  }
}
