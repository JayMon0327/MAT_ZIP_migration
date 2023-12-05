package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void save(RequestProductDto requestProductDto, Member member) {
        //예외 처리 추가해야함

        Product product = Product.builder()
                .member(member)
                .title(requestProductDto.getTitle())
                .description(requestProductDto.getDescription())
                .price(requestProductDto.getPrice())
                .stock(requestProductDto.getStock())
                .build();
        productRepository.save(product);
    }

    @Transactional
    public void update(Long id, RequestProductDto requestProductDto) {
        Product savedProduct = productRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("상품 찾기 실패");
        });
        savedProduct.updateProduct(requestProductDto.getTitle(), requestProductDto.getDescription(),
                requestProductDto.getPrice(), requestProductDto.getStock());
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
