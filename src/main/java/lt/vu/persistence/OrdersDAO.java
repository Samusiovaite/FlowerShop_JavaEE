package lt.vu.persistence;

import lt.vu.entities.UserOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
// Klase, kuri atsakinga už komunikaciją su DB
@ApplicationScoped
public class OrdersDAO {
    // anotacija naudojama įteprti EntityManager objektą į
    // ProductDAO klasę. EntityManager yra JPA sąsaja, skirta
    // valdyti CRUD operacijas su entitetais duomenų bazėje.
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
