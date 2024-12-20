# java-convenience-store-precourse

## 요구사항

> 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고 안내하는 결제 시스템 구현

- 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
    - 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다.
- 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- 영수증 출력 후 추가 구매를 진행할지 또는 종료할지 선택할 수 있다.
- 고객이 상품을 구매할 때마다, 결제된 수량 만큼 상품의 재고에서 차감하여 수량을 관리한다.
- 시스템은 항상 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.
- 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고,
  "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.

#### 프로모션 할인

- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 발생한다.
- N개 구매 시 1개 무료 증정의 형태로 진행된다.
- 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용된다.
- 동일 상품에 여러 프로모션이 적용되지 않는다.
- 프로모션 혜택은 프로모션 재고 내에서만 적용된다.
- 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- 만약 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하면, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.
- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.

#### 멤버십 할인

- 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- 멤버십 할인 최대 한도는 8,000원이다.

#### 영수증 출력

- 영수증은 고객의 구매 내역과 할인을 요약하여 출력한다.
- 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있도록 한다.
- 영수증 항목
    - 구매 상품 내역 : 구매한 상품명, 수량, 가격
    - 증정 상품 내역 : 프로모션에 따라 무료로 제공된 증정 상품 목록
    - 금액 정보
        - 총구매액 : 구매한 상품의 총 수량과 총 금액
        - 행사할인 : 프로모션에 의해 할인된 금액
        - 멤버십 할인 : 멤버십에 의해 추가로 할인된 금액
        - 내실돈 : 최종 결제 금액

## 추가로 정의한 요구사항

# 🏪 편의점 Todo List

#### 1. 사용자에게 구매 상품을 입력받은 후 결제 가능 여부를 판단한다.

- [x] productions.md 파일에 있는 상품 정보들을 읽어와서 메모리에 저장한다.
    - [x] productions.md 파일을 읽어온다.
    - [x] 상품별로 객체를 생성한다.
    - [x] 생성된 객체를 메모리에 저장한다.
- [x] promotionRepository.md 파일에 있는 프로모션 정보들을 읽어와서 메모리에 저장한다.
    - [x] promotionRepository.md 파일을 읽어온다.
    - [x] 프로모션 정보별로 객체를 생성한다.
    - [x] 생성된 객체를 메모리에 저장한다.
- [x] 환영 인사와 함께 현재 보유하고 있는 상품명, 가격, 수량(재고), 프로모션 정보를 출력한다.
- [x] 구매할 상품명과 수량을 형식에 맞게 입력받는다.
- [x] 입력받은 상품의 이름과 수량을 바탕으로 결제 가능 여부를 확인한다.
    - [x] `ERROR` 구매할 상품과 수량 형식이 올바르지 않은 경우 예외가 발생한다.
    - [x] `ERROR` 존재하지 않는 상품을 입력한 경우 예외가 발생한다.
    - [x] `ERROR` 구매 수량이 재고 수량을 초과한 경우 예외가 발생한다.
    - [x] `ERROR` 기타 잘못된 값을 입력하는 경우 예외가 발생한다.

#### 2. 프로모션 혜택, 멤버십 할인을 확인 후 안내한다.

- [x] 프로모션 대상 상품이 있는지 확인한다.
    - [x] 해당 상품의 프로모션 기간을 확인한다.
    - [x] 프로모션 재고를 확인한다.
        - [x] 프로모션 재고는 많지만 가져온 수량이 부족한 경우, 혜택에 대한 안내 메시지를 출력한다.
            - [x] Y 응답을 받은 경우, 증정 받을 수 있는 상품을 추가한다.
            - [x] N 응답을 받은 경우, 증정 받을 수 있는 상품을 추가하지 않는다.
        - [x] 프로모션 재고가 부족한 경우, 일부 수량에 대해 정가로 결제됨을 안내하는 메시지 출력한다.
            - [x] Y 응답을 받은 경우, 일부 수량에 대해 정가로 결제한다.
            - [x] N 응답을 받은 경우, 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.
- [x] 멤버십 할인 적용 여부 확인한다.
    - [x] Y 응답을 받은 경우, 멤버십 할인을 적용한다.
    - [x] N 응답을 받은 경우, 멤버십 할인을 적용하지 않는다.

#### 3. 영수증 출력 및 추가 구매 여부 확인

- [x] 영수증 출력한다.
    - [x] 구매 상품 내역 : 구매한 상품명, 수량, 가격
    - [x] 증정 상품 내역 : 프로모션에 따라 무료로 제공된 증정 상품 목록
    - [x] 금액 정보 : 총구매액, 행사할인, 멤버십 할인, 내실돈
- [x] 추가 구매 여부를 확인하기 위한 안내 메시지 출력한다.
    - [x] 추가 구매를 원하는 경우, 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
    - [x] 추가 구매를 원하지 않는 경우, 구매를 종료한다.
