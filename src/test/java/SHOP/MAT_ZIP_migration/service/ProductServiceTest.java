package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Image;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private FileStore fileStore;

    private Member member;
    private RequestProductDto ProductDto;
    private Long savedProductId;

    @BeforeEach
    void setUp() throws IOException {
        member = Member.builder()
                .username("김철수")
                .build();
        memberRepository.save(member);

        when(fileStore.storeFiles(any())).thenReturn(List.of(
                new Image(1L,"test1.jpg", "12312.jpg","/path/to/test1.jpg", null, null),
                new Image(2L, "test2.jpg","22322.jpg", "/path/to/test2.jpg", null, null)
        ));

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

    @DisplayName("상품 등록 테스트")
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

    @DisplayName("상품 수정 테스트")
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

    @DisplayName("상품 삭제 테스트")
    @Test
    void delete() {
        productService.delete(savedProductId);

        Optional<Product> deletedProduct = productRepository.findById(savedProductId);
        assertThat(deletedProduct.isPresent()).isFalse();
    }

    @DisplayName("상품 및 이미지 저장 테스트")
    @Test
    void testSaveProductWithImages() throws IOException {
        RequestProductDto productDto = RequestProductDto.builder()
                .title("제목1")
                .description("내용1")
                .price(1000)
                .stock(10)
                .imageFiles(null) // 실제 파일 업로드는 생략
                .build();

        Member savedMember = memberRepository.findById(member.getId()).orElseThrow();
        productService.save(productDto, savedMember);

        Product savedProduct = productRepository.findAll().stream()
                .filter(product -> product.getTitle().equals(productDto.getTitle()))
                .findFirst()
                .orElseThrow();

        assertThat(savedProduct.getImages()).hasSize(2);
        assertThat(savedProduct.getImages().get(0).getUploadFileName()).isEqualTo("test1.jpg");
        assertThat(savedProduct.getImages().get(1).getUploadFileName()).isEqualTo("test2.jpg");
    }

}