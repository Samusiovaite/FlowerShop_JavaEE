package lt.vu.decorators;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/item")
@RequestScoped
public class ItemResource {

    @Inject
    private ItemService itemService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDecoratedItem(@QueryParam("value") Double doubleValue) {
        return "Decorated value: " + itemService.processItem(doubleValue);
    }
}