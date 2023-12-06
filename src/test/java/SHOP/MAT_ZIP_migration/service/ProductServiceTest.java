package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.product.ProductAndItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.repository.ItemRepository;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    private Member member;
    private RequestProductDto productDto;
    private List<RequestItemDto> itemDtos;
    private ProductAndItemDto productAndItemDto;

    @BeforeEach
    void setUp() throws IOException {
        // 멤버 생성 및 저장
        member = Member.builder()
                .username("김철수")
                .build();
        memberRepository.save(member);

        // 상품 DTO 설정
        productDto = RequestProductDto.builder()
                .title("제목1")
                .description("내용1")
                .build();

        // 아이템 DTO 리스트 설정
        itemDtos = new ArrayList<>();
        itemDtos.add(new RequestItemDto("Test Item 1", 10000, 10));
        itemDtos.add(new RequestItemDto("Test Item 2", 20000, 5));

        productAndItemDto = new ProductAndItemDto();
        productAndItemDto.setProductDto(productDto);
        productAndItemDto.setItemDtos(itemDtos);
    }

    @DisplayName("상품 등록 테스트")
    @Test
    void save() throws IOException {
        Long productId = productService.saveProduct(productDto, member);
        Product savedProduct = productRepository.findById(productId).orElseThrow();

        assertThat(savedProduct.getMember().getUsername()).isEqualTo("김철수");
        assertThat(savedProduct.getTitle()).isEqualTo("제목1");
        assertThat(savedProduct.getDescription()).isEqualTo("내용1");
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() throws IOException {
        Long savedProductId = productService.saveProduct(productDto, member);

        RequestProductDto updatedto = RequestProductDto.builder()
                .title("제목2")
                .description("내용2")
                .build();
        productService.updateProduct(savedProductId,updatedto);

        Product resultProduct = productRepository.findById(savedProductId).orElseThrow();
        assertThat(resultProduct.getTitle()).isEqualTo("제목2");
        assertThat(resultProduct.getDescription()).isEqualTo("내용2");
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void delete() throws IOException {
        Long savedProductId = productService.saveProduct(productDto, member);
        productService.delete(savedProductId);

        Optional<Product> deletedProduct = productRepository.findById(savedProductId);
        assertThat(deletedProduct.isPresent()).isFalse();
    }

    @DisplayName("상품과 아이템 등록 테스트")
    @Test
    void saveProductWithItemsTest() throws IOException {
        Long productId = productService.saveProductAndItem(productAndItemDto, member);

        Product savedProduct = productRepository.findById(productId).orElseThrow();

        assertThat(savedProduct.getItems().size()).isEqualTo(2);
        assertThat(savedProduct.getItems().get(0).getName()).isEqualTo("Test Item 1");
        assertThat(savedProduct.getItems().get(0).getPrice()).isEqualTo(10000);
        assertThat(savedProduct.getItems().get(1).getName()).isEqualTo("Test Item 2");
        assertThat(savedProduct.getItems().get(1).getPrice()).isEqualTo(20000);
    }

    @DisplayName("상품과 아이템 수정 테스트")
    @Test
    void updateProductWithItemsTest() throws IOException {
        Long savedProductId = productService.saveProduct(productDto, member);
        productService.updateProductAndItem(savedProductId, productAndItemDto);

        Product resultProduct = productRepository.findById(savedProductId).orElseThrow();

        assertThat(resultProduct.getItems().size()).isEqualTo(2);
        assertThat(resultProduct.getItems().get(0).getName()).isEqualTo("Test Item 1");
        assertThat(resultProduct.getItems().get(0).getPrice()).isEqualTo(10000);
        assertThat(resultProduct.getItems().get(1).getName()).isEqualTo("Test Item 2");
        assertThat(resultProduct.getItems().get(1).getPrice()).isEqualTo(20000);
    }
}