package SHOP.MAT_ZIP_migration.dto.member;

import SHOP.MAT_ZIP_migration.domain.order.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberDto {

    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 10, max = 20)
    private String password;

    @NotBlank
    @Length(min = 10, max = 20)
    private String passwordCheck;

    @NotBlank
    @Length(min = 4, max = 10)
    private String nickName;

    @Email
    @NotBlank
    private String email;

    private Address address;
}
