package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Image;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private RequestProductDto ProductDto;
    private Long savedProductId;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .username("김철수")
                .build();
        memberRepository.save(member);
        ProductDto = RequestProductDto.builder()
                .title("제목1")
                .description("내용1")
                .price(1000)
                .stock(10)
                .build();
        productService.save(ProductDto,member);

        Product savedProduct = productRepository.findAll().stream().findFirst().orElseThrow();
        savedProductId = savedProduct.getId();
    }

    @DisplayName("상품 등록")
    @Test
    void save() {
        Product savedProduct = productRepository.findById(savedProductId).orElseThrow();
        String memberName = savedProduct.getMember().getUsername();

        assertThat(memberName).isEqualTo("김철수");
        assertThat(savedProduct.getTitle()).isEqualTo("제목1");
        assertThat(savedProduct.getDescription()).isEqualTo("내용1");
        assertThat(savedProduct.getPrice()).isEqualTo(1000);
        assertThat(savedProduct.getStock()).isEqualTo(10);
    }

    @DisplayName("상품 수정")
    @Test
    void update() {
        RequestProductDto updatedto = RequestProductDto.builder()
                .title("제목2")
                .description("내용2")
                .price(2000)
                .stock(20)
                .build();
        productService.update(savedProductId,updatedto);

        Product resultProduct = productRepository.findById(savedProductId).orElseThrow();
        assertThat(resultProduct.getTitle()).isEqualTo("제목2");
        assertThat(resultProduct.getDescription()).isEqualTo("내용2");
        assertThat(resultProduct.getPrice()).isEqualTo(2000);
        assertThat(resultProduct.getStock()).isEqualTo(20);
    }

    @DisplayName("상품 삭제")
    @Test
    void delete() {
        productService.delete(savedProductId);

        Optional<Product> deletedProduct = productRepository.findById(savedProductId);
        assertThat(deletedProduct.isPresent()).isFalse();
    }
}