package com.example.repro;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = "redirect-client")
public interface RedirectClient {

    @GET
    @Path("/redirect")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response getThroughRedirect();
}
