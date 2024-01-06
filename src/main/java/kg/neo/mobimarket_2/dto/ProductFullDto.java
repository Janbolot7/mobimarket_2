package kg.neo.mobimarket_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFullDto {
    private int product_id;
    private String image;
    private String productName;
    private int price;
    private String shortDescription;
    private String fullDescription;
    private int numberOfLikes;
    private List<String> images;

}
