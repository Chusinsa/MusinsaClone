package MusinsaClone.product;

import MusinsaClone.DatabaseCleanup;
import MusinsaClone.admin.DTO.AdminCreate;
import MusinsaClone.admin.DTO.AdminCreateResponse;
import MusinsaClone.admin.DTO.AdminLogin;
import MusinsaClone.admin.DTO.AdminLoginResponse;
import MusinsaClone.product.dto.*;
import MusinsaClone.product.dto.ProductOptionRequest;
import MusinsaClone.util.JwtProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @Autowired
    JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

    // todo : 관리자 생성
    private AdminCreateResponse 관리자_생성(){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new AdminCreate(
                        "userId",
                        "chu",
                        "abc123!",
                        "010-1234-5678"))
                .when()
                .post("/admins")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AdminCreateResponse.class);
    }
    // todo : 관리자 로그인
    private AdminLoginResponse 관리자_로그인(){
        return RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new AdminLogin(
                        "userId",
                        "abc123!"))
                .when()
                .post("/logins/admins")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(AdminLoginResponse.class);
    }

    private ProductResponse 상품_생성(String token, ProductRequest request) {
        return RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
    }

    @Test
    void 옵션_없는_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductRequest 옵션없는상품 = new ProductRequest(
                "기본 티셔츠",
                20000,
                "심플한 기본 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                null
        );


        ProductResponse 상품 = 상품_생성(로그인.token(), 옵션없는상품);

        assertNotNull(상품);
        assertEquals("기본 티셔츠", 상품.productName());
        assertTrue(상품.productOption().isEmpty());
    }

    @Test
    void 옵션_1개_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductOptionRequest 옵션1 = new ProductOptionRequest("빨강", null, 10);
        ProductOptionRequest 옵션2 = new ProductOptionRequest("파랑", null, 5);

        ProductRequest 옵션1개상품 = new ProductRequest(
                "나이키 운동화",
                120000,
                "편안한 운동화",
                Category.SHOES,
                Condition.New,
                List.of(옵션1, 옵션2)
        );



        ProductResponse 상품 = 상품_생성(로그인.token(), 옵션1개상품);

        assertNotNull(상품);
        assertThat(상품.productName()).isEqualTo("나이키 운동화");
        assertThat(상품.productOption()).hasSize(2);

    }

    @Test
    void 옵션_2개_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductOptionRequest 옵션1 = new ProductOptionRequest("빨강", "230", 10);
        ProductOptionRequest 옵션2 = new ProductOptionRequest("파랑", "240", 5);

        ProductRequest 옵션2개상품 = new ProductRequest(
                "나이키 운동화",
                120000,
                "편안한 운동화",
                Category.SHOES,
                Condition.New,
                List.of(옵션1, 옵션2)
        );



        ProductResponse 상품 = 상품_생성(로그인.token(), 옵션2개상품);

        assertNotNull(상품);
        assertEquals("나이키 운동화", 상품.productName());
    }

    @Test
    void 상품수정_옵션x() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        String 수정전_상품이름 = "수정 전 상품";
        String 수정후_상품이름 = "수정 후 상품";

        ProductOptionRequest 옵션1 = new ProductOptionRequest("빨강", "230", 10);
        ProductOptionRequest 옵션2 = new ProductOptionRequest("파랑", "240", 5);

        ProductRequest 수정_전_상품 = new ProductRequest(
                수정전_상품이름,
                120000,
                "편안한 신발",
                Category.SHOES,
                Condition.New,
                List.of(옵션1, 옵션2)
        );

        ProductResponse 수정전_상품 = 상품_생성(로그인.token(), 수정_전_상품);

        ProductUpdateRequest 수정_후_상품 = new ProductUpdateRequest(
                수정후_상품이름,
                130000,
                "가벼운 신발",
                Condition.New,
                List.of(new ProductOptionRequest("검정", "250", 3))
        );

        ProductUpdateResponse 수정후_상품 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 수정전_상품.productId())
                .body(수정_후_상품)
                .when()
                .put("/products/{productId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProductUpdateResponse.class);

        assertThat(수정전_상품.productName()).isEqualTo(수정전_상품이름);
        assertThat(수정후_상품.productName()).isEqualTo(수정후_상품이름);
    }

    @Test
    void 상품수정_옵션변경() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductOptionRequest 수정전_옵션1 = new ProductOptionRequest("빨강", "230", 10);
        ProductOptionRequest 수정전_옵션2 = new ProductOptionRequest("파랑", "240", 5);

        ProductRequest 기존상품 = new ProductRequest(
                "수정전_상품이름",
                25000,
                "기존 상품 설명",
                Category.TOP,
                Condition.New,
                List.of(수정전_옵션1, 수정전_옵션2));

        ProductResponse 수정전_상품 = 상품_생성(로그인.token(), 기존상품);

        ProductUpdateRequest 수정할_상품 = new ProductUpdateRequest(
                "수정후_상품이름",
                27000,
                "수정된 상품 설명",
                Condition.New,
                List.of(new ProductOptionRequest("노랑", "240", 5)
                ));

        ProductUpdateResponse 수정후_상품 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 수정전_상품.productId())
                .body(수정할_상품)
                .when()
                .put("/products/{productId}")
                .then().log().all()
                .statusCode(200)  // 정상 응답 확인
                .extract()
                .as(ProductUpdateResponse.class);

        assertThat(수정전_상품.productOption()).hasSize(2);
        assertThat(수정후_상품.productOption()).hasSize(1);
    }

    @Test
    void 상품삭제() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductRequest 옵션1개상품 = new ProductRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(new ProductOptionRequest("빨강", "230", 10),
                        new ProductOptionRequest("파랑", "240", 5)));

        ProductResponse 상품 = 상품_생성(로그인.token(), 옵션1개상품);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 상품.productId())
                .when()
                .delete("/products/{productId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 옵션삭제() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductRequest 옵션1개상품 = new ProductRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(new ProductOptionRequest("빨강", "230", 10),
                        new ProductOptionRequest("파랑", "240", 5)));

        ProductResponse 상품 = 상품_생성(로그인.token(), 옵션1개상품);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 상품.productId())
                .pathParam("optionId", 상품.productOption().get(0).optionId())
                .when()
                .delete("/products/{productId}/{optionId}")
                .then().log().all()
                .statusCode(200);

    }

    @Test
    void 상품조회() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductRequest 티 = new ProductRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(new ProductOptionRequest("빨강", "240",10),
                        new ProductOptionRequest("파랑", "240",15),
                        new ProductOptionRequest("검정", "240",20)));

        ProductRequest 신발 = new ProductRequest(
                "신발",
                25000,
                "편안한신발.",
                Category.SHOES,
                Condition.New,
                List.of(
                        new ProductOptionRequest("빨강", "240",10),
                        new ProductOptionRequest("파랑", "240",15),
                        new ProductOptionRequest("검정", "240",20)));

        ProductRequest 옷 = new ProductRequest(
                "이쁜 옷",
                25000,
                "여기저기 이쁜 옷.",
                Category.TOP,
                Condition.New,
                List.of(
                        new ProductOptionRequest("빨강", "240",10),
                        new ProductOptionRequest("파랑", "240",15),
                        new ProductOptionRequest("검정", "240",20)));

        ProductResponse 상품1 = 상품_생성(로그인.token(), 티);
        ProductResponse 상품2 = 상품_생성(로그인.token(), 신발);
        ProductResponse 상품3 = 상품_생성(로그인.token(), 옷);

        List<ProductListResponse> 상품들 = RestAssured
                .given().log().all()
                .when()
                .get("/products")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", ProductListResponse.class);

        assertThat(상품들.size()).isEqualTo(3);
    }

    @Test
    void 특정상품조회() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductRequest 티 = new ProductRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(
                        new ProductOptionRequest("빨강", "240",10),
                        new ProductOptionRequest("파랑", "240",15),
                        new ProductOptionRequest("검정", "240",20)));
        ProductResponse 상품1 = 상품_생성(로그인.token(), 티);

        ProductResponse 특정상품 = RestAssured
                .given().log().all()
                .pathParam("productId", 상품1.productId())
                .when()
                .get("/products/{productId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
    }
}
