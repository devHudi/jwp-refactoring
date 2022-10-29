package kitchenpos.acceptance;

import static kitchenpos.acceptance.MenuGroupAcceptanceTest.메뉴_그룹을_생성한다;
import static kitchenpos.acceptance.ProductAcceptanceTest.상품을_생성한다;
import static kitchenpos.acceptance.RequestUtil.get;
import static kitchenpos.acceptance.RequestUtil.post;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

public class MenuAcceptanceTest extends AcceptanceTest {

    static ExtractableResponse<Response> 메뉴를_생성한다(final String name, final int price, final long menuGroupId,
                                                  final List<Map<String, Long>> menuProducts) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(BigDecimal.valueOf(price));
        menu.setMenuGroupId(menuGroupId);

        menu.setMenuProducts(menuProducts.stream()
                .map(map -> {
                    MenuProduct menuProduct = new MenuProduct();
                    menuProduct.setProductId(map.get("productId"));
                    menuProduct.setQuantity(map.get("quantity"));
                    return menuProduct;
                })
                .collect(Collectors.toList()));

        return post("/api/menus", menu);
    }

    static ExtractableResponse<Response> 모든_메뉴를_조회한다() {
        return get("/api/menus");
    }

    @DisplayName("메뉴를 관리한다.")
    @TestFactory
    Stream<DynamicTest> manageMenu() {
        // given
        MenuGroup 치킨 = 메뉴_그룹을_생성한다("치킨").as(MenuGroup.class);

        Product 후라이드 = 상품을_생성한다("후라이드", 19_000).as(Product.class);
        Product 양념 = 상품을_생성한다("양념", 19_000).as(Product.class);
        Product 간장 = 상품을_생성한다("간장", 19_000).as(Product.class);

        return Stream.of(
                dynamicTest("메뉴를 생성한다.", () -> {
                    // when
                    Map<String, Long> menuProduct1 = Map.of("productId", 후라이드.getId(), "quantity", 1L);
                    Map<String, Long> menuProduct2 = Map.of("productId", 양념.getId(), "quantity", 1L);
                    Map<String, Long> menuProduct3 = Map.of("productId", 간장.getId(), "quantity", 1L);

                    ExtractableResponse<Response> response = 메뉴를_생성한다("후라이드+후라이드", 35_000, 치킨.getId(), List.of(
                            menuProduct1, menuProduct2, menuProduct3
                    ));

                    // then
                    상태코드를_검증한다(response, HttpStatus.CREATED);
                    필드가_Null이_아닌지_검증한다(response, "id");
                }),
                dynamicTest("모든 메뉴를 조회한다.", () -> {
                    // when
                    ExtractableResponse<Response> response = 모든_메뉴를_조회한다();

                    // then
                    상태코드를_검증한다(response, HttpStatus.OK);
                    리스트_길이를_검증한다(response, ".", 1);
                    리스트_길이를_검증한다(response, "[0].menuProducts", 3);
                    필드가_Null이_아닌지_검증한다(response, "[0].menuProducts[0].seq");
                })
        );
    }
}