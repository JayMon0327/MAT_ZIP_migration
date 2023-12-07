package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.ProductImage;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.domain.order.Item;
import SHOP.MAT_ZIP_migration.dto.product.ProductAndItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.dto.product.ResponseProductDto;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final FileStore fileStore;
    private final ItemService itemService;

    @Transactional
    public Long saveProductAndItem(ProductAndItemDto dto, Member member) throws IOException {
        Long productId = saveProduct(dto.getProductDto(), member);
        for (RequestItemDto requestItemDto : dto.getItemDtos()) {
            Item item = itemService.saveItem(productId, requestItemDto);
            itemRepository.save(item);
        }
        return productId;
    }

    @Transactional
    public Long updateProductAndItem(Long id, ProductAndItemDto dto) throws IOException {
        Long productId = updateProduct(id, dto.getProductDto());
        for (RequestItemDto requestItemDto : dto.getItemDtos()) {
            itemService.saveItem(productId, requestItemDto);
        }
        return productId;
    }

    @Transactional
    public Long saveProduct(RequestProductDto requestProductDto, Member member) throws IOException {
        Product product = Product.builder()
                .member(member)
                .title(requestProductDto.getTitle())
                .description(requestProductDto.getDescription())
                .build();

        List<ProductImage> productImages = fileStore.storeFiles(requestProductDto.getImageFiles());
        productImages.forEach(product::addImage);
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public Long updateProduct(Long id, RequestProductDto requestProductDto) throws IOException {
        Product savedProduct = productRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("상품 찾기 실패");
        });
        savedProduct.updateProduct(requestProductDto.getTitle(), requestProductDto.getDescription());

        savedProduct.clearImages();
        List<ProductImage> productImages = fileStore.storeFiles(requestProductDto.getImageFiles());
        productImages.forEach(savedProduct::addImage);

        return savedProduct.getId();
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product findByProduct(Long id) {
        return productRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("상세보기 실패: 상품 아이디가 존재하지 않습니다");
        });
    }

    public ResponseProductDto getProductDetails(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        // ProductDetailDto에 정보 채우기
        ResponseProductDto dto = new ResponseProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setMember(product.getMember());

        // Product의 Item 리스트를 DTO로 변환
        List<ResponseProductDto.ItemDto> itemDtos = product.getItems().stream()
                .map(item -> new ResponseProductDto.ItemDto(item.getName(), item.getPrice(), item.getStockQuantity()))
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        // Product의 Image 리스트를 DTO로 변환
        List<ResponseProductDto.ImageDto> imageDtos = product.getProductImages().stream()
                .map(image -> new ResponseProductDto.ImageDto(image.getStoreFileName()))
                .collect(Collectors.toList());
        dto.setImages(imageDtos);

        return dto;
    }

    public Page<Product> productAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
