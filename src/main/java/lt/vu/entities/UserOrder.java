package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER_ORDER")
@Getter @Setter
public class UserOrder implements Serializable {

    public UserOrder(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "ORDERED_AT")
    private Date orderedAt;

    @ManyToMany
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    public void removeProduct(Product product) {
        if (product != null && products.contains(product)) {
            products.remove(product);
        }
    }

    public void addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrder order = (UserOrder) o;
        return Objects.equals(id, order.id);
    }
}
