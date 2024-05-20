package lt.vu.interceptors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/logged")
@RequestScoped
public class LoggedResource {
    @Inject
    private LoggedService loggedService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String callLoggedMethod(){
        return loggedService.performAction();
    }
}
