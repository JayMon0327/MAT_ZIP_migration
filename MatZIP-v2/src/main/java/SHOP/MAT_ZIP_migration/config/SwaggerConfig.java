package SHOP.MAT_ZIP_migration.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;




@OpenAPIDefinition(
        info = @Info(
                title = "mat_zip API Document",
                description = "맛집 프로젝트 마이그레이션 API 명세",
                version = "v0.1",
                termsOfService = "https://bit.ly/StudyNote",
                license = @License(
                        name = "Apache License Version 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                ),
                contact = @Contact(
                        name = "dev",
                        email = "dev.be.lws@gmail.com"
                )
        ),
        tags = {
                @Tag(name = "01.Member", description = "사용자 페이지"),
                @Tag(name = "02.Payment", description = "결제 페이지"),
                @Tag(name = "03.Order", description = "주문 페이지"),
                @Tag(name = "04.Product", description = "상품게시판 페이지"),
                @Tag(name = "05.Review", description = "리뷰게시판 페이지"),
        }
)
@Configuration
public class SwaggerConfig {
}
