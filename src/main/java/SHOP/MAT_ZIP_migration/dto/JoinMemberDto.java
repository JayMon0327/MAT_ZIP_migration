package SHOP.MAT_ZIP_migration.dto;

import SHOP.MAT_ZIP_migration.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinMemberDto {

    @NotBlank(message = "아이디는 빈칸일 수 없습니다.")
    private String username;

    @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
    private String password;

    @Email
    @NotBlank(message = "이메일은 빈칸일 수 없습니다.")
    private String email;
}
