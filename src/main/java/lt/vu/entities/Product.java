package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Named;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "select p from Product as p")
})
@Table(name = "PRODUCT")
@Getter @Setter
public class Product {

    public Product(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    // EAGER - entity yra užkraunamas
    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private List<UserOrder> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }
}
