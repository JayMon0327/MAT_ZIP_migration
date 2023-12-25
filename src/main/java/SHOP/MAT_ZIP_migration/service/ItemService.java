package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import SHOP.MAT_ZIP_migration.dto.product.RequestSaveItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestUpdateItemDto;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Item saveItem(Long productId, RequestSaveItemDto requestSaveItemDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Item item = Item.builder()
                .name(requestSaveItemDto.getName())
                .price(requestSaveItemDto.getPrice())
                .stock(requestSaveItemDto.getStock())
                .build();

        product.addItem(item);
        return item;
    }

    public Item updateItem(RequestUpdateItemDto requestUpdateItemDto) {
        Item item = itemRepository.findById(requestUpdateItemDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다."));
        item.change(requestUpdateItemDto.getName(), requestUpdateItemDto.getPrice(), requestUpdateItemDto.getStock());
        return item;
    }
}
