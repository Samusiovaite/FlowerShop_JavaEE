package lt.vu.persistence;

import lt.vu.entities.UserOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class OrdersDAO {

    @Inject
    private EntityManager em;

    public void persist(UserOrder order){
        this.em.persist(order);
    }

    public UserOrder findOne(Integer id){
        return em.find(UserOrder.class, id);
    }

    public UserOrder update(UserOrder order){
        return em.merge(order);
    }
}
