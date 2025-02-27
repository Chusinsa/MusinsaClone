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
    public ProductCreateResponse create (@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @RequestBody ProductCreateRequest productCreateRequest){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        return productService.save(admin, productCreateRequest);
    }

    @PutMapping("/products/{productId}")
    public ProductUpdateResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @PathVariable Long productId,
                                        @RequestBody ProductUpdateRequest productUpdateRequest){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        return productService.update(admin, productId, productUpdateRequest);
    }

    @DeleteMapping("/products/{productId}")
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                       @PathVariable Long productId){
        Admin admin = loginMemberResolver.resolveUserFromToken(token);
        productService.delete(admin, productId);
    }

    @GetMapping("/products")
    public List<ProductListResponse> findAll(){
        return productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public ProductCreateResponse findById(@PathVariable Long productId){
        return productService.findById(productId);
    }

}
