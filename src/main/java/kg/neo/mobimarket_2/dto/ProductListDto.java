package kg.neo.mobimarket_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDto {
    private int product_id;
    private byte[] image;
    private String productName;
    private int price;
    private int numberOfLikes;
}
