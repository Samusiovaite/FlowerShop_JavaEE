package lt.vu.qualifiers;

import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
@RequestScoped
public class OrderResource {

    @Inject
    @GeneralOrder
    private OrderTypeProcessor vipOrderProcessor;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getOrderType() {
        return vipOrderProcessor.OrderType();
    }
}
