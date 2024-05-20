package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDto {
    private String Name;
    private Double Price;
    private String Barcode;
    private List<Integer> orders;
}