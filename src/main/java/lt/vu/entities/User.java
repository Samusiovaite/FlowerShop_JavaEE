package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Named;
import javax.persistence.*;
import javax.persistence.criteria.Order;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User as u")
})
@Table(name = "USER")
@Getter @Setter
public class User implements Serializable {
    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @Size(max = 50)
    @Column(name = "SURNAME")
    private String surname;

    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;


    //cascade nurodo, kad visi veiksmai turi vyti kartu su susietais objektais
    // orphanRemoval - nurodo, kad šalinant išsitrintu susieti objektai
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserOrder> orders = new ArrayList<>();

    public double getTotalOrders() {
        Double totalSum = 0.0;

        for(UserOrder order : orders) { // gauti vartotojo užsakymus
            totalSum += order.getPrice(); // sumuoti kiekvieno užsakymo sumą
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(totalSum));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email);
    }
}
