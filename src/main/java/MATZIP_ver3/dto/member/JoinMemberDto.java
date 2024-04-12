package MATZIP_ver3.dto.member;

import MATZIP_ver3.domain.order.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinMemberDto {

    @NotBlank
    @Length(min = 4, max = 20)
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
