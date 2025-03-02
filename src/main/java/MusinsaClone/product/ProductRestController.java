package MusinsaClone.product;

import MusinsaClone.admin.Admin;

import MusinsaClone.product.dto.*;
import MusinsaClone.util.LoginAdminResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductService productService;
    private final LoginAdminResolver loginMemberResolver;

    public ProductRestController(ProductService productService, LoginAdminResolver loginMemberResolver) {
        this.productService = productService;
        this.loginMemberResolver = loginMemberResolver;
    }

    @PostMapping("/products")
    public ProductResponse create (@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                   @RequestBody ProductRequest productCreateRequest){
        Admin admin = loginMemberResolver.resolveAdminFromToken(token);
        return productService.save(admin, productCreateRequest);
    }

    @PutMapping("/products/{productId}")
    public ProductUpdateResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @PathVariable Long productId,
                                        @RequestBody ProductUpdateRequest productUpdateRequest
                                        ){
        Admin admin = loginMemberResolver.resolveAdminFromToken(token);
        return productService.update(admin, productId, productUpdateRequest);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteByProductId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                       @PathVariable Long productId){
        Admin admin = loginMemberResolver.resolveAdminFromToken(token);
        productService.deleteByProductId(admin, productId);
    }

    @DeleteMapping("/products/{productId}/{optionId}")
    public void deleteByOptionId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                       @PathVariable Long productId,
                       @PathVariable Long optionId){
        Admin admin = loginMemberResolver.resolveAdminFromToken(token);
        productService.deleteByOptionId(admin, productId, optionId);
    }

    @GetMapping("/products")
    public List<ProductListResponse> findAll(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Category category,
                                             @RequestParam(required = false) Condition condition,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return productService.findAll(name, category, condition, pageable);
    }

    @GetMapping("/products/{productId}")
    public ProductResponse findById(@PathVariable Long productId){
        return productService.findById(productId);
    }

    @PatchMapping("/products/{productId}")
    public void switchPublicPrivate(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                    @PathVariable Long productId){
        Admin admin = loginMemberResolver.resolveAdminFromToken(token);
        productService.switchPublicPrivate(admin,productId);
    }

}
