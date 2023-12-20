package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.dto.member.JoinMemberDto;
import SHOP.MAT_ZIP_migration.dto.member.UpdateMemberDto;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.service.validator.MemberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberValidator memberValidator;
    @Autowired
    private BCryptPasswordEncoder encoder;
    private JoinMemberDto joinMemberDto;

    @BeforeEach
    void setup() {
        joinMemberDto = JoinMemberDto.builder()
                .username("kim123")
                .password("testPassword")
                .passwordCheck("testPassword")
                .nickName("testname")
                .email("kim@naver.com")
                .build();
    }

    @DisplayName("회원가입")
    @Test
    void signUp() {
        memberService.SignUp(joinMemberDto);
        Member savedMember = memberRepository.findByUsername("kim123");

        assertThat(savedMember.getUsername()).isEqualTo("kim123");
        assertThat(savedMember.getEmail()).isEqualTo("kim@naver.com");
        assertThat(encoder.matches("testPassword", savedMember.getPassword())).isTrue();
    }

    @DisplayName("비밀번호 확인과 예외")
    @Test
    void passwordCheck() {
        String rawPassword = joinMemberDto.getPassword();
        String passwordCheck = "testPassword";
        String encodedPassword = memberValidator.PasswordCheck(rawPassword, passwordCheck);

        assertThat(encoder.matches(rawPassword, encodedPassword)).isTrue();
        assertThatThrownBy(() -> memberValidator.PasswordCheck(joinMemberDto.getPassword(), "failPassword"))
                .isInstanceOf(CustomException.class);
    }

    @DisplayName("중복 유저네임 예외")
    @Test
    void DuplicateUsername() {
        JoinMemberDto joinMemberDto2 = JoinMemberDto.builder()
                .username("kim123")
                .password("testPassword")
                .passwordCheck("testPassword")
                .nickName("testname")
                .email("kim@naver.com")
                .build();

        memberService.SignUp(joinMemberDto);

        assertThatThrownBy(() -> memberService.SignUp(joinMemberDto2))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", CustomErrorCode.USERNAME_ALREADY_EXISTS.getErrorCode())
                .hasFieldOrPropertyWithValue("errorMessage", CustomErrorCode.USERNAME_ALREADY_EXISTS.getErrorMessage());
    }

    @DisplayName("중복 이메일 예외")
    @Test
    void DuplicateEmail() {
        JoinMemberDto joinMemberDto2 = JoinMemberDto.builder()
                .username("park123")
                .password("testPassword")
                .passwordCheck("testPassword")
                .nickName("testname")
                .email("kim@naver.com")
                .build();

        memberService.SignUp(joinMemberDto);

        assertThatThrownBy(() -> memberService.SignUp(joinMemberDto2))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", CustomErrorCode.EMAIL_ALREADY_EXISTS.getErrorCode())
                .hasFieldOrPropertyWithValue("errorMessage", CustomErrorCode.EMAIL_ALREADY_EXISTS.getErrorMessage());
    }

    @DisplayName("회원정보 수정")
    @Test
    void updateEmailAndPassword() {
        memberService.SignUp(joinMemberDto);
        Member savedMember = memberRepository.findByUsername("kim123");

        UpdateMemberDto updateMemberDto = UpdateMemberDto.builder()
                .id(savedMember.getId())
                .username("kim123")
                .password("replacePassword")
                .passwordCheck("replacePassword")
                .nickName("testname")
                .email("LEE@naver.com")
                .build();

        memberService.update(updateMemberDto);


        assertThat(savedMember.getEmail()).isEqualTo("LEE@naver.com");
        assertThat(encoder.matches("replacePassword", savedMember.getPassword())).isTrue();
    }
}