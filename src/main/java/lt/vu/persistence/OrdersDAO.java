package lt.vu.persistence;

import lt.vu.entities.Product;
import lt.vu.entities.UserOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

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
    @Transactional
    public List<UserOrder> findByProduct(Product product) {
        TypedQuery<UserOrder> query = em.createQuery(
                "SELECT o FROM UserOrder o JOIN o.products p WHERE p = :product", UserOrder.class);
        query.setParameter("product", product);
        return query.getResultList();
    }
    @Transactional
    public UserOrder save(UserOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        return em.merge(order);
    }

    @Transactional
    public List<UserOrder> findAll() {
        TypedQuery<UserOrder> query = em.createQuery("SELECT o FROM UserOrder o", UserOrder.class);
        return query.getResultList();
    }
}
