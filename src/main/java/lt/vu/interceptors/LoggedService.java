package lt.vu.interceptors;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LoggedService {

    @LoggedInvocation
    public String performAction(){
        return "Action performed";
    }
}
