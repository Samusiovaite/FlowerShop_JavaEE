package lt.vu.persistence;

import lt.vu.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import java.util.List;

@ApplicationScoped
public class UsersDAO {

    @Inject
    private EntityManager em;

    public List<User> loadAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(User user){
        this.em.persist(user);
    }

    public User findOne(Integer id) {
        return em.find(User.class, id);
    }
    // Patikrinama, ar vartotojas yra priskirtas prie dabartines sesijos,
    // Jei ne, prijungiamas naudojant merge metodą, kad pakeitimai būtų
    // sinchronizuoti su DB. Ir tuomet pašalinamas vartotojas
    public void remove(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }




}
