package MusinsaClone.product;

import MusinsaClone.admin.Admin;

import MusinsaClone.product.dto.*;
import MusinsaClone.util.LoginMemberResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductService productService;
    private final LoginMemberResolver loginMemberResolver;

    public ProductRestController(ProductService productService, LoginMemberResolver loginMemberResolver) {
        this.productService = productService;
        this.loginMemberResolver = loginMemberResolver;
    }

    @PostMapping("/products")
    public ProductResponse create (@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                   @RequestBody ProductRequest productCreateRequest){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        return productService.save(admin, productCreateRequest);
    }

    @PutMapping("/products/{productId}")
    public ProductUpdateResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @PathVariable Long productId,
                                        @RequestBody ProductUpdateRequest productUpdateRequest
                                        ){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        return productService.update(admin, productId, productUpdateRequest);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteByProductId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                       @PathVariable Long productId){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        productService.deleteByProductId(admin, productId);
    }

    @DeleteMapping("/products/{productId}/{optionId}")
    public void deleteByOptionId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                       @PathVariable Long productId,
                       @PathVariable Long optionId){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        productService.deleteByOptionId(admin, productId, optionId);
    }

    @GetMapping("/products")
    public List<ProductListResponse> findAll(){
        return productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public ProductResponse findById(@PathVariable Long productId){
        return productService.findById(productId);
    }

}
