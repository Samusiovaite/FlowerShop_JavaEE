package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Product;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.ProductsDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateProductDetail implements Serializable {

    private Product product;

    @Inject
    private ProductsDAO productsDAO;

    @PostConstruct
    private void init() {
        System.out.println("UpdateProductDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer productId = Integer.parseInt(requestParameters.get("productId"));
        this.product = productsDAO.findOne(productId);
    }

    @Transactional
    @LoggedInvocation
    public String updateProduct() {
        try {
            productsDAO.update(this.product);
        } catch (OptimisticLockException e) {
            System.out.println("Optimistic Lock Exception:)");
            return "/optimisticUpdate.xhtml?faces-redirect=true&productId=" + this.product.getId() + "&error=optimistic-lock-exception";
        }
        return "index.xhtml?productId=" + this.product.getId() + "&faces-redirect=true";
    }
}
