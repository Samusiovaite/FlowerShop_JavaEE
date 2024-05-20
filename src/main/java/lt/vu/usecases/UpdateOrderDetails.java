package lt.vu.usecases;


import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Product;
import lt.vu.entities.UserOrder;
import lt.vu.persistence.OrdersDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateOrderDetails implements Serializable {

    private UserOrder order;

    @Inject
    private OrdersDAO ordersDAO;

    @Inject
    private OrdersForUser ordersForUser;

    @PostConstruct
    private void init() {
        System.out.println("UpdatePlayerDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer orderId = Integer.parseInt(requestParameters.get("orderId"));
        this.order = ordersDAO.findOne(orderId);
    }

    @Transactional
    public String updateOrderPrice() {
        try{
            ordersDAO.update(this.order);
        } catch (OptimisticLockException e) {
            return "/orderDetails.xhtml?faces-redirect=true&orderId=" + this.order.getId() + "&error=optimistic-lock-exception";
        }
        return "orders.xhtml?userId=" + this.order.getUser().getId() + "&faces-redirect=true";
    }

    public Double calculateTotal(List<Product> products) {
        List<Double> pricesList = new ArrayList<>();
        for (Product product : products) {
            pricesList.add(product.getPrice());
        }

        Double sum = 0.0;
        for (Double price : pricesList) {
            sum += price;
        }

        return Double.parseDouble(String.format("%.2f", sum));
    }


}
