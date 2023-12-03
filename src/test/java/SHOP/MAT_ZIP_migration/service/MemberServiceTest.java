package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.dto.JoinMemberDto;
import SHOP.MAT_ZIP_migration.dto.UpdateMemberDto;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import SHOP.MAT_ZIP_migration.service.validator.MemberValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("회원가입")
    @Test
    void signUp() {
        JoinMemberDto joinMemberDto = new JoinMemberDto("kim", "testPassword", "testPassword", "kim@naver.com");

        memberService.SignUp(joinMemberDto);
        Member savedMember = memberRepository.findByUsername("kim");

        assertThat(savedMember.getUsername()).isEqualTo("kim");
        assertThat(savedMember.getEmail()).isEqualTo("kim@naver.com");
        assertThat(encoder.matches("testPassword", savedMember.getPassword())).isTrue();
    }

    @DisplayName("비밀번호 확인과 예외")
    @Test
    void passwordCheck() {
        JoinMemberDto joinMemberDto = new JoinMemberDto("kim", "testPassword", "testPassword", "kim@naver.com");
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
        JoinMemberDto joinMemberDto1 = new JoinMemberDto("kim", "testPassword", "testPassword", "kim@naver.com");
        JoinMemberDto joinMemberDto2 = new JoinMemberDto("kim", "test", "test", "Lee@naver.com");
        memberService.SignUp(joinMemberDto1);

        assertThatThrownBy(() -> memberService.SignUp(joinMemberDto2))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", CustomErrorCode.USERNAME_ALREADY_EXISTS.getErrorCode())
                .hasFieldOrPropertyWithValue("errorMessage", CustomErrorCode.USERNAME_ALREADY_EXISTS.getErrorMessage());
    }

    @DisplayName("중복 이메일 예외")
    @Test
    void DuplicateEmail() {
        JoinMemberDto joinMemberDto1 = new JoinMemberDto("kim", "testPassword", "testPassword", "kim@naver.com");
        JoinMemberDto joinMemberDto2 = new JoinMemberDto("LEE", "test", "test", "kim@naver.com");
        memberService.SignUp(joinMemberDto1);

        assertThatThrownBy(() -> memberService.SignUp(joinMemberDto2))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", CustomErrorCode.EMAIL_ALREADY_EXISTS.getErrorCode())
                .hasFieldOrPropertyWithValue("errorMessage", CustomErrorCode.EMAIL_ALREADY_EXISTS.getErrorMessage());
    }

    @DisplayName("회원정보 수정")
    @Test
    void updateEmailAndPassword() {
        JoinMemberDto joinMemberDto = new JoinMemberDto("kim", "testPassword", "testPassword", "kim@naver.com");
        memberService.SignUp(joinMemberDto);
        Member savedMember = memberRepository.findByUsername("kim");

        UpdateMemberDto updateMemberDto = new UpdateMemberDto(savedMember.getId(), "kim", "replacePassword",
                "replacePassword", "LEE@naver.com");
        memberService.update(updateMemberDto);


        assertThat(savedMember.getEmail()).isEqualTo("LEE@naver.com");
        assertThat(encoder.matches("replacePassword", savedMember.getPassword())).isTrue();
    }
}