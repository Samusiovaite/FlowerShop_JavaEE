package lt.vu.persistence;

import lt.vu.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
}
