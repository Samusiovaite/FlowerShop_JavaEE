package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.alternatives.Message;
import lt.vu.decorators.ItemDecorator;
import lt.vu.entities.Product;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.ProductsDAO;
import lt.vu.qualifiers.*;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Model
public class ProductUpdate {
    @Inject
    private ProductsDAO productsDAO;

    @Inject @GeneralOrder
    OrderTypeProcessor orderTypeProcessor;

    @Inject @Any
    private OrderType orderType;

    @Getter @Setter
    private Product productToCreate = new Product();

    @Getter
    private List<Product> allProduct;

    @Getter
    private List<Product> vipOder;

    @Inject
    private Message message;

    @Inject
    private ItemDecorator itemDecorator;

    @PostConstruct
    public void init(){
        loadAllProducts();
    }

    @Transactional
    @LoggedInvocation
    public void createProduct(){
        this.productsDAO.persist(productToCreate);
        loadAllProducts();
        System.out.println(message.WriteMessage());
        System.out.println("Decorator implementation: " + itemDecorator.DecoratedDouble(productToCreate.getPrice()));
        System.out.println(orderTypeProcessor.OrderType());
        System.out.println(orderType.OrderType());
    }

    private void loadAllProducts(){
        this.allProduct = productsDAO.loadAll();
    }
    public void loadVipProduct(){
        List<Product> orders = productsDAO.loadAll();
        this.vipOder = orders.stream().filter(s->s.getName().toUpperCase().contains("VIP")).collect(Collectors.toList());
    }


}
