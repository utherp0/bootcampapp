package org.uth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.*;
import java.io.*;

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
    String nextLayer = System.getenv("NEXTLAYER");

    // (Log)
    System.out.println( "ENV found: " + nextLayer );

    String ipInformation = null;

    try
    {
      InetAddress localAddress = InetAddress.getLocalHost();
      ipInformation = localAddress.toString();
    }
    catch( UnknownHostException exc )
    {
      ipInformation = "(Unknown Host Exception in App)"; 
    }
    catch( Exception exc )
    {
      ipInformation = "(Other exception in App: " + exc.toString() + ")";
    }

    // If there is no ENV variable, just return the current IP details
    if( nextLayer == null )
    {
      return ipInformation;
    }
    // Otherwise call on to the next layer and add that information to the return
    else
    {
      System.out.println( "Fetching " + nextLayer + "/endpoints/callLayers" );

      String targetURL = nextLayer + "/endpoints/callLayers";

      try
      {
        URL url = new URL( targetURL );
        HttpURLConnection getConnection = (HttpURLConnection)url.openConnection();

        getConnection.setRequestMethod( "GET" );
        getConnection.setRequestProperty( "Content-Type", "text/plain" );
        getConnection.setDoOutput(true);

        int responseCode = getConnection.getResponseCode();

        System.out.println( "Response: " + responseCode );

        // If it's a valid connection, pull the info
        if( responseCode == 200 )
        {
          BufferedReader in = new BufferedReader( new InputStreamReader( getConnection.getInputStream()));
          String inputLine = null;
          StringBuffer content = new StringBuffer();

          while(( inputLine = in.readLine()) != null )
          {
            content.append( inputLine );
          }

          in.close();
      
          System.out.println( "Received: " + content.toString() );

          ipInformation = ipInformation + " " + content.toString();
        } 
        else
        {
          ipInformation = ipInformation + " " + "(Unreachable " + targetURL + ")";
        }
      }
      catch( Exception exc )
      {
        ipInformation = ipInformation + " " + "(Exception occurred connecting to " + targetURL + " " + exc.toString() + ")";
      }

      return ipInformation;
    }
  }
}
