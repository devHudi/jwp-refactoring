package kitchenpos.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.domain.table.OrderTable;

public interface OrderTableRepository {
    OrderTable save(OrderTable entity);

    Optional<OrderTable> findById(Long id);

    List<OrderTable> findAll();

    List<OrderTable> findAllByIdIn(List<Long> ids);

    List<OrderTable> findAllByTableGroupId(Long tableGroupId);
}
