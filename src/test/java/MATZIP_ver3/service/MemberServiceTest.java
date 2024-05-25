package MATZIP_ver3.service;

import MATZIP_ver3.domain.Member;
import MATZIP_ver3.dto.member.JoinMemberDto;
import MATZIP_ver3.dto.member.UpdateMemberDto;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import MATZIP_ver3.repository.MemberRepository;
import MATZIP_ver3.service.validator.MemberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
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
        memberService.signUp(joinMemberDto);
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
        memberValidator.passwordCheck(rawPassword, passwordCheck);

        assertThatThrownBy(() -> memberValidator.passwordCheck(joinMemberDto.getPassword(), "failPassword"))
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

        memberService.signUp(joinMemberDto);

        assertThatThrownBy(() -> memberService.signUp(joinMemberDto2))
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

        memberService.signUp(joinMemberDto);

        assertThatThrownBy(() -> memberService.signUp(joinMemberDto2))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", CustomErrorCode.EMAIL_ALREADY_EXISTS.getErrorCode())
                .hasFieldOrPropertyWithValue("errorMessage", CustomErrorCode.EMAIL_ALREADY_EXISTS.getErrorMessage());
    }
}