package kg.neo.mobimarket_2.service;

import kg.neo.mobimarket_2.dto.ProductFullDto;
import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.ProductSaveRequestDto;
import kg.neo.mobimarket_2.model.Product;
import org.springframework.context.annotation.Primary;

import java.util.List;

public interface ProductService {
    public Product saveProduct(ProductSaveRequestDto requestDto);
    public Product updateProduct(int productId, Product updatedProduct);


    public List<ProductListDto> findAllProducts();
//    public List<ProductDto> findAllUserProducts(int userId);
    public ProductFullDto findSingleProduct(int id);

    public Product getProductById(int id);

}
