package lt.vu.qualifiers;

import javax.enterprise.context.Dependent;

@GeneralOrder
@Dependent
public class OrderType implements OrderTypeProcessor {

    @Override
    public String OrderType(){
        return "Order type is GENERAL";
    }
}
