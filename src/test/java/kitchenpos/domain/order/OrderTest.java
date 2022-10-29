package kitchenpos.domain.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderTest {

    @DisplayName("주문 생성자")
    @Nested
    class Constructor {
        @DisplayName("주문 항목이 비어있다면, IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_OrderLineItemsIsEmpty() {
            // given & when & then
            assertThatThrownBy(() -> new Order(1L, List.of()))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("상태 변경")
    @Nested
    class ChangeOrderStatus {
        @DisplayName("주문이 완료 상태라면, IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_OrderStatusIsCompletion() {
            // given
            OrderLineItem orderLineItem = new OrderLineItem(1L, 1L);
            Order order = new Order(1L, 1L, OrderStatus.COMPLETION, LocalDateTime.now(), List.of(orderLineItem));

            // when & then
            assertThatThrownBy(() -> order.changeOrderStatus(OrderStatus.COOKING))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}