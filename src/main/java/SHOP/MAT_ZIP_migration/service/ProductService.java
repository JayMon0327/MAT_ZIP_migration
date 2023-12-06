package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Image;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.product.RequestItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final FileStore fileStore;
    private final ItemService itemService;

    @Transactional
    public Long saveProductAndItem(RequestProductDto requestProductDto, List<RequestItemDto> requestItemDtos, Member member) throws IOException {
        Long productId = saveProduct(requestProductDto, member);
        for (RequestItemDto requestItemDto : requestItemDtos)
            itemService.addItem(productId, requestItemDto);
        return productId;
    }

    @Transactional
    public Long saveProduct(RequestProductDto requestProductDto, Member member) throws IOException {
        Product product = Product.builder()
                .member(member)
                .title(requestProductDto.getTitle())
                .description(requestProductDto.getDescription())
                .build();

        List<Image> images = fileStore.storeFiles(requestProductDto.getImageFiles());
        images.forEach(product::addImage);
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public void updateProduct(Long id, RequestProductDto requestProductDto) throws IOException {
        Product savedProduct = productRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("상품 찾기 실패");
        });
        savedProduct.updateProduct(requestProductDto.getTitle(), requestProductDto.getDescription());

        savedProduct.clearImages();
        List<Image> images = fileStore.storeFiles(requestProductDto.getImageFiles());
        images.forEach(savedProduct::addImage);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
