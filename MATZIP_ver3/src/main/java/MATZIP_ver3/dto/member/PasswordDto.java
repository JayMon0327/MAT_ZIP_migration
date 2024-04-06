package MATZIP_ver3.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDto {

    @NotNull
    private Long id;

    @NotBlank
    @Length(min = 10, max = 20)
    private String currentPassword;

    @NotBlank
    @Length(min = 10, max = 20)
    private String newPassword;

    @NotBlank
    @Length(min = 10, max = 20)
    private String newPasswordCheck;
}
