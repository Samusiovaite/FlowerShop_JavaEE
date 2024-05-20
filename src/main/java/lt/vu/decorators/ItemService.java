package lt.vu.decorators;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ItemService {

    @Inject
    private ItemDecorator itemDecorator;

    public Double processItem(Double doubleValue) {
        return itemDecorator.DecoratedDouble(doubleValue);
    }
}
