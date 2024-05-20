package lt.vu.decorators;

import javax.enterprise.context.Dependent;

@Dependent
public class ClassForDecorating implements ItemDecorator{

    @Override
    public Double DecoratedDouble(Double doubleValue){
        return doubleValue;
    }
}
