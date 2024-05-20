package lt.vu.usecases;

import lt.vu.services.ProductNumberGenerator;
import lt.vu.interceptors.LoggedInvocation;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@SessionScoped
@Named
public class GenerateProductNumber implements Serializable {
    @Inject
    ProductNumberGenerator productNumberGenerator;
    private CompletableFuture<String> productsNumberGenerationTask = null;

    @LoggedInvocation
    public String generateNewProductsNumber() {
        productsNumberGenerationTask = CompletableFuture.supplyAsync(() -> productNumberGenerator.generateProductsNumber());

        return "index.xhtml?faces-redirect=true";
    }

    public boolean isProductGenerationRunning() {
        return productsNumberGenerationTask != null && !productsNumberGenerationTask.isDone();
    }

    public String getProductsGenerationStatus() throws ExecutionException, InterruptedException {
        if (productsNumberGenerationTask == null) {
            return null;
        } else if (isProductGenerationRunning()) {
            return "Products generation in progress";
        }
        return "Suggested products barcode: " + productsNumberGenerationTask.get();
    }

}