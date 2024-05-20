package lt.vu.qualifiers;

import javax.enterprise.inject.Specializes;

@Specializes
public class VipOrderType extends OrderType  {
    @Override
    public String OrderType(){
        return "Order type is VIP";
    }
}
