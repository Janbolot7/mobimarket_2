package kg.neo.mobimarket_2.controller;

import io.swagger.annotations.Api;
import kg.neo.mobimarket_2.dto.ProductFullDto;
import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.ProductSaveRequestDto;
import kg.neo.mobimarket_2.exceptions.VerificationException;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.service.Impl.ProductServiceImpl;
import kg.neo.mobimarket_2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static kg.neo.mobimarket_2.configuration.SwaggerConfig.PRODUCT;

@Api(tags = PRODUCT)
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
//    private final ProductRepository productRepository;


    //    @PostMapping("/saveProduct")
//    @Transactional
//    public ResponseEntity<String> saveProduct(@RequestBody ProductSaveRequestDto requestDto,
//                                              @RequestParam("file") List<MultipartFile> file) {
//        productService.saveProduct(requestDto);
//        return ResponseEntity.ok("Product saved successfully.");
//    }
    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductSaveRequestDto requestDto, @RequestParam("files") List<MultipartFile> imageFiles) {
        try {
            Product savedProduct = productService.saveProduct(requestDto, imageFiles);
            return ResponseEntity.ok(savedProduct);
        } catch (VerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id,
                                                 @RequestBody ProductFullDto product) {
        try {
            ResponseEntity<Product> updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //    @DeleteMapping("/deleteProduct/{id}")
//    public void deleteProduct(@RequestParam int id){
//        productRepository.deleteById(id);
//    }
    @DeleteMapping("/deleteProduct/{product_id}")
    public ResponseEntity<Product> deleteProduct(
            @PathVariable("productId") Integer productId,
            @RequestParam("publicId") String publicId,
            @RequestBody ProductFullDto productFullDto) {

        return productService.deleteProduct(productId, publicId, productFullDto);
    }

    @GetMapping("/getProductByIdd{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable int productId) {
        try {
            Product product = productService.getProductByIdd(productId);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAllProducts")
    public List<ProductListDto> allProducts() {
        return productService.findAllProducts();
    }

//    @GetMapping("/findSingleProduct")
//    public ProductFullDto findSingleProduct(@PathVariable int id) {
//        return productService.findSingleProduct(id);
//    }

    @PostMapping("/{productId}/like/increment")
    public ResponseEntity<String> incrementLikeCount(@PathVariable Integer product_id) {
        boolean success = productService.incrementLikeCount(product_id);
        if (success) {
            return ResponseEntity.ok("Счетчик успешно увеличен");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт не найден");
        }
    }

    @PostMapping("/{productId}/like/decrement")
    public ResponseEntity<String> decrementLikeCount(@PathVariable Integer product_id) {
        boolean success = productService.decrementLikeCount(product_id);
        if (success) {
            return ResponseEntity.ok("Счетчик успешно декрементирован");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Продукт не найден или его количество уже равно 0");
        }
    }
}
