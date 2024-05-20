package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class ProductNumberGenerator implements Serializable{

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BARCODE_LENGTH = 12;

    public String generateProductsNumber(){
        System.out.println("Pradedamas barkodo generavimas...");
        try {
            Thread.sleep(3000); // Simuliuojamas laiko sąnaudas
        } catch (InterruptedException e) {
            System.out.println("Thread buvo pertrauktas");
            Thread.currentThread().interrupt(); // Atkurti pertraukos būseną
        }
        StringBuilder barcode = new StringBuilder(BARCODE_LENGTH);
        Random random = new Random();

        for (int i = 0; i < BARCODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            barcode.append(CHARACTERS.charAt(index));
        }
        String generatedBarcode = barcode.toString();
        System.out.println("Sugeneruotas barkodas: " + generatedBarcode);
        return barcode.toString();
    }
}

