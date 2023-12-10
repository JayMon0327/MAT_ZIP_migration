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

    @NotNull
    private Long id;

    @NotBlank
    @Length(min = 10, max = 30)
    private String title;

    @NotBlank
    @Length(min = 10, max = 1000)
    private String description;

    @NotNull
    @Valid
    private Member member;

    private List<ItemDto> items;
    private List<ProductImageDto> images;
    private List<ReviewDto> reviews;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDto {
        @NotBlank
        @Length(min = 5, max = 20)
        private String name;

        @NotNull
        @Min(1000)
        @Max(1000000)
        private Integer price;

        @NotNull
        @Min(1)
        @Max(100)
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

        @NotNull
        private Long id;

        @NotNull
        private Member member;

        @NotBlank
        @Length(min = 10, max = 1000)
        private String content;

        @NotNull
        @Min(1)
        @Max(5)
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

