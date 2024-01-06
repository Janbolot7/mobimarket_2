package kg.neo.mobimarket_2.service.Impl;

import kg.neo.mobimarket_2.dto.ProductFullDto;
import kg.neo.mobimarket_2.dto.ProductListDto;
import kg.neo.mobimarket_2.dto.ProductSaveRequestDto;
import kg.neo.mobimarket_2.exceptions.VerificationException;
import kg.neo.mobimarket_2.model.Image;
import kg.neo.mobimarket_2.model.Product;
import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.repository.ProductRepository;
import kg.neo.mobimarket_2.service.CloudinaryService;
import kg.neo.mobimarket_2.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
    }

    //    @Override
//    @Transactional
//    public Product saveProduct(ProductSaveRequestDto requestDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//
//        if (!user.getVerified()) {
//            System.out.println("User is not verified ");
//            throw new VerificationException("User is not verified. To add a new product, you have to verify your phone number before.");
//        }
//
//        Product product = new Product();
//        String fileName = StringUtils.cleanPath(requestDto.getImage().getOriginalFilename());
//
//        if (fileName.contains("..")) {
//            System.out.println("Not a valid file");
//        }
//
////        try {
////            product.setImage(Base64.getEncoder().encodeToString(requestDto.getImage().getBytes()));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        try {
//            byte[] imageBytes = requestDto.getImage().getBytes();
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//            product.setImage(base64Image.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        product.setShortDescription(requestDto.getShortDescription());
//        product.setFullDescription(requestDto.getFullDescription());
//        product.setProductName(requestDto.getProductName());
//        product.setPrice(requestDto.getPrice());
//        product.setUser(user);
//
//        return productRepository.save(product);
//    }
    @Override
    @Transactional
    public Product saveProduct(ProductSaveRequestDto requestDto, List<MultipartFile> imageFiles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (!user.getVerified()) {
            System.out.println("User is not verified ");
            throw new VerificationException("User is not verified. To add a new product, you have to verify your phone number before.");
        }
        Product product = new Product();
        List<Image> images = new ArrayList<>();

        for (MultipartFile file : imageFiles) {
            String imageUrl = cloudinaryService.uploadImage(file);
            String publicId = cloudinaryService.extractPublicId(imageUrl);
            Image productImage = new Image();
            productImage.setPublicId(publicId);
            productImage.setUrl(imageUrl);
            productImage.setProduct(product);
            images.add(productImage);
        }
        product.setShortDescription(requestDto.getShortDescription());
        product.setFullDescription(requestDto.getFullDescription());
        product.setProductName(requestDto.getProductName());
        product.setPrice(requestDto.getPrice());
        product.setUser(user);

        product.setImages(images);
//    productRepository.save(product);

        return productRepository.save(product);
    }
    @Override
    public ResponseEntity<Product> deleteProduct(Integer product_id, String publicId, ProductFullDto productFullDto) {
        try {
            Product product = productRepository.findById(product_id)
                    .orElseThrow(() -> new IllegalStateException("Product with id " + product_id + " not found!"));

            List<Image> images = product.getImages();

            Optional<Image> imageToRemove = images.stream()
                    .filter(image -> image.getPublicId().equals(publicId))
                    .findFirst();

            if (imageToRemove.isPresent()) {
                String deletedPublicId = imageToRemove.get().getPublicId();
                cloudinaryService.deleteProductImage(deletedPublicId);
                images.remove(imageToRemove.get());
                product.setShortDescription(null);
                product.setFullDescription(null);
                product.setProductName(null);
                product.setPrice(0);
                product.setUser(null);
                productRepository.save(product);
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductByIdd(int product_id) {
        return productRepository.findById(product_id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + product_id));
    }

//    @Override
//    public Product getProductById(int id) {
//        return null;
//    }

    @Override
    public ResponseEntity<Product> updateProduct(Integer id, ProductFullDto product) {
        try {
            Product productInDB = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("Product with id " + id + " does not exist!"));

            productInDB.setProductName(product.getProductName());
            productInDB.setShortDescription(product.getShortDescription());
            productInDB.setFullDescription(product.getFullDescription());
            productInDB.setPrice(product.getPrice());

            List<Image> images = new ArrayList<>();
            if (product.getImages() != null) {
                for (String imageUrl : product.getImages()) {
                    Image image = new Image();
                    image.setUrl(imageUrl);
                    images.add(image);
                }
            }
            productInDB.setImages(images);

            productRepository.save(productInDB);
            System.out.println("Product successfully updated!");
            return ResponseEntity.ok(productInDB);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public List<ProductListDto> findAllProducts() {
        List<Product> productList = productRepository.findAll();

        List<ProductListDto> productListDtos = new ArrayList<>();
        for (Product product : productList) {
            ProductListDto productListDto = new ProductListDto();
            productListDto.setProduct_id(product.getProduct_id());
            productListDto.setProductName(product.getProductName());
            productListDto.setPrice(product.getPrice());
            productListDto.setNumberOfLikes(product.getNumberOfLikes());
            productListDtos.add(productListDto);
        }
        return productListDtos;
    }


//    @Override
//    public ProductFullDto findSingleProduct(int id) {
//        Optional<Product> productOptional = productRepository.findById(id);
//        if (productOptional.isPresent()) {
//            Product product = productOptional.get();
//            return new ProductFullDto(
//                    product.getProduct_id(),
//                    product.getImage(),
//                    product.getProductName(),
//                    product.getPrice(),
//                    product.getShortDescription(),
//                    product.getFullDescription(),
//                    product.getNumberOfLikes()
//            );
//        }
//
//        throw new RuntimeException("ProductDto is not find " + id);
//    }

    public boolean incrementLikeCount(Integer product_id) {
        Optional<Product> productOptional = productRepository.findById(product_id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.incrementLikeCount(); // Вызываем метод инкремента количества лайков в классе Product
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public boolean decrementLikeCount(Integer product_id) {
        Optional<Product> productOptional = productRepository.findById(product_id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.decrementLikeCount(); // Вызываем метод декремента количества лайков в классе Product
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
