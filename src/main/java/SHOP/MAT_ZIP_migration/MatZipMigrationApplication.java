package SHOP.MAT_ZIP_migration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MatZipMigrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatZipMigrationApplication.class, args);
	}

}
