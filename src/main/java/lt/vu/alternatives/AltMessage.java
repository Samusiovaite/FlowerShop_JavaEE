package lt.vu.alternatives;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;

@Dependent
@Alternative
public class AltMessage implements Message {

    @Override
    public String WriteMessage(){
        return "Alternative product created";
    }

    public AltMessage(){

    }


}
