package SHOP.MAT_ZIP_migration.dto.product;

import SHOP.MAT_ZIP_migration.domain.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDto {

    private static final String responseHeader = "응답 : ";

    @NotNull(message = responseHeader+ "{product.notNull}")
    private Long id;

    @NotBlank(message = responseHeader + "{title.notBlank}")
    @Length(min = 10, max = 30, message = responseHeader + "{title.length}")
    private String title;

    @NotBlank(message = responseHeader + "{content.notBlank}")
    @Length(min = 10, max = 1000, message = responseHeader + "{content.length}")
    private String description;

    @NotNull(message =responseHeader + "{member.notNull}")
    @Valid
    private Member member;

    private List<ItemDto> items;
    private List<ProductImageDto> images;
    private List<ReviewDto> reviews;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDto {
        @NotBlank(message = responseHeader + "{name.notBlank}")
        @Length(min = 5, max = 20, message = "{name.length}")
        private String name;

        @NotNull(message = responseHeader + "{price.notNull}")
        @Min(value = 1000, message = responseHeader + "{price.min}")
        @Max(value = 1000000, message = responseHeader + "{price.max}")
        private Integer price;

        @NotNull(message = responseHeader + "{stock.notNull}")
        @Min(value = 1, message = responseHeader + "{stock.min}")
        @Max(value = 100, message = responseHeader + "{stock.max}")
        private Integer stock;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductImageDto {
        private String storeFileName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewDto {

        @NotNull(message = responseHeader + "{review.notNull}")
        private Long id;

        @NotNull(message = responseHeader + "{member.notNull}")
        private Member member;

        @NotBlank(message = responseHeader + "{content.notBlank}")
        @Length(min = 10, max = 1000, message = responseHeader + "{content.length}")
        private String content;

        @NotNull(message = responseHeader + "{rating.notNull}")
        @Min(value = 1, message = responseHeader + "{rating.min}")
        @Max(value = 5, message = responseHeader + "{rating.max}")
        private Integer rating;

        private List<ReviewImageDto> images;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ReviewImageDto {
            private String storeFileName;
        }
    }
}

