package MusinsaClone.product;

import MusinsaClone.admin.Admin;
import MusinsaClone.admin.AdminRepository;
import MusinsaClone.product.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionSubRepository productOptionSubRepository;


    public ProductService(ProductRepository productRepository, AdminRepository adminRepository, ProductOptionRepository productOptionRepository, ProductOptionSubRepository productOptionSubRepository) {
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
        this.productOptionRepository = productOptionRepository;
        this.productOptionSubRepository = productOptionSubRepository;
    }

    @Transactional
    public ProductCreateResponse save(Admin admin, ProductCreateRequest productCreateRequest) {
        adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("관리자가 아닙니다."));

        Product product = productRepository.save(new Product(
                productCreateRequest.productName(),
                productCreateRequest.price(),
                productCreateRequest.description(),
                productCreateRequest.category(),
                productCreateRequest.productCondition()
        ));

        //상품에 새로운 옵션을 추가
        if(productCreateRequest.options() != null){
            for (OptionGroup optionRequest : productCreateRequest.options()) {
                saveProductOption(product, optionRequest, null);
            }
        }
        return convertToProductCreateResponse(product);
}

    private void saveProductOption(Product product, OptionGroup optionGroup, ProductOption parentOption) {
        ProductOption productOption = new ProductOption(optionGroup.optionName(), product);

        if (parentOption != null) {
            parentOption.addSubOption(productOption);  // 서브옵션
        } else {
            product.addOption(productOption);  // 옵션
        }

        productOptionRepository.save(productOption);

        // 1) 옵션 값 저장 (optionValue & stock 포함)
        if (optionGroup.optionValues() != null) {
            for (OptionValue subOption : optionGroup.optionValues()) {
                ProductOptionSub optionSub = new ProductOptionSub(subOption.optionValue(), subOption.stock());
                productOption.addOptionValue(optionSub);
                productOptionSubRepository.save(optionSub);
            }
        }

        // 2) 하위 옵션 저장 (재귀 호출)
        if (optionGroup.subOptions() != null) {
            for (OptionGroup subOptionRequest : optionGroup.subOptions()) {
                saveProductOption(product, subOptionRequest, productOption);
            }
        }
    }
    private ProductCreateResponse convertToProductCreateResponse(Product product) {
        return new ProductCreateResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getProductCondition(),
                product.getOptions().stream()
                        .map(productOption -> new OptionGroup(
                                productOption.getOptionName(),
                                convertToOptionValue(productOption.getOptionValues()),
                                convertToOptionGroup(productOption.getSubOptions())))
                        .toList(),
                product.getCreatedAt());
    }

    private List<OptionValue> convertToOptionValue(List<ProductOptionSub> optionValues) {
        return optionValues.stream()
                .map(value -> new OptionValue(
                        value.getOptionValue(),
                        value.getStock()
                )).toList();
    }

    private List<OptionGroup> convertToOptionGroup(List<ProductOption> subOptions) {
        return subOptions.stream()
                .map(sub -> new OptionGroup(
                        sub.getOptionName(),
                        convertToOptionValue(sub.getOptionValues()),
                        convertToOptionGroup(sub.getSubOptions())))
                .toList();
    }

    @Transactional
    public ProductUpdateResponse update(Admin admin, Long productId, ProductUpdateRequest productUpdateRequest) {
        adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자가 아닙니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));

        /*List<ProductOption> productOptions = productUpdateRequest.options()
                .stream()
                .map(option -> new ProductOption(
                        option.optionName(),
                        product
                )).toList();
관계 설정은 그냥 설정인거고, ㄱ실제 오브젝트 참조 관계를 맺어줘야지
        productOptions*/

        // 새 옵션 생성
        List<ProductOption> productOptions = new ArrayList<>();

        // 옵션그룹과 옵션값 처리 (한 번에 처리)
        for (OptionGroup optionGroup : productUpdateRequest.options()) {
            ProductOption option = new ProductOption(optionGroup.optionName(), product);
            ProductOption savedOption = productOptionRepository.save(option);
            productOptions.add(savedOption);

            if (optionGroup.optionValues() != null && !optionGroup.optionValues().isEmpty()) {
                List<ProductOptionSub> optionSubs = optionGroup.optionValues().stream()
                        .map(value -> new ProductOptionSub(
                                value.optionValue(),
                                value.stock(),
                                savedOption))
                        .toList();

                productOptionSubRepository.saveAll(optionSubs);
            }
        }

        product.update(
                productUpdateRequest.productName(),
                productUpdateRequest.price(),
                productUpdateRequest.description(),
                productUpdateRequest.productCondition(),
                productOptions
        );

        return new ProductUpdateResponse(
                productId, product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProductCondition(),
                convertToOptionGroup(product.getOptions()),
                product.getUpdatedAt());

    }

    @Transactional
    public void delete(Admin admin, Long productId) {
        adminRepository.findById(admin.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자가 아닙니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));

        product.setDeleted();
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

    public ProductCreateResponse findById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));

        return convertToProductCreateResponse(product);
    }
}
