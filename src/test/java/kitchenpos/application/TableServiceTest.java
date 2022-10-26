package kitchenpos.application;

import static kitchenpos.fixtures.domain.OrderTableFixture.createOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.OrderTable;
import kitchenpos.fixtures.domain.OrderTableFixture.OrderTableRequestBuilder;
import kitchenpos.fixtures.domain.TableGroupFixture.TableGroupRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TableServiceTest extends ServiceTest {

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderTableDao orderTableDao;

    @Autowired
    private TableGroupService tableGroupService;

    private OrderTable orderTable1;
    private OrderTable orderTable2;

    @BeforeEach
    void setUp() {
        this.orderTable1 = orderTableDao.save(createOrderTable(10, true));
        this.orderTable2 = orderTableDao.save(createOrderTable(15, true));
    }

    @DisplayName("create 메소드는")
    @Nested
    class CreateMethod {

        @DisplayName("주문 테이블을 생성한다.")
        @Test
        void Should_CreateOrderTable() {
            // given
            OrderTable orderTable = createOrderTable(10, false);

            // when
            OrderTable actual = tableService.create(orderTable);

            // then
            assertAll(() -> {
                assertThat(actual.getId()).isNotNull();
                assertThat(actual.getNumberOfGuests()).isEqualTo(orderTable.getNumberOfGuests());
                assertThat(actual.isEmpty()).isEqualTo(orderTable.isEmpty());
            });
        }
    }

    @DisplayName("list 메소드는")
    @Nested
    class ListMethod {

        @DisplayName("생성된 모든 주문 테이블 목록을 조회한다.")
        @Test
        void Should_ReturnAllOrderTableList() {
            // given
            int expected = 4;
            for (int i = 0; i < expected; i++) {
                OrderTable orderTable = createOrderTable(10, false);
                orderTableDao.save(orderTable);
            }

            // when
            List<OrderTable> actual = tableService.list();

            // then
            assertThat(actual).hasSize(expected + 2);
        }
    }

    @DisplayName("changeEmpty 메소드는")
    @Nested
    class ChangeEmptyMethod {

        @DisplayName("빈 테이블 여부를 변경한다.")
        @Test
        void Should_ChangeIsEmptyTable() {
            // given
            OrderTable request = new OrderTableRequestBuilder()
                    .empty()
                    .build();

            // when
            OrderTable actual = tableService.changeEmpty(orderTable1.getId(), request);

            // then
            assertThat(actual.isEmpty()).isTrue();
        }

        @DisplayName("주문 테이블이 존재하지 않으면 IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_OrderTableDoesNotExist() {
            // given
            OrderTable request = new OrderTableRequestBuilder()
                    .empty()
                    .build();

            // when & then
            assertThatThrownBy(() -> tableService.changeEmpty(orderTable2.getId() + 1, request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("주문 테이블의 단체 지정 정보가 존재한다면 IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_OrderTableHasTableGroup() {
            // given
            tableGroupService.create(new TableGroupRequestBuilder()
                    .addOrderTables(orderTable1, orderTable2)
                    .build());

            OrderTable request = new OrderTableRequestBuilder().build();

            // when & then
            assertThatThrownBy(() -> tableService.changeEmpty(orderTable1.getId(), request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("changeNumberOfGuests 메소드는")
    @Nested
    class ChangeNumberOfGuestsMethod {

        @DisplayName("주문 테이블의 방문한 손님 수를 변경한다.")
        @Test
        void Should_ChangeNumberOfGuests() {
            // given
            int expected = 100;
            OrderTable orderTable = orderTableDao.save(createOrderTable(1, false));
            OrderTable request = new OrderTableRequestBuilder()
                    .numberOfGuests(expected)
                    .build();

            // when
            OrderTable actual = tableService.changeNumberOfGuests(orderTable.getId(), request);

            // then
            assertThat(actual.getNumberOfGuests()).isEqualTo(expected);
        }

        @DisplayName("방문한 손님 수가 0보다 작다면 IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_NumberOfGuestsIsLessThan0() {
            // given
            OrderTable request = new OrderTableRequestBuilder()
                    .numberOfGuests(-1)
                    .build();

            // when & then
            assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTable1.getId(), request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("전달된 주문 테이블 ID에 대한 주문 테이블이 존재하지 않다면 IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_OrderTableDoesNotExist() {
            // given
            OrderTable orderTable = orderTableDao.save(createOrderTable(1, false));
            OrderTable request = new OrderTableRequestBuilder()
                    .numberOfGuests(100)
                    .build();

            // when & then
            assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTable.getId() + 1, request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("전달된 주문 테이블 ID에 대한 주문 테이블이 비어있다면 IAE를 던진다.")
        @Test
        void Should_ThrowIAE_When_OrderTableIsEmpty() {
            // given
            OrderTable orderTable = orderTableDao.save(createOrderTable(1, true));
            OrderTable request = new OrderTableRequestBuilder()
                    .numberOfGuests(100)
                    .build();

            // when & then
            assertThatThrownBy(() -> tableService.changeNumberOfGuests(orderTable.getId(), request))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
