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
                @Tag(name = "01.Common", description = "공통 기능"),
                @Tag(name = "02.User", description = "사용자 기능"),
                @Tag(name = "03.Undefined", description = "미정의 기능"),
        }
)
@Configuration
public class SwaggerConfig {
}
