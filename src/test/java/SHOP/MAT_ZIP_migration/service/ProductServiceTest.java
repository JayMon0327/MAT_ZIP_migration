package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.product.*;
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
    private List<RequestSaveItemDto> itemDtos;
    private List<RequestUpdateItemDto> updateItemDtos;
    private RequestSaveProductAndItemDto requestSaveProductAndItemDto;

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
        itemDtos.add(new RequestSaveItemDto("Test Item 1", 10000, 10));
        itemDtos.add(new RequestSaveItemDto("Test Item 2", 20000, 5));

        requestSaveProductAndItemDto = new RequestSaveProductAndItemDto();
        requestSaveProductAndItemDto.setProductDto(productDto);
        requestSaveProductAndItemDto.setItems(itemDtos);


    }

    @DisplayName("상품 등록 테스트")
    @Test
    void save() {
        Long productId = productService.saveProduct(productDto, member);
        Product savedProduct = productRepository.findById(productId).orElseThrow();

        assertThat(savedProduct.getMember().getUsername()).isEqualTo("김철수");
        assertThat(savedProduct.getTitle()).isEqualTo("제목1");
        assertThat(savedProduct.getDescription()).isEqualTo("내용1");
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() {
        Long savedProductId = productService.saveProduct(productDto, member);

        RequestProductDto updatedto = RequestProductDto.builder()
                .title("제목2")
                .description("내용2")
                .build();
        productService.updateProduct(savedProductId, updatedto);

        Product resultProduct = productRepository.findById(savedProductId).orElseThrow();
        assertThat(resultProduct.getTitle()).isEqualTo("제목2");
        assertThat(resultProduct.getDescription()).isEqualTo("내용2");
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void delete() {
        Long savedProductId = productService.saveProduct(productDto, member);
        productService.delete(savedProductId);

        Optional<Product> deletedProduct = productRepository.findById(savedProductId);
        assertThat(deletedProduct.isPresent()).isFalse();
    }

    @DisplayName("상품과 아이템 등록 테스트")
    @Test
    void saveProductWithItemsTest() {
        Long productId = productService.saveProductAndItem(requestSaveProductAndItemDto, member);

        Product savedProduct = productRepository.findById(productId).orElseThrow();

        assertThat(savedProduct.getItems().size()).isEqualTo(2);
        assertThat(savedProduct.getItems().get(0).getName()).isEqualTo("Test Item 1");
        assertThat(savedProduct.getItems().get(0).getPrice()).isEqualTo(10000);
        assertThat(savedProduct.getItems().get(1).getName()).isEqualTo("Test Item 2");
        assertThat(savedProduct.getItems().get(1).getPrice()).isEqualTo(20000);
    }


    @DisplayName("상품과 아이템 수정 테스트")
    @Test
    void updateProductWithItemsTest() {
        // 상품과 아이템 저장
        Long savedProductId = productService.saveProductAndItem(requestSaveProductAndItemDto, member);

        // 수정할 상품 DTO 설정
        RequestProductDto updatedProductDto = RequestProductDto.builder()
                .title("제목수정")
                .description("내용수정")
                .build();

        // 수정할 아이템 DTO 리스트 설정
        List<RequestUpdateItemDto> updateItemDtos = new ArrayList<>();
        Product savedProduct = productRepository.findById(savedProductId).orElseThrow();
        Long itemId1 = savedProduct.getItems().get(0).getId();
        Long itemId2 = savedProduct.getItems().get(1).getId();
        updateItemDtos.add(new RequestUpdateItemDto(itemId1, "Change Item 1", 30000, 12));
        updateItemDtos.add(new RequestUpdateItemDto(itemId2, "Change Item 2", 40000, 55));

        RequestUpdateProductAndItemDto updateItemDto = new RequestUpdateProductAndItemDto();
        updateItemDto.setProductDto(updatedProductDto);
        updateItemDto.setItems(updateItemDtos);

        // when
        productService.updateProductAndItem(savedProductId, updateItemDto);

        // then
        Product resultProduct = productRepository.findById(savedProductId).orElseThrow();
        assertThat(resultProduct.getTitle()).isEqualTo("제목수정");
        assertThat(resultProduct.getDescription()).isEqualTo("내용수정");
        assertThat(resultProduct.getItems().size()).isEqualTo(2);
        assertThat(resultProduct.getItems().get(0).getName()).isEqualTo("Change Item 1");
        assertThat(resultProduct.getItems().get(0).getPrice()).isEqualTo(30000);
        assertThat(resultProduct.getItems().get(1).getName()).isEqualTo("Change Item 2");
        assertThat(resultProduct.getItems().get(1).getPrice()).isEqualTo(40000);
    }

}