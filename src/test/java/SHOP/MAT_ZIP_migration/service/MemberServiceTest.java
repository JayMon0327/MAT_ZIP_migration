package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.domain.Role;
import SHOP.MAT_ZIP_migration.dto.JoinMemberDto;
import SHOP.MAT_ZIP_migration.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원가입_성공() {
        JoinMemberDto joinMemberDto = new JoinMemberDto("testUser", "password", "test@example.com", Role.USER);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(memberRepository.existsByUsername("testUser")).thenReturn(false);
        when(memberRepository.existsByEmail("test@example.com")).thenReturn(false);

        memberService.회원가입(joinMemberDto);

        verify(memberRepository).save(any(Member.class));
    }
}