package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Product;
import lt.vu.persistence.ProductsDAO;
import lt.vu.services.ProductNumberGenerator;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Products {

    @Inject
    private ProductsDAO productsDAO;

    @Inject
    private ProductNumberGenerator productNumberGenerator;

    @Getter @Setter
    private Product productToCreate = new Product();

    @Getter @Setter
    private Product productToUpdate = new Product();

    @Getter
    private List<Product> allProducts;

    @PostConstruct
    public void init(){
        loadAllProducts();
    }

    @Transactional
    public void createProduct(){
        this.productsDAO.persist(productToCreate);
        loadAllProducts();
    }

    @Transactional
    public void deleteProduct(Product product){
        this.productsDAO.delete(product);
        loadAllProducts(); // atnaujiname produktų sąrašą po produkto ištrynimo
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produktas sėkmingai ištrintas"));
    }

    @Transactional
    public void updateProduct(){
        this.productsDAO.update(productToUpdate);
        loadAllProducts();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produktas sėkmingai atnaujintas"));
    }

    public void findProductById(Integer id){
        this.productToUpdate = productsDAO.findOne(id);
    }

    public void generateBarcode(){
        String generatedBarcode = productNumberGenerator.generateProductsNumber();
        productToCreate.setBarcode(generatedBarcode.toString());
    }

    private void loadAllProducts(){
        this.allProducts = productsDAO.loadAll();
    }
}
