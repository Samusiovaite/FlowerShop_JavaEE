package lt.vu.rest;

import lt.vu.entities.Product;
import lt.vu.entities.UserOrder;
import lt.vu.persistence.OrdersDAO;
import lt.vu.persistence.ProductsDAO;
import lt.vu.rest.contracts.ProductDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/products")
public class ProductsController {

    @Inject
    private ProductsDAO productsDAO;
    @Inject
    private OrdersDAO ordersDAO;

    @Path("/{id}")
    @GET
    // duomenys grąžins JSON formatu
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Product product = productsDAO.findOne(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setBarcode(product.getBarcode());
        List<Integer> orderIds = new ArrayList<>(); // Create a new empty list for order IDs
        if (product.getOrders() != null) { // Check if the orders list is not null
            for (UserOrder order : product.getOrders()) { // Loop through each order in the product
                orderIds.add(order.getId()); // Get the ID of each order and add it to the list
            }
        }
        productDto.setOrders(orderIds);

        return Response.ok(productDto).build();
    }

    @Path("/{id}")
    @PUT
    // tikimąsi gauti duomenis JSON formatu
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Integer productId, ProductDto productData) {
        try {
            Product existingProduct = productsDAO.findOne(productId);
            if (existingProduct == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            existingProduct.setName(productData.getName());
            existingProduct.setPrice(productData.getPrice());
            existingProduct.setBarcode(productData.getBarcode());

//        Assuming productData provides order IDs, handle ManyToMany relation
            updateProductOrders(existingProduct, productData.getOrders());
            existingProduct.setOrders(updateProductOrders(existingProduct, productData.getOrders()));

            productsDAO.update(existingProduct);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    private List<UserOrder> updateProductOrders(Product product, List<Integer> orderIds) {
        List<UserOrder> allOrdersWithProduct = ordersDAO.findByProduct(product); // Ieškome visų užsakymų su šiuo produktu
        List<UserOrder> allOrders = ordersDAO.findAll(); // Pridėti metodą gauti visiems užsakymams
        List<UserOrder> updatedOrders = new ArrayList<>();

        for (UserOrder order : allOrders) {
            if (orderIds != null && orderIds.contains(order.getId())) {
                if (!order.getProducts().contains(product)) {
                    order.addProduct(product); // Pridedame produktą, jei jo nėra užsakyme
                }
                updatedOrders.add(order); // Pridedame užsakymą prie atnaujintų užsakymų sąrašo
                ordersDAO.save(order); // Išsaugome atnaujintą užsakymą
            } else if (allOrdersWithProduct.contains(order) && (orderIds == null || !orderIds.contains(order.getId()))) {
                order.removeProduct(product); // Pašaliname produktą iš užsakymo, jei jis nėra orderIds sąraše
                ordersDAO.save(order); // Išsaugome atnaujintą užsakymą
            }
        }

        product.setOrders(updatedOrders); // Nustatome naujus užsakymus produktui
        return updatedOrders;
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response add(ProductDto productData) {
        Product newProduct = new Product();
        newProduct.setName(productData.getName());
        newProduct.setPrice(productData.getPrice());
        newProduct.setBarcode(productData.getBarcode());

        if (productData.getOrders() != null) {
            List<UserOrder> orders = new ArrayList<>(); // Create a new empty list for orders
            for (Integer id : productData.getOrders()) { // Loop through each order ID in productData
                UserOrder order = ordersDAO.findOne(id); // Find the order using the ID
                orders.add(order); // Add the found order to the list of orders
            }
            newProduct.setOrders(orders); // Set the collected orders to the new product
        }

        productsDAO.persist(newProduct);
        return Response.ok().build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String testEndpoint() {
        return "Test successful";
    }
}

