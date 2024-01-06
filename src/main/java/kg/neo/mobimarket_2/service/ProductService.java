package kg.neo.mobimarket_2.service;

import kg.neo.mobimarket_2.dto.ProductFullDto;
import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.ProductSaveRequestDto;
import kg.neo.mobimarket_2.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public Product saveProduct(ProductSaveRequestDto requestDto, List<MultipartFile> imageFiles);
    public ResponseEntity<Product> updateProduct(Integer id, ProductFullDto product);
    ResponseEntity<Product> deleteProduct(Integer product_id, String publicId, ProductFullDto productFullDto);

    public List<ProductListDto> findAllProducts();
//    public List<ProductDto> findAllUserProducts(int userId);
//    public ProductFullDto findSingleProduct(int id);

    public Product getProductByIdd(int product_id);
//    public Product getProductById(int id);

    public boolean decrementLikeCount(Integer product_id);
    public boolean incrementLikeCount(Integer product_id);
}
