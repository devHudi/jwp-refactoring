package kitchenpos.dto.request;

import kitchenpos.domain.table.OrderTable;

public class OrderTableRequest {

    private int numberOfGuests;
    private boolean empty;

    private OrderTableRequest() {
    }

    public OrderTableRequest(final OrderTable orderTable) {
        this(orderTable.getNumberOfGuests(), orderTable.isEmpty());
    }

    public OrderTableRequest(final int numberOfGuests, final boolean empty) {
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public OrderTable toEntity() {
        return new OrderTable(numberOfGuests, empty);
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
