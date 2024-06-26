package MATZIP_ver3.service;

import MATZIP_ver3.domain.*;
import MATZIP_ver3.domain.order.Item;
import MATZIP_ver3.dto.product.*;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.ItemRepository;
import MATZIP_ver3.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final FileStore<ProductImage> fileStore;
    private final ItemService itemService;

    public ProductService(ProductRepository productRepository, ItemRepository itemRepository,
                          @Qualifier("productFileStore") FileStore<ProductImage> fileStore, ItemService itemService) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.fileStore = fileStore;
        this.itemService = itemService;
    }

    @Transactional
    public Long saveProductAndItem(RequestSaveProductAndItemDto dto, Member member) {
        log.info(String.valueOf(dto.getProductDto().getImageFiles()));
        Long productId = saveProduct(dto.getProductDto(), member);
        for (RequestSaveItemDto requestSaveItemDto : dto.getItems()) {
            Item item = itemService.saveItem(productId, requestSaveItemDto);
            itemRepository.save(item);
        }
        return productId;
    }

    @Transactional
    public Long saveProduct(RequestProductDto dto, Member member) {
        Product product = Product.builder()
                .member(member)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();

        List<ProductImage> productImages = fileStore.storeFiles(dto.getImageFiles());
        productImages.forEach(product::addImage);
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public Long updateProductAndItem(Long id, RequestUpdateProductAndItemDto dto) {
        Long productId = updateProduct(id, dto.getProductDto());
        for (RequestUpdateItemDto requestUpdateItemDto : dto.getItems()) {
            itemService.updateItem(requestUpdateItemDto);
        }
        return productId;
    }

    @Transactional
    public Long updateProduct(Long id, RequestProductDto requestProductDto) {
        Product savedProduct = productRepository.findById(id).orElseThrow(() -> {
            return new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT);
        });
        savedProduct.updateProduct(requestProductDto.getTitle(), requestProductDto.getDescription());

        savedProduct.clearImages();
        List<ProductImage> productImages = fileStore.storeFiles(requestProductDto.getImageFiles());
        productImages.forEach(savedProduct::addImage);

        return savedProduct.getId();
    }

    public Page<Product> productAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public ResponseProductDto getProductDetails(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_PRODUCT));

        ResponseProductDto dto = ResponseProductDto.builder()
                .id(productId)
                .title(product.getTitle())
                .description(product.getDescription())
                .member(product.getMember())
                .items(itemDtoTransfer(product))
                .images(imageDtoTransfer(product))
                .reviews(reviewDtoTransfer(product))
                .build();

        return dto;
    }

    public List<ResponseProductDto.ReviewDto> reviewDtoTransfer(Product product) {
        List<ResponseProductDto.ReviewDto> reviewDtos = product.getReviews().stream()
                .map(review -> {
                    List<ResponseProductDto.ReviewDto.ReviewImageDto> reviewImageDtos = review.getReviewImages().stream()
                            .map(reviewImage -> new ResponseProductDto.ReviewDto.ReviewImageDto(reviewImage.getStoreFileName()))
                            .collect(Collectors.toList());

                    return new ResponseProductDto.ReviewDto(review.getId(), review.getMember(), review.getContent(), review.getRating(), reviewImageDtos);
                })
                .collect(Collectors.toList());
        return reviewDtos;
    }

    public List<ResponseProductDto.ItemDto> itemDtoTransfer(Product product) {
        List<ResponseProductDto.ItemDto> itemDtos = product.getItems().stream()
                .map(item -> new ResponseProductDto.ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock()))
                .collect(Collectors.toList());
        return itemDtos;
    }

    public List<ResponseProductDto.ProductImageDto> imageDtoTransfer(Product product) {
        List<ResponseProductDto.ProductImageDto> imageDtos = product.getProductImages().stream()
                .map(image -> new ResponseProductDto.ProductImageDto(image.getStoreFileName()))
                .collect(Collectors.toList());
        return imageDtos;
    }
}
