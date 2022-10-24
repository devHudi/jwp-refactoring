# 키친포스

## 요구 사항

### 메뉴 그룹

- 메뉴 그룹을 생성한다.
- 생성된 메뉴 그룹 전체 목록을 조회한다.

### 메뉴

- 메뉴를 생성한다.
    - 메뉴의 가격이 `null`이라면 `IAE`를 던진다.
    - 메뉴의 가격이 0보다 작다면 `IAE`를 던진다.
    - 메뉴의 메뉴 그룹이 존재하지 않다면, `IAE`를 던진다.
    - 메뉴의 메뉴 상품(menu product) 리스트가 존재하지 않는 상품을 가지고 있다면, `IAE`를 던진다.
    - 메뉴의 모든 메뉴 상품 목록에 대해 `상품 가격 * 메뉴 상품 수량`의 합이 메뉴의 가격보다 크다면 `IAE`를 던진다.
- 생성된 메뉴 전체 목록을 조회한다.

### 주문

- 주문을 생성한다.
    - 주문 항목 목록이 비어있다면, `IAE`를 던진다.
    - 주문 항목 목록의 메뉴가 하나라도 존재하지 않다면 `IAE`를 던진다.
    - 주문의 주문 테이블이 존재하지 않다면 `IAE`를 던진다.
    - 주문의 주문 테이블이 빈 주문 테이블이라면 `IAE`를 던진다.
- 생성된 주문 전체 목록을 조회한다.
- 주문의 주문 상태를 변경한다.
    - 요청된 주문이 존재하지 않다면, `IAE`를 던진다.
    - 저장된 주문의 상태가 `계산 완료`라면 `IAE`를 던진다.

### 상품

- 상품을 생성한다.
    - 상품의 가격이 `null`이라면, `IAE`를 던진다.
    - 상품의 가격이 0보다 작다면 `IAE`를 던진다.
- 생성된 상품 전체 목록을 조회한다.

### 단체 지정

- 단체 지정을 생성한다.
    - 단체 지정의 주문 테이블 목록이 비어있다면, `IAE`를 던진다.
    - 단체 지정의 주문 테이블 목록이 2보다 작다면, `IAE`를 던진다.
    - 단체 지정의 주문 테이블 목록과 실제 저장된 주문 테이블 목록의 개수가 다르면 `IAE`를 던진다.
    - 단체 지정의 주문 테이블 목록에 대해 어떤 주문 테이블이 비어있지 않다uSET fF면 `IAE`를 던진다.
    - 단체 지정의 주문 테이블 목록에 대해 어떤 주문 테이블의 단체 지정 정보가 있다면 `IAE`를 던진다.
- 단체 지정을 해제(ungroup)한다.
    - 단체 지정의 주문 테이블 목록 중 하나라도 `조리` 혹은 `식사` 상태라면 `IAE` 를 던진다.
    - 단체 지정의 주문 테이블 모두 테이블 그룹을 없앤다.
    - 단체 지정의 주문 테이블 모두 빈 테이블이 아니도록 만든다.

### 주문 테이블

- 주문 테이블을 생성한다.
- 생성된 주문 테이블 목록을 조회한다.
- 주문 테이블의 빈 테이블 여부를 변경한다.
    - 주문 테이블이 존재하지 않으면 `IAE`를 던진다.
    - 주문 테이블의 단체 지정 정보가 `null`이 아니라면 `IAE`를 던진다.
    - 주문 테이블이 존재하고, 주문 상태가 `조리` 또는 `식사` 상태라면  `IAE`를 던진다.
- 주문 테이블의 방문한 손님 수를 변경한다.
    - 전달된 주문 테이블의 방문한 손님 수가 0보다 작다면 `IAE`를 던진다.
    - 전달된 주문 테이블 ID에 해당하는 주문 테이블이 없다면 `IAE`를 던진다.
    - 전달된 주문 테이블 ID에 해당하는 주문 테이블이 빈 테이블이라면 `IAE`를 던진다.

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
