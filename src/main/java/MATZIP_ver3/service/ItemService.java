package MATZIP_ver3.service;

import MATZIP_ver3.domain.Product;
import MATZIP_ver3.domain.order.Item;
import MATZIP_ver3.dto.product.RequestSaveItemDto;
import MATZIP_ver3.dto.product.RequestUpdateItemDto;
import MATZIP_ver3.repository.ItemRepository;
import MATZIP_ver3.repository.ProductRepository;
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
        Item item = itemRepository.findById(requestUpdateItemDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("아이템이 존재하지 않습니다."));
        item.change(requestUpdateItemDto.getName(), requestUpdateItemDto.getPrice(), requestUpdateItemDto.getStock());
        return item;
    }
}
