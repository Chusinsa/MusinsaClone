package MusinsaClone.product;

import MusinsaClone.DatabaseCleanup;
import MusinsaClone.admin.DTO.AdminCreate;
import MusinsaClone.admin.DTO.AdminCreateResponse;
import MusinsaClone.admin.DTO.AdminLogin;
import MusinsaClone.admin.DTO.AdminLoginResponse;
import MusinsaClone.product.dto.*;
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

    private ProductCreateResponse 상품_생성(String token, ProductCreateRequest request) {
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
                .as(ProductCreateResponse.class);
    }

    @Test
    void 옵션_없는_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductCreateRequest 옵션없는상품 = new ProductCreateRequest(
                "기본 티셔츠",
                20000,
                "심플한 기본 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                null
        );

        ProductCreateResponse 상품 = 상품_생성(로그인.token(), 옵션없는상품);

        assertNotNull(상품);
        assertEquals("기본 티셔츠", 상품.productName());
        assertTrue(상품.options().isEmpty());
    }

    @Test
    void 옵션_1개_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductCreateRequest 옵션1개상품 = new ProductCreateRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );


        ProductCreateResponse 상품 = 상품_생성(로그인.token(), 옵션1개상품);

        assertNotNull(상품);
        assertThat(상품.productName()).isEqualTo("컬러 티셔츠");
        assertThat(상품.options().size()).isEqualTo(1);
        assertThat(상품.options().get(0).optionValues().size()).isEqualTo(3);

    }

    @Test
    void 옵션_2개_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductCreateRequest 옵션2개상품 = new ProductCreateRequest(
                "편안한 신발",
                50000,
                "편안한 신발입니다.",
                Category.SHOES,
                Condition.New,
                List.of(
                        new OptionGroup("색상",
                                List.of(
                                        new OptionValue("빨강", null),
                                        new OptionValue("검정", null)
                                ),
                                List.of(
                                        new OptionGroup("빨강",
                                                List.of(
                                                        new OptionValue("230", 3),
                                                        new OptionValue("240", 5),
                                                        new OptionValue("250", 7)
                                                ),
                                                null
                                        ),
                                        new OptionGroup("검정",
                                                List.of(
                                                        new OptionValue("230", 4),
                                                        new OptionValue("240", 6),
                                                        new OptionValue("250", 8)
                                                ),
                                                null
                                        )
                                )
                        )
                )
        );

        ProductCreateResponse 상품 = 상품_생성(로그인.token(), 옵션2개상품);

        assertNotNull(상품);
        assertEquals("편안한 신발", 상품.productName());
        assertThat(상품.options().get(0).optionValues().size()).isEqualTo(2);
    }

    @Test
    void 옵션_3개_상품_생성() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductCreateRequest 옵션3개상품 = new ProductCreateRequest(
                "패턴 운동화",
                70000,
                "다양한 무늬, 색상, 사이즈의 패턴 운동화입니다.",
                Category.SHOES,
                Condition.New,
                List.of(
                        new OptionGroup("무늬", List.of(
                                        new OptionValue("체크무늬", null),
                                        new OptionValue("줄무늬", null)),
                                List.of(
                                        new OptionGroup("체크무늬", List.of(), List.of(
                                                new OptionGroup("빨강", List.of(
                                                        new OptionValue("230", 3),
                                                        new OptionValue("240", 5),
                                                        new OptionValue("250", 7)), null
                                                        ),
                                                new OptionGroup("파랑", List.of(
                                                        new OptionValue("230", 4),
                                                        new OptionValue("240", 6),
                                                        new OptionValue("250", 8)), null
                                                        ),
                                                new OptionGroup("초록", List.of(
                                                        new OptionValue("230", 2),
                                                        new OptionValue("240", 4),
                                                        new OptionValue("250", 6)), null
                                                        ),
                                                new OptionGroup("노랑", List.of(
                                                        new OptionValue("230", 3),
                                                        new OptionValue("240", 5),
                                                        new OptionValue("250", 7)), null
                                                        )
                                                )
                                        ),
                                        new OptionGroup("줄무늬", List.of(), List.of(
                                                new OptionGroup("빨강", List.of(
                                                        new OptionValue("230", 2),
                                                        new OptionValue("240", 4),
                                                        new OptionValue("250", 6)), null
                                                        ),
                                                new OptionGroup("파랑", List.of(
                                                        new OptionValue("230", 3),
                                                        new OptionValue("240", 5),
                                                        new OptionValue("250", 7)), null
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        ProductCreateResponse 상품 = 상품_생성(로그인.token(), 옵션3개상품);

        // 검증
        assertNotNull(상품);
        assertEquals(1, 상품.options().size());  // 무늬

        OptionGroup patternOption = 상품.options().get(0);
        assertEquals("무늬", patternOption.optionName());
        assertEquals(2, patternOption.optionValues().size());  // 체크무늬, 줄무늬
        assertEquals(2, patternOption.subOptions().size());  // 체크무늬 옵션그룹, 줄무늬 옵션그룹

        OptionGroup checkPattern = patternOption.subOptions().get(0);
        assertEquals("체크무늬", checkPattern.optionName());
        assertEquals(4, checkPattern.subOptions().size());  // 빨강, 파랑, 초록, 노랑

        OptionGroup stripePattern = patternOption.subOptions().get(1);
        assertEquals("줄무늬", stripePattern.optionName());
        assertEquals(2, stripePattern.subOptions().size());  // 빨강, 파랑

        // 체크무늬의 빨강 옵션 검증
        OptionGroup checkRedOption = checkPattern.subOptions().get(0);
        assertEquals("빨강", checkRedOption.optionName());
        assertEquals(3, checkRedOption.optionValues().size());  // 230, 240, 250
    }

    @Test
    void 상품수정_옵션x() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        String 수정전_상품이름 = "수정 전 상품";
        String 수정후_상품이름 = "수정 후 상품";

        ProductCreateRequest 옵션1개상품 = new ProductCreateRequest(
                수정전_상품이름,
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );

        ProductCreateResponse 수정전_상품 = 상품_생성(로그인.token(), 옵션1개상품);

        ProductUpdateResponse 수정후_상품 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 수정전_상품.id())
                .body(new ProductUpdateRequest(
                        수정후_상품이름,
                        25000,
                        "다양한 색상의 티셔츠입니다.",
                        Condition.New,
                        List.of(
                                new OptionGroup("색상", List.of(
                                        new OptionValue("빨강", 10),
                                        new OptionValue("파랑", 15),
                                        new OptionValue("검정", 20)
                                ), null))))
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

        String 수정전_상품이름 = "기존 상품";
        String 수정후_상품이름 = "수정된 상품";

        ProductCreateRequest 기존상품 = new ProductCreateRequest(
                수정전_상품이름,
                25000,
                "기존 상품 설명",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상", List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );

        ProductCreateResponse 수정전_상품 = 상품_생성(로그인.token(), 기존상품);

        ProductUpdateRequest 수정할_상품 = new ProductUpdateRequest(
                수정후_상품이름,
                27000,
                "수정된 상품 설명",
                Condition.New,
                List.of(
                        new OptionGroup("색상", List.of(
                                new OptionValue("노랑", 5),
                                new OptionValue("초록", 8)
                        ), null)
                )
        );

        ProductUpdateResponse 수정후_상품 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 수정전_상품.id())
                .body(수정할_상품)
                .when()
                .put("/products/{productId}")
                .then().log().all()
                .statusCode(200)  // 정상 응답 확인
                .extract()
                .as(ProductUpdateResponse.class);


        List<OptionGroup> 수정된옵션 = 수정후_상품.options();
        assertThat(수정된옵션).hasSize(1);
        assertThat(수정된옵션.get(0).optionValues()).hasSize(2);

        List<OptionValue> 수정된_옵션_이름들 = 수정된옵션.get(0).optionValues()
                .stream()
                .map(optionValue -> new OptionValue(
                        optionValue.optionValue(),
                        optionValue.stock()
                ))
                .toList();

        assertThat(수정된_옵션_이름들.get(0).optionValue()).isEqualTo("노랑");
        assertThat(수정된_옵션_이름들.get(1).optionValue()).isEqualTo("초록");
    }

    @Test
    void 상품삭제() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductCreateRequest 옵션1개상품 = new ProductCreateRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );

        ProductCreateResponse 상품 = 상품_생성(로그인.token(), 옵션1개상품);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + 로그인.token())
                .pathParam("productId", 상품.id())
                .when()
                .delete("/products/{productId}")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 상품조회() {
        AdminCreateResponse 관리자 = 관리자_생성();
        AdminLoginResponse 로그인 = 관리자_로그인();

        ProductCreateRequest 티 = new ProductCreateRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );

        ProductCreateRequest 신발 = new ProductCreateRequest(
                "신발",
                25000,
                "편안한신발.",
                Category.SHOES,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );

        ProductCreateRequest 옷 = new ProductCreateRequest(
                "이쁜 옷",
                25000,
                "여기저기 이쁜 옷.",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );

        ProductCreateResponse 상품1 = 상품_생성(로그인.token(), 티);
        ProductCreateResponse 상품2 = 상품_생성(로그인.token(), 신발);
        ProductCreateResponse 상품3 = 상품_생성(로그인.token(), 옷);

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

        ProductCreateRequest 티 = new ProductCreateRequest(
                "컬러 티셔츠",
                25000,
                "다양한 색상의 티셔츠입니다.",
                Category.TOP,
                Condition.New,
                List.of(
                        new OptionGroup("색상",  List.of(
                                new OptionValue("빨강", 10),
                                new OptionValue("파랑", 15),
                                new OptionValue("검정", 20)
                        ), null)
                )
        );
        ProductCreateResponse 상품1 = 상품_생성(로그인.token(), 티);

        ProductCreateResponse 특정상품 = RestAssured
                .given().log().all()
                .pathParam("productId", 상품1.id())
                .when()
                .get("/products/{productId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ProductCreateResponse.class);


    }
}
