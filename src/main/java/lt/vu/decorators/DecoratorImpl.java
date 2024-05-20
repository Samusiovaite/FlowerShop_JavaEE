package lt.vu.decorators;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class DecoratorImpl implements ItemDecorator{
    @Inject @Delegate @Any
    ItemDecorator itemDecorator;

    public Double DecoratedDouble(Double doubleValue){
        System.out.println("Decorator is used");
        return itemDecorator.DecoratedDouble(doubleValue) * 1.21;
    }


}
