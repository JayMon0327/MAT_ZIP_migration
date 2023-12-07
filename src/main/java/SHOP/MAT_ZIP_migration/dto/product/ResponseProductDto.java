package SHOP.MAT_ZIP_migration.dto.product;

import SHOP.MAT_ZIP_migration.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDto {
    private Long id;
    private String title;
    private String description;
    private Member member;
    private List<ItemDto> items;
    private List<ImageDto> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDto {
        private String name;
        private int price;
        private int stockQuantity;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDto {
        private String storeFileName;
    }
}

