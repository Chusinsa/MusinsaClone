package MusinsaClone.product;

import MusinsaClone.admin.Admin;
import MusinsaClone.admin.AdminRepository;
import MusinsaClone.product.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final ProductOptionRepository productOptionRepository;


    public ProductService(ProductRepository productRepository, AdminRepository adminRepository, ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
        this.productOptionRepository = productOptionRepository;
    }


    @Transactional
    public ProductResponse save(Admin admin, ProductRequest productCreateRequest) {
        Admin admin1 = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("관리자가 아닙니다."));

        Product product = productRepository.save(new Product(
                productCreateRequest.productName(),
                productCreateRequest.price(),
                productCreateRequest.description(),
                productCreateRequest.category(),
                productCreateRequest.productCondition()
        ));


        if(productCreateRequest.productOption() != null){
            List<ProductOption> productOptions = productCreateRequest.productOption().stream()
                    .map(optionRequest -> productOptionRepository.save(new ProductOption(
                            optionRequest.color(),
                            optionRequest.size(),
                            optionRequest.stock(),
                            product)))
                    .toList();

            product.addOptions(productOptions);
        }

        List<ProductOptionResponse> optionResponses = product.getProductOptions()
                .stream()
                .map(option -> new ProductOptionResponse(
                        option.getId(),
                        option.getColor(),
                        option.getSize(),
                        option.getStock()
                )).toList();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getProductCondition(),
                optionResponses,
                LocalDateTime.now()
        );
    }

    @Transactional
    public ProductUpdateResponse update(Admin admin, Long productId, ProductUpdateRequest productUpdateRequest) {
        adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자가 아닙니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));

        List<ProductOption> productOptions = productUpdateRequest.productOptions()
                .stream()
                .map(option -> new ProductOption(
                        option.color(),
                        option.size(),
                        option.stock(),
                        product)).toList();

        product.update(
                productUpdateRequest.productName(),
                productUpdateRequest.price(),
                productUpdateRequest.description(),
                productUpdateRequest.productCondition(),
                productOptions
        );

        List<ProductOptionResponse> optionResponses = product.getProductOptions()
                .stream()
                .map(option -> new ProductOptionResponse(
                        option.getId(),
                        option.getColor(),
                        option.getSize(),
                        option.getStock()
                )).toList();

        return new ProductUpdateResponse(
                productId, product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProductCondition(),
                optionResponses,
                product.getUpdatedAt());
    }

    @Transactional
    public void deleteByProductId(Admin admin, Long productId) {
        adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자가 아닙니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));

        product.setDeleted();
    }

    @Transactional
    public void deleteByOptionId(Admin admin, Long productId, Long optionId) {
        adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자가 아닙니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));

        ProductOption productOption = productOptionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("옵션이 없습니다."));

        productOption.setDeleted();
    }


    public List<ProductListResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product ->
                        new ProductListResponse(
                                product.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getCategory(),
                                product.getProductCondition()
                        ))
                .toList();
    }

    public ProductResponse findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));

        List<ProductOptionResponse> optionResponses = product.getProductOptions()
                .stream()
                .map(option -> new ProductOptionResponse(
                        option.getId(),
                        option.getColor(),
                        option.getSize(),
                        option.getStock()
                )).toList();

        return new ProductResponse(
                productId,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getProductCondition(),
                optionResponses,
                product.getCreatedAt());
    }

    private List<ProductOptionResponse> convertRequestToResponse(Product product){
        return product.getProductOptions()
                .stream()
                .map(option -> new ProductOptionResponse(
                        option.getId(),
                        option.getColor(),
                        option.getSize(),
                        option.getStock()
                )).toList();
    }


}
