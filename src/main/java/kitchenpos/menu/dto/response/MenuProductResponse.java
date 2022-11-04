package kitchenpos.menu.dto.response;

import kitchenpos.menu.domain.MenuProduct;

public class MenuProductResponse {

    private Long seq;
    private Long productId;
    private long quantity;

    public MenuProductResponse(final MenuProduct menuProduct) {
        this(menuProduct.getSeq(), menuProduct.getProductId(), menuProduct.getQuantity());
    }

    public MenuProductResponse(final Long seq, final Long productId, final long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}