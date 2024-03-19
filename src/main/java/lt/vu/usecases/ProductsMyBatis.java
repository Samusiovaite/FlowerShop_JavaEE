package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.model.Product;
import lt.vu.mybatis.dao.ProductMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class ProductsMyBatis {

    @Inject
    private ProductMapper productMapper;
    @Getter
    private List<Product> allProducts;
    @Getter @Setter
    private Product productToCreate = new Product();
    @PostConstruct
    public void init(){this.loadAllProducts();}

    private void loadAllProducts() {this.allProducts = productMapper.selectAll();}

    @Transactional
    public String createProduct() {
        productMapper.insert(productToCreate);
        return "/myBatis/users-products?faces-redirect=true";
    }

}
