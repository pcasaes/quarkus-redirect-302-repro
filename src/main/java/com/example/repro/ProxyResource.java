package com.example.repro;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/exercise")
public class ProxyResource {

    @Inject
    @RestClient
    RedirectClient redirectClient;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String exercise() {
        try (Response response = redirectClient.getThroughRedirect()) {
            return response.readEntity(String.class);
        }
    }
}
