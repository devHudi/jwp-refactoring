package kitchenpos.application;

import static kitchenpos.fixtures.domain.MenuFixture.createMenu;
import static kitchenpos.fixtures.domain.MenuGroupFixture.createMenuGroupRequest;
import static kitchenpos.fixtures.domain.OrderTableFixture.createOrderTable;
import static kitchenpos.fixtures.domain.ProductFixture.createProduct;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import kitchenpos.DatabaseCleaner;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.Product;
import kitchenpos.dto.request.MenuGroupRequest;
import kitchenpos.dto.response.MenuGroupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private MenuGroupService menuGroupService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TableService tableService;

    @BeforeEach
    void cleanTables() throws SQLException {
        databaseCleaner.clean();
    }

    protected MenuGroupResponse saveMenuGroup(final String name) {
        MenuGroupRequest request = createMenuGroupRequest(name);
        return menuGroupService.create(request);
    }

    protected Menu saveMenu(final String name, final int price, final MenuGroup menuGroup,
                            final List<MenuProduct> menuProducts) {
        Menu menu = createMenu(name, new BigDecimal(price), menuGroup.getId(), menuProducts);
        return menuService.create(menu);
    }

    protected OrderTable saveOrderTable(final int numberOfGuests, final boolean empty) {
        OrderTable request = createOrderTable(numberOfGuests, empty);
        return tableService.create(request);
    }

    protected Product saveProduct(final String name, final int price) {
        Product request = createProduct(name, new BigDecimal(price));
        return productService.create(request);
    }
}
