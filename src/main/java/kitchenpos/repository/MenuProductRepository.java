package kitchenpos.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.domain.menu.MenuProduct;

public interface MenuProductRepository {
    MenuProduct save(MenuProduct entity);

    Optional<MenuProduct> findById(Long id);

    List<MenuProduct> findAll();
}
