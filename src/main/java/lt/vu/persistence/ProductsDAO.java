package lt.vu.persistence;

import lt.vu.entities.Product;
import lt.vu.entities.User;
import lt.vu.entities.UserOrder;
import lt.vu.usecases.OrdersForUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

@ApplicationScoped
public class ProductsDAO {

    @Inject
    private EntityManager em;

    public List<Product> loadAll() {
        return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Product product){
        this.em.persist(product);
    }

    public void delete(Product product) {
        for (UserOrder order : product.getOrders()) {
            order.getProducts().remove(product);
            Double newAmount = pricesAmount(order.getProducts());
            order.setPrice(newAmount);
        }

        // Ištriname produktą
        if (!em.contains(product)) {
            product = em.merge(product);
        }
        em.remove(product);
    }

    private Double pricesAmount(List<Product> products) {
        // Skaičiuojame visų produktų kainų sumą
        double sum = 0.0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public Product findOne(Integer id) {
        return em.find(Product.class, id);
    }

    public Product update(Product product){
        return em.merge(product);
    }

    public List<Product> loadByIds(List<Integer> ids) {
        return em.createQuery("SELECT p FROM Product p WHERE p.id IN :ids", Product.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public List<Product> findByName(String name) {
        return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :name", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
