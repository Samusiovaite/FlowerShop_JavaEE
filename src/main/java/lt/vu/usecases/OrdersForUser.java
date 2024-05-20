package lt.vu.usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.criteria.Order;
import javax.transaction.Transactional;
import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.User;
import lt.vu.entities.Product;
import lt.vu.entities.UserOrder;
import lt.vu.persistence.OrdersDAO;
import lt.vu.persistence.ProductsDAO;
import lt.vu.persistence.UsersDAO;

import org.junit.Test;

@Model
public class OrdersForUser implements Serializable {

    @Inject
    private UsersDAO usersDAO;

    @Inject
    private ProductsDAO productsDAO;

    @Inject
    private OrdersDAO ordersDAO;

    @Getter @Setter
    private User user;

    @Getter @Setter
    private UserOrder orderToCreate = new UserOrder();

    @Getter @Setter
    private List<Integer> selectedProducts = new ArrayList<>();

    // Anotacija, kuri rodo, kad metodas turi būti iškviečiamas iš kart po to
    // kai yra sukurta klasė ir inicijuoti visi klasės laukai
    // - gaunami parametrai, kurie buvo perduoti per HTTP Užklausą, kurią
    // apdoroja Javaserver Faces JSF karkasas
    // gaunamas userId ir priskiriamas laukui user
    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer userId = Integer.parseInt(requestParameters.get("userId"));
        this.user = usersDAO.findOne(userId);
    }

    @Transactional
    public void createOrder() {
        List<Product> products = new ArrayList<>();
        for (Integer id : selectedProducts) {
            Product product = productsDAO.findOne(id);
            if (product != null) {
                products.add(product);
            }
        }
        Double price = pricesAmount(products);
        orderToCreate.setPrice(price);
        orderToCreate.setProducts(products);
        orderToCreate.setUser(user);
        orderToCreate.setOrderedAt(new Date());
        ordersDAO.persist(orderToCreate);
    }

    public Double pricesAmount(List<Product> products) {
        Double sum = 0.0;
        for (Product product : products) {
            sum += product.getPrice();
        }

        return Double.parseDouble(String.format("%.2f", sum));
    }



    public void foo(User user){
        System.out.println("iskviesta foo");
        String email = user.getOrders().get(0).getProducts().get(0).getOrders().get(0).getUser().getEmail();
//        String email = user.getOrders().get(0).getProducts().get(0).getName();
        System.out.println("jei pavyko tai bus gero zmogaus email");
        System.out.println(email);
    }

    @Test
    public void testFoo() {
        // Testuojamas objektas
        OrdersForUser ordersForUser = new OrdersForUser();


        User user = new User();
        user.setId(9);
        user.setName("liepa");
        user.setSurname("liepaitiene");
        user.setEmail("liepute@gmail.com");


        UserOrder order = new UserOrder();
        order.setId(12);
        order.setUser(user);

        Product product = new Product();
        product.setName("gvazdikėliai");
        product.setPrice(Double.valueOf("1.2"));


        List<Product> Produktai = new ArrayList<>();
        Produktai.add(product);

        order.setProducts(Produktai);

        List<UserOrder> uzsakymai  = new ArrayList<>();
        uzsakymai.add(order);

        user.setOrders(uzsakymai);
        product.setOrders(uzsakymai);

        order.setUser(user);
        ordersForUser.foo(user);
    }
}
