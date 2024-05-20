package lt.vu.alternatives;

import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/message")
@RequestScoped
public class MessageResource {

    @Inject
    private Message message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage() {
        return message.WriteMessage();
    }
}
