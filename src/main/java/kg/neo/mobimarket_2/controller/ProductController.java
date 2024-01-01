package kg.neo.mobimarket_2.controller;

import io.swagger.annotations.Api;
import kg.neo.mobimarket_2.dto.ProductFullDto;
import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.ProductSaveRequestDto;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.repository.ProductRepository;
import kg.neo.mobimarket_2.service.Impl.ProductServiceImpl;
import kg.neo.mobimarket_2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static kg.neo.mobimarket_2.configuration.SwaggerConfig.PRODUCT;

@Api(tags = PRODUCT)
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    private final ProductServiceImpl productServiceImpl;

    @PostMapping("/saveProduct")
    @Transactional
    public ResponseEntity<String> saveProduct(@RequestBody ProductSaveRequestDto requestDto) {
        productService.saveProduct(requestDto);
        return ResponseEntity.ok("Product saved successfully.");
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int product_id,
            @RequestBody Product updatedProduct
    ) {
        Product product = productService.getProductById(product_id);

        if (product == null) {
            System.out.println("product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@RequestParam int id){
        productRepository.deleteById(id);
    }
    @GetMapping("/findAllProducts")
    public List<ProductListDto> allProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/findSingleProduct")
    public ProductFullDto findSingleProduct(@PathVariable int id){
        return productService.findSingleProduct(id);
    }
    @PostMapping("/{productId}/like/increment")
    public ResponseEntity<String> incrementLikeCount(@PathVariable Integer product_id) {
        boolean success = productServiceImpl.incrementLikeCount(product_id);
        if (success) {
            return ResponseEntity.ok("Счетчик успешно увеличен");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт не найден");
        }
    }
    @PostMapping("/{productId}/like/decrement")
    public ResponseEntity<String> decrementLikeCount(@PathVariable Integer product_id) {
        boolean success = productServiceImpl.decrementLikeCount(product_id);
        if (success) {
            return ResponseEntity.ok("Счетчик успешно декрементирован");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт не найден или его количество уже равно 0");
        }
    }
}
