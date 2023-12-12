package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import SHOP.MAT_ZIP_migration.dto.order.OrderForm;
import SHOP.MAT_ZIP_migration.dto.order.ResponseOrderForm;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 상품 이름, 구매 수량, 총 구매금액, 판매자 이름, 배송지 정보
     */
    @Transactional
    public ResponseOrderForm orderForm(OrderForm form, Member member) {
        ResponseOrderForm orderForm = ResponseOrderForm.builder()
                .items(itemDtoTransfer(form))
                .address(member.getAddress())
                .sellerName(form.getSellerName())
                .totalPrice(calculateTotalPrice(form))
                .build();
        return orderForm;
    }

    public List<ResponseOrderForm.ItemDto> itemDtoTransfer(OrderForm form) {
        List<ResponseOrderForm.ItemDto> itemDtos = form.getItems().stream()
                .map(item -> new ResponseOrderForm.ItemDto(item.getItemName(), item.getCount()))
                .collect(Collectors.toList());
        return itemDtos;
    }

    public int calculateTotalPrice(OrderForm form) {
        int totalPrice = 0;
        for (OrderForm.ItemDto itemDto : form.getItems()) {
            Item item = itemRepository.findById(itemDto.getItemId())
                    .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_ITEM));

            int itemTotalPrice = item.getPrice() * itemDto.getCount();
            totalPrice += itemTotalPrice;
        }
        return totalPrice;
    }
}
