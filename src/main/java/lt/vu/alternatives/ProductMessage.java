package lt.vu.alternatives;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.Dependent;

@Dependent
public class ProductMessage implements Message {

    @Override
    public String WriteMessage() {
        return "Product created";
    }

    public ProductMessage() {
    }


}
