package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Image;
import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.domain.order.Item;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private ItemRepository itemRepository;

    @MockBean
    private FileStore fileStore;

    private Member member;
    private RequestProductDto productDto;
    private Long savedProductId;
    private List<RequestItemDto> requestItemDtos;

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

        productDto = RequestProductDto.builder()
                .title("제목1")
                .description("내용1")
                .build();
        productService.saveProduct(productDto,member);

        //아이템 정보 설정
        requestItemDtos = new ArrayList<>();
        requestItemDtos.add(new RequestItemDto("Test Item 1", 10000, 10));
        requestItemDtos.add(new RequestItemDto("Test Item 2", 20000, 5));

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
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() throws IOException {
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
    void delete() {
        productService.delete(savedProductId);

        Optional<Product> deletedProduct = productRepository.findById(savedProductId);
        assertThat(deletedProduct.isPresent()).isFalse();
    }

    @DisplayName("상품 및 이미지 저장 테스트")
    @Test
    void SaveProductWithImages() throws IOException {

        Product savedProduct = productRepository.findAll().stream()
                .filter(product -> product.getTitle().equals(productDto.getTitle()))
                .findFirst()
                .orElseThrow();


        assertThat(savedProduct.getImages()).hasSize(2);
        assertThat(savedProduct.getImages().get(0).getUploadFileName()).isEqualTo("test1.jpg");
        assertThat(savedProduct.getImages().get(1).getUploadFileName()).isEqualTo("test2.jpg");
    }

    @DisplayName("상품과 아이템 등록 테스트")
    @Test
    void saveProductWithItemsTest() throws IOException {
        Long productId = productService.saveProductAndItem(productDto, requestItemDtos, member);

        // 상품 조회 및 검증
        Product savedProduct = productRepository.findById(productId).orElseThrow();

        // 상품에 연결된 아이템 검증
        List<Item> items = itemRepository.findByProduct(savedProduct);

        assertThat(savedProduct.getItems().size()).isEqualTo(2);
        assertThat(savedProduct.getItems().get(0).getName()).isEqualTo("Test Item 1");
        assertThat(savedProduct.getItems().get(0).getPrice()).isEqualTo(10000);
        assertThat(savedProduct.getItems().get(1).getName()).isEqualTo("Test Item 2");
        assertThat(savedProduct.getItems().get(1).getPrice()).isEqualTo(20000);
    }
}