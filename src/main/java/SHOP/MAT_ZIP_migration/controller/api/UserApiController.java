package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.domain.Member;
import SHOP.MAT_ZIP_migration.dto.JoinMemberDto;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    private final MemberService memberService;

    @PostMapping("/auth/joinMember")
    public ResponseDto<Integer> save(@Valid @RequestBody JoinMemberDto joinMemberDto) {
        memberService.회원가입(joinMemberDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@RequestBody Member member) {
        memberService.회원수정(member);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
